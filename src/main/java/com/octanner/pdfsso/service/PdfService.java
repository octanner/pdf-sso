package com.octanner.pdfsso.service;

import com.octanner.pdfsso.dto.CreatePdfRequest;
import com.octanner.pdfsso.service.object.Identity;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Service
@AllArgsConstructor
public class PdfService {

    private final IdentityInfoService identityInfoService;

    public Path createPdf(CreatePdfRequest createPdfRequest){

        // Get Identity pii
        Identity identity = identityInfoService.callGraphQL(createPdfRequest.getIdentityId().toString());
        Date timestamp = new Date();
        String documentName = identity.getFirstName() + identity.getLastName() + timestamp.toString() + ".pdf";

        // Creating PDF document object
        PDDocument document = new PDDocument();

        for (int i=0; i<1; i++) {
            //Creating a blank page
            PDPage blankPage = new PDPage();

            //Adding the blank page to the document
            document.addPage( blankPage );
        }

        try {
            document.save(documentName);
            PDPage page = document.getPage(0);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            //Begin the Content stream
            contentStream.beginText();
            //Setting the font to the Content stream
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
            //Setting the position for the line
            contentStream.newLineAtOffset(80, 700);
            //Adding text in the form of string
            contentStream.showText(createPdfRequest.getFirstParagraph());
            contentStream.endText();
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
            contentStream.newLineAtOffset(80, 600);
            contentStream.showText(createPdfRequest.getSecondParagraph());
            contentStream.endText();
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
            contentStream.newLineAtOffset(80, 500);
            contentStream.showText(createPdfRequest.getThirdParagraph());
            contentStream.endText();

            //Creating PDImageXObject object
            PDImageXObject pdImage = PDImageXObject.createFromFile("/Users/ericlineback/Downloads/pdfpoc/src/main/resources/images/jon.png",document);

            //Drawing the image in the PDF document
            contentStream.drawImage(pdImage, 5, 5);

            System.out.println("Image inserted");

            System.out.println("Content added");

            //Closing the content stream
            contentStream.close();

            //Saving the document
            document.save(documentName);

            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Paths.get(documentName);
    }
}
