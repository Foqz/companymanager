package com.alifiks.companymanager.entity;

import com.alifiks.companymanager.enumeration.CITType;
import com.alifiks.companymanager.enumeration.VATType;
import com.alifiks.companymanager.serialization.CustomLocalDateDeserializer;
import com.alifiks.companymanager.serialization.CustomLocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class MonthlyBilling {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billingId;

    @NotBlank(message = "Date of Billing cannot be empty")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate date;

    private BigDecimal grossEarnings;

    private BigDecimal netEarnings;

    private BigDecimal vatValue;

    private BigDecimal citValue;

    private BigDecimal earningsOnHand;

    private CITType citType;

    private VATType vatType;

    @OneToMany(fetch = FetchType.LAZY)
    private List<MonthlyCost> monthlyCosts;
}
