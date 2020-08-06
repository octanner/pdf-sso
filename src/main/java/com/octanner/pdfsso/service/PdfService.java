package com.octanner.pdfsso.service;

import com.octanner.pdfsso.dto.CreatePdfRequest;
import com.octanner.pdfsso.service.object.Identity;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import shaded.org.apache.maven.wagon.ResourceDoesNotExistException;

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

            //Creating Image
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream resource = classLoader.getResourceAsStream("images/jon.png");
            PDImageXObject pdImage = null;
            if(resource != null){
                pdImage = PDImageXObject.createFromByteArray(document, resource.readAllBytes(), "jon");
            }else {
                throw new FileNotFoundException("Was not able to find the image.");
            }

            //Drawing the image in the PDF document
            contentStream.drawImage(pdImage, 5, 5);
            contentStream.close();

            document.save(byteArrayOutputStream);
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ;
        return byteArrayOutputStream.toByteArray();
    }
}
