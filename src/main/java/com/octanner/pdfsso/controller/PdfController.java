package com.octanner.pdfsso.controller;

import com.octanner.pdfsso.dto.CreatePdfRequest;
import com.octanner.pdfsso.service.PdfService;
import lombok.AllArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;

@AllArgsConstructor
@RestController
@RequestMapping("pdf")
public class PdfController {

    private final PdfService pdfService;

    @PostMapping(value = "/create" , produces = "application/pdf")
    public byte[] createPdf(@RequestBody CreatePdfRequest createPdfRequest) throws Exception {

        return pdfService.createPdf(createPdfRequest);
    }

}
