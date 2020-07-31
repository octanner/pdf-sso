package com.octanner.pdfsso.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CreatePdfRequest {

    private UUID customerId;
    private UUID identityId;
    private String firstParagraph;
    private String secondParagraph;
    private String thirdParagraph;

}
