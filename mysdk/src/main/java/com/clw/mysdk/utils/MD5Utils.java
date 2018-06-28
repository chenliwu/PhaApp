package com.clw.mysdk.utils;

import java.security.MessageDigest;

/**
 * MD5工具类
 */
public class MD5Utils {

    /**
     * MD5加密实现
     * @param str
     * @return
     */
    public static String md5(String str) {
        String s = "ilas" + str;
        if (s == null) {
            return "";
        } else {
            char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
                    '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            try {
                byte[] strTemp = s.getBytes();
                MessageDigest mdTemp = MessageDigest.getInstance("MD5");
                mdTemp.update(strTemp);
                byte[] md = mdTemp.digest();
                int j = md.length;
                char newStr[] = new char[j * 2];
                int k = 0;
                for (int i = 0; i < j; i++) {
                    byte byte0 = md[i];
                    newStr[k++] = hexDigits[byte0 >>> 4 & 0xf];
                    newStr[k++] = hexDigits[byte0 & 0xf];
                }
                return new String(newStr);
            } catch (Exception e) {
                return null;
            }
        }
    }

}
