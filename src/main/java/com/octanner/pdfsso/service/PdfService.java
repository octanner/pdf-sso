package com.octanner.pdfsso.service;

import com.octanner.pdfsso.dto.CreatePdfRequest;
import com.octanner.pdfsso.service.object.Identity;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import shaded.org.apache.maven.wagon.ResourceDoesNotExistException;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

@Log4j2
@Service
@AllArgsConstructor
public class PdfService {

    private final IdentityInfoService identityInfoService;

    public byte[] createPdf(CreatePdfRequest createPdfRequest){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Get Identity pii
        Identity identity = identityInfoService.callGraphQL(createPdfRequest.getIdentityId().toString());
        Date timestamp = new Date();
        String documentName = null;
        documentName = identity.getFirstName() + identity.getLastName() + timestamp.toString();
        // Creating PDF document object
        PDDocument document = new PDDocument();
        for (int i=0; i<1; i++) {
            //Creating a blank page
            PDPage blankPage = new PDPage();

            //Adding the blank page to the document
            document.addPage( blankPage );
        }

        try {
            PDPage page = document.getPage(0);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Thank you
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_BOLD, 30);
            contentStream.setNonStrokingColor(Color.BLUE);
            contentStream.newLineAtOffset(50, 650);
            contentStream.showText("Thank you!");
            contentStream.endText();

            // 1st Paragraph
            int lineStart = 0;
            int startX = 50;
            int startY = 620;
            String firstParagraph = createPdfRequest.getFirstParagraph();
            createParagraph(contentStream, lineStart, startX, startY, firstParagraph);

            // How to login and redeem
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_BOLD, 20);
            contentStream.setNonStrokingColor(Color.BLUE);
            contentStream.newLineAtOffset(50, 520);
            contentStream.showText("How to login and redeem");
            contentStream.endText();

            // 2st Paragraph
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 16);
            contentStream.setNonStrokingColor(Color.BLACK);
            contentStream.newLineAtOffset(50, 490);
            contentStream.showText(createPdfRequest.getSecondParagraph());
            contentStream.endText();

            // Temporary Login
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_BOLD, 20);
            contentStream.setNonStrokingColor(Color.BLUE);
            contentStream.newLineAtOffset(50, 420);
            contentStream.showText("Temporary login");
            contentStream.endText();

            // Id
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_BOLD, 17);
            contentStream.setNonStrokingColor(Color.DARK_GRAY);
            contentStream.newLineAtOffset(50, 400);
            contentStream.showText("ID: ");
            contentStream.endText();

            // Password
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_BOLD, 17);
            contentStream.setNonStrokingColor(Color.DARK_GRAY);
            contentStream.newLineAtOffset(50, 380);
            contentStream.showText("PASSWORD: ");
            contentStream.endText();

            // 3rd Paragraph
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 16);
            contentStream.setNonStrokingColor(Color.BLACK);
            contentStream.newLineAtOffset(50, 360);
            contentStream.showText(createPdfRequest.getThirdParagraph());
            contentStream.endText();

            // Top Image
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream resource = classLoader.getResourceAsStream("images/top_image.png");
            PDImageXObject topImage = null;
            if(resource != null){
                topImage = PDImageXObject.createFromByteArray(document, resource.readAllBytes(), "top_image");
            }else {
                throw new FileNotFoundException("Was not able to find the image.");
            }

            contentStream.drawImage(topImage, -1, 720);
            contentStream.close();

            document.save(byteArrayOutputStream);
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ;
        return byteArrayOutputStream.toByteArray();
    }

    int[] possibleWrapPoints(String text) {
        String[] split = text.split("(?<=\\W)");
        int[] ret = new int[split.length];
        ret[0] = split[0].length();
        for ( int i = 1 ; i < split.length ; i++ )
            ret[i] = ret[i-1] + split[i].length();
        return ret;
    }

    void createParagraph(PDPageContentStream contentStream, int lineStart, int startX, int startY, String text) throws IOException {
        int[] wrapPoints = possibleWrapPoints(text);
        String firstParagraph = text;
        for ( int i : wrapPoints ) {
            if(firstParagraph != null){
                if(text.substring(lineStart, i).length() >= 70){
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.TIMES_ROMAN, 16);
                    contentStream.setNonStrokingColor(Color.BLACK);
                    contentStream.newLineAtOffset(startX, startY);
                    String tempText = text.substring(lineStart, i);
                    if(tempText.startsWith(" ")){
                        tempText = tempText.substring(1);
                    }
                    contentStream.showText(tempText);
                    contentStream.endText();
                    startY = startY - 20;
                    firstParagraph = text.substring(i);
                    lineStart = i;
                }
                if(firstParagraph.length() < 70){
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.TIMES_ROMAN, 16);
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
}
