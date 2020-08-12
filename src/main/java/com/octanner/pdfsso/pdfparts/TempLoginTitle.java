package com.octanner.pdfsso.pdfparts;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.*;
import java.io.IOException;

public class TempLoginTitle {
    public TempLoginTitle(PDPageContentStream contentStream) throws IOException {
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_BOLD, 20);
        contentStream.setNonStrokingColor(Color.BLUE);
        contentStream.newLineAtOffset(50, 380);
        contentStream.showText("Temporary login");
        contentStream.endText();
    }
}
