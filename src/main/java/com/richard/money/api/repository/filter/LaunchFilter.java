package com.richard.money.api.repository.filter;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class LaunchFilter {


    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateExpirationIn; //de

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateExpirationUpUntil; //ate
}
