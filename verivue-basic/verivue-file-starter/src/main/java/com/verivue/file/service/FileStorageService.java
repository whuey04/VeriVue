package com.verivue.file.service;

import java.io.InputStream;

public interface FileStorageService {

    /**
     * Upload image file
     * @param prefix
     * @param filename
     * @param inputStream
     * @return
     */
    public String uploadImgFile(String prefix, String filename, InputStream inputStream);

    /**
     * Upload html file
     * @param prefix
     * @param filename
     * @param inputStream
     * @return
     */
    public String uploadHtmlFile(String prefix, String filename,InputStream inputStream);

    /**
     * Delete file
     * @param pathUrl
     */
    public void delete(String pathUrl);

    /**
     * Download file
     * @param pathUrl
     * @return
     */
    public byte[]  downLoadFile(String pathUrl);
}
