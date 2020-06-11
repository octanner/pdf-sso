package com.example.pdfpoc.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.util.Date;

@RestController
public class PdfController {

    @GetMapping(value = "/createpdf/{text}" , produces = "application/pdf")
    public byte[] createPdf(@PathVariable String text){
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try{
            PdfWriter.getInstance(document, outputStream);
            document.open();
            document.add(new Paragraph("This is how you do ºª•¶∞¢£™"));
            document.add(new Paragraph("This is your text: " + text));
            document.add(new Paragraph(new Date().toString()));
            //Add more content here
        }catch(Exception e){
            e.printStackTrace();
        }
        document.close();

        return outputStream.toByteArray();
    }


}
