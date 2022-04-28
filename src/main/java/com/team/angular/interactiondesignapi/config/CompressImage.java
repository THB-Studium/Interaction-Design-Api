package com.team.angular.interactiondesignapi.config;

import com.team.angular.interactiondesignapi.exception.ApiRequestException;
import com.wizzardo.tools.image.JpegEncoder;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class CompressImage {
    final static String FILEPATH = "./img_temp.jpg";
    final static String FILEPATH_COMPRESS = "./compressed_img.jpg";

    public static byte[] compressBild(String bild) {
        // from byte to file
        try {
            Path path = Paths.get(FILEPATH);
            Files.write(path, Base64.decodeBase64(bild.substring(22)));// delete header: data:image/jpeg;base64

            //compress
            if (Files.size(Path.of(FILEPATH)) / 1024 > 5000) {
                throw new ApiRequestException("Files >5 Mo are not allowed ");
            } else if (Files.size(Path.of(FILEPATH)) / 1024 > 300) { // when size > 300

                File imageFile = new File(FILEPATH);
                File compressedImageFile = new File(FILEPATH_COMPRESS);

                InputStream inputStream = new FileInputStream(imageFile);
                OutputStream outputStream = new FileOutputStream(compressedImageFile);

                // Create the buffered image
                BufferedImage bufferedImage = ImageIO.read(inputStream);

                int size_index = (int) Files.size(Path.of(FILEPATH)) / (1024 * 500);

                switch (size_index) { // compression quality depend on the size
                    case 0:
                        saveJPG(bufferedImage, outputStream, 10);
                        break;
                    case 1:
                        saveJPG(bufferedImage, outputStream, 9);
                        break;
                    case 2:
                        saveJPG(bufferedImage, outputStream, 8);
                        break;
                    case 3:
                        saveJPG(bufferedImage, outputStream, 7);
                        break;
                    case 4:
                        saveJPG(bufferedImage, outputStream, 6);
                        break;
                    case 5:
                        saveJPG(bufferedImage, outputStream, 5);
                        break;
                    case 6:
                        saveJPG(bufferedImage, outputStream, 4);
                        break;
                    case 7:
                        saveJPG(bufferedImage, outputStream, 3);
                        break;
                    case 8:
                        saveJPG(bufferedImage, outputStream, 2);
                        break;
                    case 9:
                        saveJPG(bufferedImage, outputStream, 1);
                        break;
                }
                // from file to bytes[]
                return Files.readAllBytes(Paths.get(FILEPATH_COMPRESS));
            } else {
                // from file to bytes[]
                return Files.readAllBytes(Paths.get(FILEPATH));
            }
        } catch (Exception e) {
            throw new ApiRequestException("can't compress the picture");
        }
        // delete File
        /*try {
            imageFile.delete();
            compressedImageFile.delete();
        } catch (Exception e) {
            System.out.println(e);
        }*/

        // from file to bytes[]
        // return Files.readAllBytes(Paths.get(FILEPATH));
    }

    public static void saveJPG(BufferedImage im, OutputStream out, int quality) throws IOException {
        JpegEncoder encoder = new JpegEncoder(im, quality, out);
        encoder.Compress();
        out.close();
    }
}
