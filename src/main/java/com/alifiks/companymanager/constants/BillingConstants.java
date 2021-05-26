package com.alifiks.companymanager.constants;

import lombok.Data;

import java.math.BigDecimal;

@Data
public abstract class BillingConstants {
    public static final BigDecimal VAT_PL = new BigDecimal("0.23");
    public static final BigDecimal CIT_LINEAR_19 = new BigDecimal("0.19");
    public static final BigDecimal CIT_PROGRESSIVE_17 = new BigDecimal("0.17");
    public static final BigDecimal CIT_PROGRESSIVE_32 = new BigDecimal("0.32");
    public static final BigDecimal CIT_LUMP_15 = new BigDecimal("0.15");
}
