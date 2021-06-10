package com.alifiks.companymanager.service;

import com.alifiks.companymanager.constants.BillingConstants;
import com.alifiks.companymanager.enumeration.CITType;
import com.alifiks.companymanager.enumeration.VATType;
import com.alifiks.companymanager.exceptions.CitCustomException;
import com.alifiks.companymanager.exceptions.VatCustomException;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@NoArgsConstructor
public class TaxUtilsService {

    public BigDecimal getVATByType(VATType taxType) {
        switch (taxType) {
            case VAT_PL:
                return BillingConstants.VAT_PL;
            default:
                throw new VatCustomException("Vat type not found");
        }
    }

    public BigDecimal getCITByType(CITType citType) {
        switch (citType) {
            case PROGRESSIVE_17:
                return BillingConstants.CIT_PROGRESSIVE_17;
            case PROGRESSIVE_32:
                return BillingConstants.CIT_PROGRESSIVE_32;
            case LUMP_15:
                return BillingConstants.CIT_LUMP_15;
            case LINEAR_19:
                return BillingConstants.CIT_LINEAR_19;
            default:
                throw new CitCustomException("CIT type not found");
        }
    }
}
