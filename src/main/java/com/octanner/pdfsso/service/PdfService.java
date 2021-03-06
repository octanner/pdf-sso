package com.octanner.pdfsso.service;

import com.octanner.pdfsso.config.ApplicationConfig;
import com.octanner.pdfsso.dto.CreatePdfRequest;
import com.octanner.pdfsso.pdfparts.Image;
import com.octanner.pdfsso.pdfparts.LoginRedeem;
import com.octanner.pdfsso.pdfparts.Paragraph;
import com.octanner.pdfsso.pdfparts.TempId;
import com.octanner.pdfsso.pdfparts.TempLoginTitle;
import com.octanner.pdfsso.pdfparts.TempPassword;
import com.octanner.pdfsso.pdfparts.ThankYou;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

@Log4j2
@Service
@AllArgsConstructor
public class PdfService {

    private final IdentityInfoService identityInfoService;

    private final ApplicationConfig applicationConfig;

    public byte[] createPdf(CreatePdfRequest createPdfRequest){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        Date timestamp = new Date();
        String documentName = createPdfRequest.getName() + timestamp.toString();

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
            new ThankYou(contentStream, createPdfRequest.getName(),50,650);

            // 1st Paragraph
            String paragraphOne = "All of us have the power to be great. That's exactly what this space " +
                    "is all about: inspiring greatness, achieving greatness, and celebrating" +
                    " greatness when we see it. Take the first step by visiting " +
                    createPdfRequest.getUrl() +
                    " to create your account and start inspiring greatness today.";
            new Paragraph(contentStream,50,620, paragraphOne, PDType1Font.TIMES_ROMAN,16,70,20);

            // How to login and redeem
            new LoginRedeem(contentStream,50,500);

            String loginParagraph = "URL - " + createPdfRequest.getUrl();
            new Paragraph(contentStream,50,460, loginParagraph, PDType1Font.TIMES_ROMAN,16,70,20);

            // 2nd Paragraph
            String loginDirectionsParagraph = "Go to the above URL and log in using the ID and password given to you below. You" +
                    " will need to review and accept the Terms and Conditions, and then update and complete" +
                    " your account information. Once completed you will be taken to your corporate page" +
                    " where you can begin to inspire greatness.";
            new Paragraph(contentStream,50,430, loginDirectionsParagraph, PDType1Font.TIMES_ROMAN,16,70,20);

            // Temporary Login
            new TempLoginTitle(contentStream,50,330);

            // Id
            new TempId(contentStream, createPdfRequest.getId(),50,300);

            // Password
            new TempPassword(contentStream, createPdfRequest.getPassword(),50,280);

            // 3rd Paragraph
            String caveat = "* Temporary login will expire 60 days from issue date. If unsuccessful in " +
                    "creating an account after 10 attempts the login credentials will be invalid" +
                    " and require a new code to be generated by contacting your Client Admin.";
            new Paragraph(contentStream, 50, 240, caveat, PDType1Font.TIMES_ROMAN, 12,90,15);

            // Top Image
            new Image(contentStream, 180, 708, "images/osko_energy_logo.png", document);

            // Bottom Image
            new Image(contentStream, 140, 5, "images/bottom_help.png", document);

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
