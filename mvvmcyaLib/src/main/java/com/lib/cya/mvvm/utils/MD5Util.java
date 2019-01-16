package com.lib.cya.mvvm.utils;
import java.security.MessageDigest;


public class MD5Util {

    public static void main(String[] args) throws Exception {
        String string = "07cf0692548c25fb5b4499ab4e52fc11#464f308bda039692d3ac01d9b3e25dfd";
        String data = MD5Encode(string);
        System.out.println("MD5结果:"+data);
        System.out.println("原文:"+string);
    }

	public static String MD5Encode(String inStr) throws Exception {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
}
