package com.octanner.pdfsso.pdfparts;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Image {
    public Image(PDPageContentStream contentStream, int x,
                 int y, String path, PDDocument document) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream resource = classLoader.getResourceAsStream(path);
        PDImageXObject topImage = null;
        if(resource != null){
            topImage = PDImageXObject.createFromByteArray(document, resource.readAllBytes(), "top_image");
        }else {
            throw new FileNotFoundException("Was not able to find the image.");
        }

        contentStream.drawImage(topImage, x, y);
    }
}
