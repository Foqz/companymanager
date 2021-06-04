package com.alifiks.companymanager.service;

import com.alifiks.companymanager.dto.BillingRequest;
import com.alifiks.companymanager.entity.Billing;
import com.alifiks.companymanager.repository.BillingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
@AllArgsConstructor
public class BillingService {

    private final BillingRepository billingRepository;
    private final TaxUtilsService taxUtilsService;

    public List<Billing> getMonthlyBillingByDate(LocalDate date) {
        return billingRepository.findAllByDateGreaterThanEqualAndDateLessThanEqual(date.with(TemporalAdjusters.firstDayOfMonth()), date.with(TemporalAdjusters.lastDayOfMonth()));
    }

    public Billing saveBilling(BillingRequest billingRequest) {

        BigDecimal VATRate = taxUtilsService.getVATByType(billingRequest.getVatType());
        BigDecimal CITRate = taxUtilsService.getCITByType(billingRequest.getCitType());
        BigDecimal CITTax = billingRequest.getNetEarnings().multiply(CITRate);

        BigDecimal vatAmount = billingRequest.getNetEarnings().multiply(VATRate);

        Billing billing = Billing.builder()
                .date(billingRequest.getDate())
                .netEarnings(billingRequest.getNetEarnings())
                .vatValue(vatAmount)
                .grossEarnings(billingRequest.getNetEarnings().add(vatAmount))
                .citValue(CITTax)
                .earningsOnHand(calculateEarningsOnHand(billingRequest.getNetEarnings(), CITTax))
                .vatType(billingRequest.getVatType())
                .citType(billingRequest.getCitType())
                .build();

        return billingRepository.save(billing);
    }

    //TODO add costs
    private BigDecimal calculateEarningsOnHand(BigDecimal netEarnings, BigDecimal CITTax) {
        return netEarnings.subtract(CITTax);
    }
}
