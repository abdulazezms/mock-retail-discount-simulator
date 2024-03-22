package com.myretailbusiness.discountservice.config.mapper;

import com.myretailbusiness.discountservice.controller.response.BaseEntityResponse;
import com.myretailbusiness.discountservice.domain.BaseEntity;
import org.springframework.stereotype.Component;

@Component
public class BaseResponseMapper {
    public BaseEntityResponse setBaseResponseAttributes(BaseEntity source, BaseEntityResponse target) {
        target.setId(source.getId());
        target.setCreatedDate(source.getCreatedDate());
        target.setLastModifiedDate(source.getLastModifiedDate());
        target.setCreatedByUser(source.getCreatedByUser());
        target.setModifiedByUser(source.getModifiedByUser());
        return target;
    }
}
