package com.octanner.pdfsso.pdfparts;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.*;
import java.io.IOException;

public class ThankYou {
    public ThankYou(PDPageContentStream contentStream) throws IOException {
        // Thank you
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_BOLD, 30);
        contentStream.setNonStrokingColor(Color.BLUE);
        contentStream.newLineAtOffset(50, 650);
        contentStream.showText("Thank you!");
        contentStream.endText();
    }
}
