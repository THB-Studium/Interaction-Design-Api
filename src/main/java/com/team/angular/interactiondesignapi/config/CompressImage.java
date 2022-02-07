package com.team.angular.interactiondesignapi.config;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

public class CompressImage {
    final static String FILEPATH = "./target/temp/img_temp.jpg";
    final static String FILEPATH_COMPRESS = "./target/temp/compressed_img.jpg";

    public static byte[] compressBild(String bild) throws Exception {

        // from byte to file
        Path path = Paths.get(FILEPATH);
        Files.write(path, Base64.decodeBase64(bild.substring(22)));// delete header: data:image/jpeg;base64

        //compress
        File imageFile = new File(FILEPATH);
        File compressedImageFile = new File(FILEPATH_COMPRESS);

        InputStream inputStream = new FileInputStream(imageFile);
        OutputStream outputStream = new FileOutputStream(compressedImageFile);

        float imageQuality = 0.9f;

        // Create the buffered image
        BufferedImage bufferedImage = ImageIO.read(inputStream);

        // Get image writers
        Iterator<ImageWriter> imageWriters = ImageIO.getImageWritersByFormatName("jpg");

        if (!imageWriters.hasNext())
            throw new IllegalStateException("Writers Not Found!!");

        ImageWriter imageWriter = (ImageWriter) imageWriters.next();
        ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(outputStream);
        imageWriter.setOutput(imageOutputStream);

        ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();

        // Set the compress quality metrics
        imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        imageWriteParam.setCompressionQuality(imageQuality);

        // Created image
        imageWriter.write(null, new IIOImage(bufferedImage, null, null), imageWriteParam);

        // close all streams
        inputStream.close();
        outputStream.close();
        imageOutputStream.close();
        imageWriter.dispose();

        // from file to bytes[]
        byte[] newImage = Files.readAllBytes(Paths.get(FILEPATH_COMPRESS));

        // delete File
        try {
            imageFile.delete();
            compressedImageFile.delete();
        } catch (Exception e) {
            System.out.println(e);
        }

        return newImage;
    }
}
