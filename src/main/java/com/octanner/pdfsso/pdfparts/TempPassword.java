package com.octanner.pdfsso.pdfparts;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.*;
import java.io.IOException;

public class TempPassword {
    public TempPassword(PDPageContentStream contentStream, String password, int startX, int startY)  throws IOException {
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_BOLD, 17);
        contentStream.setNonStrokingColor(Color.DARK_GRAY);
        contentStream.newLineAtOffset(startX, startY);
        contentStream.showText("PASSWORD: " + password);
        contentStream.endText();
    }
}
