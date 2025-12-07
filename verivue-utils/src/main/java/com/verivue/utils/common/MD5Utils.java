package com.verivue.utils.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

    /**
     * MD5 encryption (hashing)
     * @param str
     * @return
     */
    public final static String encode(String str) {
        try {
            // Create MessageDigest instance with MD5 algorithm
            MessageDigest md = MessageDigest.getInstance("MD5");
            // Update the digest using the specified byte array
            md.update(str.getBytes());
            // Perform the hash computation and return the byte array
            byte mdBytes[] = md.digest();
            String hash = "";
            // Loop through the byte array
            for (int i = 0; i < mdBytes.length; i++) {
                int temp;
                // Convert negative byte values to positive
                if (mdBytes[i] < 0)
                    temp = 256 + mdBytes[i];
                else
                    temp = mdBytes[i];
                if (temp < 16)
                    hash += "0";
                // Convert byte to hex string and append
                hash += Integer.toString(temp, 16);
            }
            return hash;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String encodeWithSalt(String numStr, String salt) {
        return encode(encode(numStr) + salt);
    }

    public static void main(String[] args) {
        System.out.println(encode("test"));
        System.out.println(encodeWithSalt("123456","123456"));
    }
}