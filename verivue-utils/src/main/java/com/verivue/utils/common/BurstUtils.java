package com.verivue.utils.common;

/**
 * Sharding bucket field algorithm
 */
public class BurstUtils {

    public final static String SPLIT_CHAR = "-";

    /**
     * Join fields using "-" symbol
     * @param fields
     * @return
     */
    public static String encrypt(Object... fields){
        StringBuffer sb  = new StringBuffer();
        if(fields!=null&&fields.length>0) {
            sb.append(fields[0]);
            for (int i = 1; i < fields.length; i++) {
                sb.append(SPLIT_CHAR).append(fields[i]);
            }
        }
        return sb.toString();
    }

    /**
     * Default to first group
     * @param fields
     * @return
     */
    public static String groupOne(Object... fields){
        StringBuffer sb  = new StringBuffer();
        if(fields!=null&&fields.length>0) {
            sb.append("0");
            for (int i = 0; i < fields.length; i++) {
                sb.append(SPLIT_CHAR).append(fields[i]);
            }
        }
        return sb.toString();
    }
}
