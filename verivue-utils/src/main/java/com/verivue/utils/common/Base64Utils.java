package com.verivue.utils.common;

import java.util.Base64;

public class Base64Utils {

    /**
     * Decode
     * @param base64
     * @return
     */
    public static byte[] decode(String base64){
        /*BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] b = decoder.decodeBuffer(base64);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            return b;
        } catch (Exception e) {
            return null;
        }*/
        try{
            return Base64.getDecoder().decode(base64);
        }catch (Exception e){
            return null;
        }
    }


    /**
     * Encode
     * @param data
     * @return
     * @throws Exception
     */
    public static String encode(byte[] data) {
        /*BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);*/
        return Base64.getEncoder().encodeToString(data);
    }
}