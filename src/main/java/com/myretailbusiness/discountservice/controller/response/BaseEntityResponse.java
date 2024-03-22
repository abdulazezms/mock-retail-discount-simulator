package com.myretailbusiness.discountservice.controller.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public abstract class BaseEntityResponse {
    private String id;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    private String createdByUser;

    private String modifiedByUser;
}
