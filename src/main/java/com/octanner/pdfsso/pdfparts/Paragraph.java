package com.octanner.pdfsso.pdfparts;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.*;
import java.io.IOException;

public class Paragraph {
    public Paragraph(PDPageContentStream contentStream, int startX,
                     int startY, String text, PDType1Font fontType, int fontSize, int paragraphLength, int lineSeperation) throws IOException {
        int[] wrapPoints = possibleWrapPoints(text);
        int lineStart = 0;
        String firstParagraph = text;
        for ( int i : wrapPoints ) {
            if(firstParagraph != null){
                if(text.substring(lineStart, i).length() >= paragraphLength){
                    contentStream.beginText();
                    contentStream.setFont(fontType, fontSize);
                    contentStream.setNonStrokingColor(Color.BLACK);
                    contentStream.newLineAtOffset(startX, startY);
                    String tempText = text.substring(lineStart, i);
                    if(tempText.startsWith(" ")){
                        tempText = tempText.substring(1);
                    }
                    contentStream.showText(tempText);
                    contentStream.endText();
                    startY = startY - lineSeperation;
                    firstParagraph = text.substring(i);
                    lineStart = i;
                }
                if(firstParagraph.length() < paragraphLength){
                    contentStream.beginText();
                    contentStream.setFont(fontType, fontSize);
                    contentStream.setNonStrokingColor(Color.BLACK);
                    contentStream.newLineAtOffset(startX, startY);
                    if(firstParagraph.startsWith(" ")){
                        firstParagraph = firstParagraph.substring(1);
                    }
                    contentStream.showText(firstParagraph);
                    contentStream.endText();
                    firstParagraph = null;
                }
            }
        }
    }

    int[] possibleWrapPoints(String text) {
        String[] split = text.split("(?<=\\W)");
        int[] ret = new int[split.length];
        ret[0] = split[0].length();
        for ( int i = 1 ; i < split.length ; i++ )
            ret[i] = ret[i-1] + split[i].length();
        return ret;
    }
}
