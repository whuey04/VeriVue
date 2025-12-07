package com.verivue.utils.common;

import java.text.NumberFormat;
import java.util.Locale;

public class Compute {

    /**
     * Calculate similarity degree
     * @param strA
     * @param strB
     * @return
     */
    public static double SimilarDegree(String strA, String strB) {
        String newStrA = removeSign(strA);
        String newStrB = removeSign(strB);
        // Use the length of the longer string as the denominator, and the longest common substring length as numerator to calculate similarity
        int maxLen = Math.max(newStrA.length(), newStrB.length());
        int temp = Math.max(newStrA.length(), newStrB.length());
        int temp2 = longestCommonSubstring(newStrA, newStrB).length();
        return temp2 * 1.0 / temp;
    }


    /**
     * Write all the string data into one line
     * @param str
     * @return
     */
    public static String removeSign(String str) {
        StringBuffer sb = new StringBuffer();
        //Traverse the string. If it is a Chinese character, number or letter, append it to ab.
        for (char item : str.toCharArray()) {
            if (charReg(item)) {
                sb.append(item);
            }
        }
        return sb.toString();
    }


    /**
     * Check if the character is Chinese, digit or letter.
     * @param charValue
     * @return
     */
    public static boolean charReg(char charValue) {
        return (charValue >= 0x4E00 && charValue <= 0X9FA5) ||
                (charValue >= 'a' && charValue <= 'z') ||
                (charValue >= 'A' && charValue <= 'Z') ||
                (charValue >= '0' && charValue <= '9');
    }

    /**
     * Find common substrings using a dynamic programming algorithm.
     * It does not require that the characters found be consecutive in the given string.
     * @param strA
     * @param strB
     * @return
     */
    public static String longestCommonSubstring(String strA, String strB) {
        char[] chars_strA = strA.toCharArray();
        char[] chars_strB = strB.toCharArray();
        int m = chars_strA.length;
        int n = chars_strB.length;

        /*
         * Initialize the matrix: matrix[0][0] = 0,
         * if chars_strA[i-1] == chars_strB[j-1], matrix[i][j] = matrix[i-1][j-1] + 1,
         * else matrix[i][j] = max(matrix[i][j-1], matrix[i-1][j])
         * The values of the remaining points in the matrix are 0.
         */
        int[][] matrix = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (chars_strA[i - 1] == chars_strB[j - 1]) {
                    matrix[i][j] = matrix[i - 1][j - 1] + 1;
                } else {
                    matrix[i][j] = Math.max(matrix[i][j - 1], matrix[i - 1][j]);
                }

            }
        }

        /*
         * In the matrix, if the value of matrix[m][n] is not equal to the value of matrix[m-1][n]
         * and is not equal to the value of matrix[m][n-1],
         * then the character corresponding to matrix[m][n] is a similar character element
         * and is stored in the result array.
         */
        char[] result = new char[matrix[m][n]];
        int currentIndex = result.length - 1;
        while (matrix[m][n] != 0) {
            if (matrix[n] == matrix[n - 1]){
                n--;
            } else if (matrix[m][n] == matrix[m - 1][n]){
                m--;
            }else {
                result[currentIndex] = chars_strA[m - 1];
                currentIndex--;
                n--;
                m--;
            }
        }
        return new String(result);
    }


    /**
     * Format the result as a percentage string
     * @param resule
     * @return
     */
    public static String similarityResult(double resule) {
        return NumberFormat.getPercentInstance(new Locale("en ", "US ")).format(resule);
    }
}