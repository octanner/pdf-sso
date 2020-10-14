package com.octanner.pdfsso.pdfparts;

import com.octanner.pdfsso.service.object.Identity;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.*;
import java.io.IOException;

public class TempId {
    public TempId(PDPageContentStream contentStream, String id, int startX, int startY)  throws IOException {
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_BOLD, 17);
        contentStream.setNonStrokingColor(Color.DARK_GRAY);
        contentStream.newLineAtOffset(startX, startY);
        contentStream.showText("ID: " + id);
        contentStream.endText();
    }
}
