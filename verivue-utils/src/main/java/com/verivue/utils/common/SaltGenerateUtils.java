package com.verivue.utils.common;

import java.util.Random;

public class SaltGenerateUtils {

    public static String generateSalt(int length) {
        char[] chars = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O',
                'P','Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f',
                'g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w',
                'x','y','z','0','1','2','3','4','5','6','7','8','9'};
        StringBuilder salt = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            salt.append(chars[random.nextInt(chars.length)]);
        }
        return salt.toString();
    }

    public static void main(String[] args) {
        System.out.println(generateSalt(8));
    }
}
