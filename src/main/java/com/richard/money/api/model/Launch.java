package com.richard.money.api.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "launch")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "code")
public class Launch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;

    private String description;

    @Column(name = "date_expiration")
    private LocalDate dateExpiration;

    @Column(name = "date_payment")
    private LocalDate datePayment;

    private BigDecimal value;

    private String note;

    @Enumerated(EnumType.STRING)
    private TypeLaunch type;

    @ManyToOne
    @JoinColumn(name = "code_category")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "code_person")
    private Person person;


}
