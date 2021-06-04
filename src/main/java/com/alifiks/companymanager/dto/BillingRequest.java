package com.alifiks.companymanager.dto;

import com.alifiks.companymanager.enumeration.CITType;
import com.alifiks.companymanager.enumeration.VATType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillingRequest {

    private Long billingId;

    @NotBlank(message = "Date of Billing cannot be blank")
    private LocalDate date;

    @NotBlank(message = "Net Earnings cannot be blank")
    private BigDecimal netEarnings;

    @NotBlank(message = "Vat Type cannot be blank")
    private VATType vatType;

    @NotBlank(message = "CIT Type cannot be blank")
    private CITType citType;
}
