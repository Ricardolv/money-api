package com.richard.money.api.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;



@NoArgsConstructor
@EqualsAndHashCode(of = "code")
@Data
@Entity
@Table(name = "launch")
public class Launch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;

    @NotNull
    private String description;

    @NotNull
    @Column(name = "date_expiration")
    private LocalDate dateExpiration;

    @Column(name = "date_payment")
    private LocalDate datePayment;

    @NotNull
    private BigDecimal value;

    private String note;

    @Enumerated(EnumType.STRING)
    private TypeLaunch type;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "code_category")
    private Category category;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "code_person")
    private Person person;

}
