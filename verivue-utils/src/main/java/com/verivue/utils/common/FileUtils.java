package com.verivue.utils.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtils {

    /**
     * Read the first line from an InputStream resource
     * @param in
     * @return
     * @throws IOException
     */
    public static String readFirstLineFormResource(InputStream in) throws IOException{
        BufferedReader br=new BufferedReader(new InputStreamReader(in));
        return br.readLine();
    }


}
