package com.octanner.pdfsso.pdfparts;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.*;
import java.io.IOException;

public class HelpContact {
    public HelpContact(PDPageContentStream contentStream) throws IOException {
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_BOLD, 15);
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.newLineAtOffset(50, 100);
        contentStream.showText("For any help please contact (888)-888-8888 or email helpdesk@octanner.com");
        contentStream.endText();
    }
}
