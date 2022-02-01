package com.team.angular.interactiondesignapi.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Helper {

    private static final Logger log = LoggerFactory.getLogger(Helper.class);

    public static byte[] convertMultiPartFileToByte(MultipartFile file) {

        byte[] byteArr = null;
        try {
            byteArr = file.getBytes();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        InputStream inputStream = new ByteArrayInputStream(byteArr);


        return byteArr;
    }

}
