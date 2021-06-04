package com.alifiks.companymanager.dto;

import com.alifiks.companymanager.entity.Billing;
import com.alifiks.companymanager.enumeration.CurrencyEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillingResponse {
    private List<Billing> billings;
    private BigDecimal sumNetEarnings;
    private BigDecimal sumGrossEarnings;
    private BigDecimal sumVatValue;
    private BigDecimal sumCitValue;
    private BigDecimal zusValue;
    private BigDecimal sumEarningsOnHand;
    private CurrencyEnum currency;
}
