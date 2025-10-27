package io.github.kailbin.tools;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Base64;

public class ImageDownloadUtil {

    public static byte[] downloadImageAsBytes(String imageUrl) throws IOException {

        try (InputStream in = new URL(imageUrl).openStream(); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[8192];
            int n;
            while ((n = in.read(buffer)) != -1) {
                baos.write(buffer, 0, n);
            }
            return baos.toByteArray();
        }
    }

    public static String downloadImageAsBase64(String imageUrl) throws IOException {
        //
        byte[] byteArray = downloadImageAsBytes(imageUrl);
        //
        return Base64.getEncoder().encodeToString(byteArray);
    }

}
