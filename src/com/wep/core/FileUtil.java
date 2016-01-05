/*
 * @(#)FileUtil.java 2014-5-17 ÏÂÎç02:52:29
 * wep
 */
package com.wep.core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * FileUtil
 * @author wang
 * @version 1.0
 *
 */
public class FileUtil {
    public static String readFile(String path) {
        BufferedReader br = null;
        StringBuffer sb = new StringBuffer();
        try {
            br = new BufferedReader(new FileReader(path));
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str + "\n");
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                br = null;
            }
        }
        return sb.toString();
    }
}
