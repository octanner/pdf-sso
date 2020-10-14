package com.octanner.pdfsso.controller;

import com.octanner.pdfsso.dto.CreatePdfRequest;
import com.octanner.pdfsso.service.PdfService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
