package com.richard.money.api.repository.projection;


import com.richard.money.api.model.TypeLaunch;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ResumeLaunch {

    private Long code;
    private String description;
    private LocalDate dateExpiration;
    private LocalDate datePayment;
    private BigDecimal value;
    private TypeLaunch type;
    private String category;
    private String person;

}
