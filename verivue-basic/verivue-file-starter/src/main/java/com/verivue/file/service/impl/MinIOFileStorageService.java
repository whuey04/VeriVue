package com.verivue.file.service.impl;

import com.verivue.file.config.MinIOConfig;
import com.verivue.file.config.MinIOConfigProperties;
import com.verivue.file.service.FileStorageService;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
@EnableConfigurationProperties(MinIOConfigProperties.class)
@Import(MinIOConfig.class)
public class MinIOFileStorageService implements FileStorageService {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinIOConfigProperties minIOConfigProperties;

    private final static String separator = "/";

    public String buildFilePath(String dirPath, String fileName) {
        StringBuilder strBuilder = new StringBuilder(50);
        if(!StringUtils.isEmpty(dirPath)) {
            strBuilder.append(dirPath).append(separator);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String todayStr = simpleDateFormat.format(new Date());
        strBuilder.append(todayStr).append(separator);
        strBuilder.append(fileName);
        return strBuilder.toString();
    }

    /**
     * Upload image file
     *
     * @param prefix
     * @param filename
     * @param inputStream
     * @return
     */
    @Override
    public String uploadImgFile(String prefix, String filename, InputStream inputStream) {
        String filePath = buildFilePath(prefix, filename);
        try {
            PutObjectArgs objectArgs = PutObjectArgs.builder()
                    .object(filePath)
                    .contentType("image/jpeg")
                    .bucket(minIOConfigProperties.getBucket())
                    .stream(inputStream, inputStream.available(), -1)
                    .build();

            minioClient.putObject(objectArgs);
            StringBuilder urlPath = new StringBuilder(minIOConfigProperties.getReadPath());
            urlPath.append(separator + minIOConfigProperties.getBucket());
            urlPath.append(separator);
            urlPath.append(filePath);
            return urlPath.toString();
        }catch (Exception e) {
            log.error("minio put file error.",e);
            throw new RuntimeException("Upload file failed.");
        }
    }

    /**
     * Upload html file
     *
     * @param prefix
     * @param filename
     * @param inputStream
     * @return
     */
    @Override
    public String uploadHtmlFile(String prefix, String filename, InputStream inputStream) {
        String path = buildFilePath(prefix, filename);
        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .object(path)
                    .contentType("text/html")
                    .bucket(minIOConfigProperties.getBucket()).stream(inputStream, inputStream.available(), -1)
                    .build();
            minioClient.putObject(putObjectArgs);
            StringBuilder urlPath = new StringBuilder(minIOConfigProperties.getReadPath());
            urlPath.append(separator + minIOConfigProperties.getBucket());
            urlPath.append(separator);
            urlPath.append(path);
            return urlPath.toString();
        } catch (Exception e) {
            log.error("minio put file error.",e);
            e.printStackTrace();
            throw new RuntimeException("Upload file failed.");
        }
    }

    /**
     * Delete file
     *
     * @param pathUrl
     */
    @Override
    public void delete(String pathUrl) {
        String key = pathUrl.replace(minIOConfigProperties.getEndpoint()+"/","");
        int index = key.indexOf(separator);
        String bucket = key.substring(0, index);
        String filePath = key.substring(index + 1);

        //Remove obj
        RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
                .bucket(bucket)
                .object(filePath)
                .build();
        try {
            minioClient.removeObject(removeObjectArgs);
        }catch (Exception e) {
            log.error("minio remove file error.  pathUrl:{}",pathUrl);
            e.printStackTrace();
        }
    }

    /**
     * Download file
     *
     * @param pathUrl
     * @return
     */
    @Override
    public byte[] downLoadFile(String pathUrl) {
        String key = pathUrl.replace(minIOConfigProperties.getEndpoint()+"/","");
        int index = key.indexOf(separator);
        String bucket = key.substring(0, index);
        String filePath = key.substring(index+1);
        InputStream inputStream = null;
        try {
            inputStream = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(minIOConfigProperties.getBucket())
                    .object(filePath)
                    .build());
        } catch (Exception e) {
            log.error("minio down file error.  pathUrl:{}",pathUrl);
            e.printStackTrace();
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int rc = 0;
        while (true){
            try {
                if (!((rc = inputStream.read(buffer,0,100)) > 0)){
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            byteArrayOutputStream.write(buffer,0,rc);
        }
        return byteArrayOutputStream.toByteArray();
    }

}
