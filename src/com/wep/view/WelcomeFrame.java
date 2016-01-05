/*
 * @(#)WelcomeFrame.java 2014-5-17 下午04:01:17 wep
 */
package com.wep.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.wep.core.FileUtil;

/**
 * WelcomeFrame
 * @author wang
 * @version 1.0
 *
 */
public class WelcomeFrame extends JFrame {
    private JButton btnSelect = new JButton("选取包");

    private JButton btnAnalyse = new JButton("分析");

    private JTextArea txtContent = new JTextArea();

    private String content;

    public WelcomeFrame() {
        super("WEP密码破解");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(50, 50, 600, 800);
        this.add(new JScrollPane(txtContent,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
            BorderLayout.CENTER);
        txtContent.setFont(new Font("宋体", Font.PLAIN, 14));
        txtContent.setEditable(false);
        JPanel p = new JPanel();
        p.add(btnSelect);
        p.add(btnAnalyse);
        btnAnalyse.setEnabled(false);
        btnSelect.addActionListener(new Monitor());
        btnAnalyse.addActionListener(new Monitor());
        this.add(p, BorderLayout.SOUTH);
        this.add(new JLabel("数据包", JLabel.CENTER), BorderLayout.NORTH);
        this.setVisible(true);
    }

    private class Monitor implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnSelect) {
                JFileChooser chooser = new JFileChooser();
                if (chooser.showOpenDialog(WelcomeFrame.this) == JFileChooser.APPROVE_OPTION) {
                    content = FileUtil.readFile(chooser.getSelectedFile()
                            .getAbsolutePath());
                    txtContent.setText(content);
                    btnAnalyse.setEnabled(true);
                }
            } else if (e.getSource() == btnAnalyse) {
                new AnalyseFrame(content);
                WelcomeFrame.this.dispose();
            }
        }

    }

    public static void main(String[] args) {
        new WelcomeFrame();
    }
}
