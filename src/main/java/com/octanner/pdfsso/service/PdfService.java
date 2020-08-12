package com.octanner.pdfsso.service;

import com.octanner.pdfsso.dto.CreatePdfRequest;
import com.octanner.pdfsso.pdfparts.HelpContact;
import com.octanner.pdfsso.pdfparts.Image;
import com.octanner.pdfsso.pdfparts.LoginRedeem;
import com.octanner.pdfsso.pdfparts.Paragraph;
import com.octanner.pdfsso.pdfparts.TempId;
import com.octanner.pdfsso.pdfparts.TempLoginTitle;
import com.octanner.pdfsso.pdfparts.TempPassword;
import com.octanner.pdfsso.pdfparts.ThankYou;
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
            new ThankYou(contentStream);

            // 1st Paragraph
            new Paragraph(contentStream, 50, 620, createPdfRequest.getFirstParagraph());

            // How to login and redeem
            new LoginRedeem(contentStream);

            // 2nd Paragraph
            new Paragraph(contentStream, 50, 460, createPdfRequest.getSecondParagraph());

            // Temporary Login
            new TempLoginTitle(contentStream);

            // Id
            new TempId(contentStream);

            // Password
            new TempPassword(contentStream);

            // 3rd Paragraph
            new Paragraph(contentStream, 50, 290, createPdfRequest.getThirdParagraph());

            // Top Image
            new Image(contentStream, -1, 720, "images/top_image.png", document);

            // Bottom Image
            new Image(contentStream, 250, 50, "images/bottom_logo.png", document);

            // help contact line
            new HelpContact(contentStream);

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
