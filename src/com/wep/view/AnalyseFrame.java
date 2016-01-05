/*
 * @(#)AnalyseFrame.java 2014-5-17 下午04:12:39 wep
 */
package com.wep.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.wep.core.Packet;
import com.wep.core.PacketManager;
import com.wep.dao.DaoFactory;
import com.wep.dao.WepDao;

/**
 * AnalyseFrame
 * @author wang
 * @version 1.0
 *
 */
public class AnalyseFrame extends JFrame {
    private JTextArea txtIV = new JTextArea(10, 51);

    private JTextArea txtCipher = new JTextArea(10, 51);

    private JTextArea txtKey = new JTextArea(10, 51);

    private JButton btnCrack = new JButton("破解");

    private JButton btnSave = new JButton("保存");

    private PacketManager manager = new PacketManager();

    private String content;

    private List<Packet> maxIVList;

    private WepDao wepDao = DaoFactory.getWepDao();

    public AnalyseFrame(String content) {
        super("WEP密码破解");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(50, 50, 600, 800);
        JPanel p = new JPanel();
        p.add(new JLabel("IV"));
        p.add(new JScrollPane(txtIV));
        p.add(new JLabel("密文"));
        p.add(new JScrollPane(txtCipher));
        p.add(new JLabel("密钥流"));
        p.add(new JScrollPane(txtKey));
        this.add(p);
        p = new JPanel();
        p.add(btnCrack);
        p.add(btnSave);
        btnCrack.addActionListener(new Monitor());
        btnSave.addActionListener(new Monitor());
        this.add(p, BorderLayout.SOUTH);
        this.setVisible(true);
        this.content = content;
        init();
    }

    private void init() {
        List<Packet> list = manager.loadPacket(content);
        maxIVList = manager.getMaxIVPacket(list);
        for (Packet p : list) {
            txtIV.setText(txtIV.getText() + p.getIvStr() + "\n");
            txtCipher.setText(txtCipher.getText() + p.getCipherStr() + "\n");
        }
        for (Packet p : maxIVList) {
            txtKey.setText(txtKey.getText()
                    + String.format("%02x", (p.getCipher()[0] ^ 0xaa)) + " ");
        }
    }

    private class Monitor implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnCrack) {

            } else if (e.getSource() == btnSave) {
                List<Packet> list = manager.loadPacket(content);
                maxIVList = manager.getMaxIVPacket(list);
                for (Packet p : list) {
                    wepDao.saveIV(p.getIvStr());
                    wepDao.saveCipher(p.getCipherStr());
                }
                for (Packet p : maxIVList) {
                    wepDao.saveKey(String.format("%02x",
                        (p.getCipher()[0] ^ 0xaa)));
                    wepDao.saveResult(manager.PRGA(manager.KSA(p.getIv())));
                }
            }
        }

    }
}
