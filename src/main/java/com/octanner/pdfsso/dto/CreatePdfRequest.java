package com.octanner.pdfsso.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreatePdfRequest {
    private String name;
    private String id;
    private String password;
    private String url;
}
