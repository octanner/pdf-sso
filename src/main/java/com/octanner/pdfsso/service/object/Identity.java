package com.octanner.pdfsso.service.object;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Identity {

    private String id;

    private String customerId;

    private String uniqueId;

    private String firstName;

    private String lastName;

    private String loginId;

}
