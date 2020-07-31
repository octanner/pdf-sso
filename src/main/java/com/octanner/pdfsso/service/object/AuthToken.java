package com.octanner.pdfsso.service.object;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthToken {
    private String access_token;
    private String token_type;
    private String scope;
    private int expires_in;
}
