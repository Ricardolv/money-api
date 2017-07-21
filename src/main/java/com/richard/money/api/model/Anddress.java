package com.richard.money.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class Anddress implements Serializable {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String street;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String number;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String complement;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String district;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "zip_code")
    private String zipCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String city;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String state;

}
