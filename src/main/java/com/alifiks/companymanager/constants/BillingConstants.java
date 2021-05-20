package com.alifiks.companymanager.constants;

import lombok.Data;

import java.math.BigDecimal;

@Data
public abstract class BillingConstants {
    public static final BigDecimal VAT_PL = new BigDecimal("0.23");
}
