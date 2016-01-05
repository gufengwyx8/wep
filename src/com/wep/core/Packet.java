/*
 * @(#)Packet.java 2014-5-17 下午02:39:43 wep
 */
package com.wep.core;

import java.util.Arrays;

/**
 * Packet
 * @author wang
 * @version 1.0
 *
 */
public class Packet {

    public static final int DATA_LENGTH = 0x0040;

    public static final int IV_OFFSET = 0x001A;

    private String content;

    private int[] iv = new int[3];

    private int[] data = new int[DATA_LENGTH];

    public Packet(String content) {
        this.content = content;
        int count = 0;
        for (String str : content.split("\\n")) {
            int c = 0;
            for (String s : str.split("\\s+")) {
                if (s.length() == 2 && count < DATA_LENGTH && !s.contains(".")
                        && c < 15) {
                    data[count++] = Integer.parseInt(s, 16);
                    c++;
                }
            }
        }
        iv = Arrays.copyOfRange(data, IV_OFFSET - 1, IV_OFFSET - 1 + iv.length);
    }

    public String getIvStr() {
        String ivStr = String.format("%02x", iv[0]) + " "
                + String.format("%02x", iv[1]) + " "
                + String.format("%02x", iv[2]);
        return ivStr;
    }

    public int[] getCipher() {
        return Arrays.copyOfRange(data, IV_OFFSET + iv.length, DATA_LENGTH);
    }

    public String getCipherStr() {
        int[] cipher = this.getCipher();
        StringBuilder sb = new StringBuilder();
        for (int i : cipher) {
            sb.append(String.format("%02x", i) + " ");
        }
        return sb.toString();
    }

    /**
     * 返回  content
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置 content
     * @param content content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 返回  iv
     * @return iv
     */
    public int[] getIv() {
        return iv;
    }

    /**
     * 设置 iv
     * @param iv iv
     */
    public void setIv(int[] iv) {
        this.iv = iv;
    }

    /**
     * 返回  data
     * @return data
     */
    public int[] getData() {
        return data;
    }

    /**
     * 设置 data
     * @param data data
     */
    public void setData(int[] data) {
        this.data = data;
    }

    /**
     * 返回  dataLength
     * @return dataLength
     */
    public static int getDataLength() {
        return DATA_LENGTH;
    }

    /**
     * 返回  ivOffset
     * @return ivOffset
     */
    public static int getIvOffset() {
        return IV_OFFSET;
    }
}
