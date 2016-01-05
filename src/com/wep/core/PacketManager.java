/*
 * @(#)PacketManager.java 2014-5-17 ÏÂÎç02:55:38 wep
 */
package com.wep.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * PacketManager
 * @author wang
 * @version 1.0
 *
 */
public class PacketManager {
    public List<Packet> loadPacket(String content) {
        List<Packet> list = new ArrayList<Packet>();
        for (String str : content.split("\\b(?=0000)")) {
            if (!str.trim().equals("")) {
                Packet p = new Packet(str);
                list.add(p);
            }
        }
        return list;
    }

    public List<Packet> getMaxIVPacket(List<Packet> list) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        Map<String, List<Packet>> map2 = new HashMap<String, List<Packet>>();
        for (Packet p : list) {
            String ivStr = p.getIvStr();
            if (!map.containsKey(ivStr)) {
                map.put(ivStr, 0);
                map2.put(ivStr, new ArrayList<Packet>());
            }
            map.put(ivStr, map.get(ivStr) + 1);
            map2.get(ivStr).add(p);
        }
        List<Packet> result = null;
        int max = 0;
        for (Entry<String, Integer> entry : map.entrySet()) {
            if (max < entry.getValue()) {
                result = map2.get(entry.getKey());
                max = entry.getValue();
            }
        }
        return result;
    }

    public int[] KSA(int[] iv) {
        int[] K = Arrays.copyOf(iv, iv.length);
        int[] s = new int[256];
        for (int i = 0; i < s.length; i++) {
            s[i] = i;
        }
        int j = 0;
        for (int i = 0; i < s.length; i++) {
            j += s[i] + K[i % K.length];
            j %= s.length;
            int tmp = s[i];
            s[i] = s[j];
            s[j] = tmp;
        }
        return s;
    }

    public int[] PRGA(int[] s) {
        int m = 0, n = 0;
        int[] keyset = new int[256];
        for (int i = 0; i < s.length; i++) {
            m = (m + 1) & 255;
            n = (n + s[m]) & 255;
            keyset[i] = s[(s[m] + s[n]) & 255];
            int tmp = s[m];
            s[m] = s[n];
            s[n] = tmp;
        }
        return Arrays.copyOf(keyset, 64);
    }

    public boolean isRuoIV(int[] iv) {
        return iv[1] == 0xFF;
    }
}
