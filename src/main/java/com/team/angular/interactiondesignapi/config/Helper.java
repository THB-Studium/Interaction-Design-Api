package com.team.angular.interactiondesignapi.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Helper {

    private static final Logger log = LoggerFactory.getLogger(Helper.class);

    public static byte[] convertMultiPartFileToByte(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Failed to convert the MultipartFile to a File");
        }

        byte[] toReturn = new byte[(int) convertedFile.length()];

        convertedFile.delete();

        return toReturn;
    }

}
