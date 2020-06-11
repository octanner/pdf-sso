package com.example.pdfpoc.controller;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;

@RestController
public class PdfController {

    @GetMapping(value = "/createpdf/{text}" , produces = "application/pdf")
    public byte[] createPdf(@PathVariable String text) throws Exception {

        //Creating PDF document object
        PDDocument document = new PDDocument();

        for (int i=0; i<2; i++) {
            //Creating a blank page
            PDPage blankPage = new PDPage();

            //Adding the blank page to the document
            document.addPage( blankPage );
        }

        document.save("blankTest.pdf");

        PDPage page = document.getPage(0);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        //Begin the Content stream
        contentStream.beginText();

        //Setting the font to the Content stream
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);

        //Setting the position for the line
        contentStream.newLineAtOffset(25, 500);

        //Adding text in the form of string
        contentStream.showText(text);

        //Ending the content stream
        contentStream.endText();

        System.out.println("Content added");

        //Closing the content stream
        contentStream.close();

        //Creating PDImageXObject object
        PDImageXObject pdImage = PDImageXObject.createFromFile("/Users/ericlineback/Downloads/pdfpoc/src/main/resources/images/jon.png",document);

        //creating the PDPageContentStream object
        PDPageContentStream contents = new PDPageContentStream(document, page);

        //Drawing the image in the PDF document
        contents.drawImage(pdImage, 70, 250);

        System.out.println("Image inserted");

        //Closing the PDPageContentStream object
        contents.close();

        //Saving the document
        document.save("blankTest.pdf");

        document.close();

        File newPdf = new File("blankTest.pdf");

        return Files.readAllBytes(newPdf.toPath());
    }

}
