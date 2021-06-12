package com.cognizant.galvanize.contacts.controller.response;

import com.cognizant.galvanize.contacts.domain.ContactBean;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class ContactResponse {

    public static final String NO_DATA_PRESENT = "No data found";
    public static final String NO_DATA_CREATED = "No data created";
    @JsonProperty
    private Iterable<ContactBean> contactBeanResponseList;

    @JsonProperty
    private String message;
}
