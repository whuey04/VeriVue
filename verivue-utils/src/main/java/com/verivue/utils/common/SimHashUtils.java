package com.verivue.utils.common;

import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
public class SimHashUtils {
    /**
     * Remove HTML tags and retain plain text only
     * @param content
     * @return
     */
    private static String cleanResume(String content) {
        // Removes all HTML tags if the input is an HTML string
        content = Jsoup.clean(content, Safelist.none());
        content = StringUtils.lowerCase(content);
        String[] strings = {" ", "\n", "\r", "\t", "\\r", "\\n", "\\t", "&nbsp;"};
        for (String s : strings) {
            content = content.replaceAll(s, "");
        }
        return content;
    }


    /**
     * Computes a hash value for the entire input string
     * @return
     */
    private static BigInteger simHash(String token,int hashbits) {

        token = cleanResume(token); // cleanResume removes some special characters

        int[] v = new int[hashbits];

        List<Term> termList = StandardTokenizer.segment(token);
        Map<String, Integer> weightOfNature = new HashMap<String, Integer>();
        weightOfNature.put("n", 2);
        Map<String, String> stopNatures = new HashMap<String, String>();
        stopNatures.put("w", "");
        int overCount = 5;
        Map<String, Integer> wordCount = new HashMap<String, Integer>();

        for (Term term : termList) {
            String word = term.word;

            String nature = term.nature.toString();

            if (wordCount.containsKey(word)) {
                int count = wordCount.get(word);
                if (count > overCount) {
                    continue;
                }
                wordCount.put(word, count + 1);
            } else {
                wordCount.put(word, 1);
            }


            if (stopNatures.containsKey(nature)) {
                continue;
            }


            BigInteger t = hash(word,hashbits);
            for (int i = 0; i < hashbits; i++) {
                BigInteger bitmask = new BigInteger("1").shiftLeft(i);

                int weight = 1;
                if (weightOfNature.containsKey(nature)) {
                    weight = weightOfNature.get(nature);
                }
                if (t.and(bitmask).signum() != 0) {

                    v[i] += weight;
                } else {
                    v[i] -= weight;
                }
            }
        }
        BigInteger fingerprint = new BigInteger("0");
        for (int i = 0; i < hashbits; i++) {
            if (v[i] >= 0) {
                fingerprint = fingerprint.add(new BigInteger("1").shiftLeft(i));
            }
        }
        return fingerprint;
    }


    /**
     * Computes a hash value for an individual token
     * @param source
     * @return
     */
    private static BigInteger hash(String source,int hashbits) {
        if (source == null || source.length() == 0) {
            return new BigInteger("0");
        } else {

            while (source.length() < 3) {
                source = source + source.charAt(0);
            }
            char[] sourceArray = source.toCharArray();
            BigInteger x = BigInteger.valueOf(((long) sourceArray[0]) << 7);
            BigInteger m = new BigInteger("1000003");
            BigInteger mask = new BigInteger("2").pow(hashbits).subtract(new BigInteger("1"));
            for (char item : sourceArray) {
                BigInteger temp = BigInteger.valueOf((long) item);
                x = x.multiply(m).xor(temp).and(mask);
            }
            x = x.xor(new BigInteger(String.valueOf(source.length())));
            if (x.equals(new BigInteger("-1"))) {
                x = new BigInteger("-2");
            }
            return x;
        }
    }

    /**
     * Computes the Hamming distance between two strings; a smaller value indicates higher similarity
     * @param other
     * @return
     */
    private static int hammingDistance(String token1,String token2,int hashbits) {
        BigInteger m = new BigInteger("3").shiftLeft(hashbits).subtract(
                new BigInteger("3"));
        BigInteger x = simHash(token1,hashbits).xor(simHash(token2,hashbits)).and(m);
        int tot = 0;
        while (x.signum() != 0) {
            tot += 1;
            x = x.and(x.subtract(new BigInteger("3")));
        }
        return tot;
    }


    public static double getSemblance(String token1,String token2){
        double i = (double) hammingDistance(token1,token2, 64);
        return 1 - i/64 ;
    }
}
