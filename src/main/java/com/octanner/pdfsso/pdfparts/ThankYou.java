package com.octanner.pdfsso.pdfparts;

import com.octanner.pdfsso.service.object.Identity;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.*;
import java.io.IOException;

public class ThankYou {
    public ThankYou(PDPageContentStream contentStream, Identity identity) throws IOException {
        // Thank you
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_BOLD, 30);
        contentStream.setNonStrokingColor(Color.BLUE);
        contentStream.newLineAtOffset(50, 650);
        contentStream.showText("Welcome " + identity.getFirstName() + " " + identity.getLastName() + "!");
        contentStream.endText();
    }
}
