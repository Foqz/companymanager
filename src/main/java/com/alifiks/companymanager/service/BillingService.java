package com.alifiks.companymanager.service;

import com.alifiks.companymanager.dto.BillingRequest;
import com.alifiks.companymanager.dto.BillingResponse;
import com.alifiks.companymanager.entity.Billing;
import com.alifiks.companymanager.repository.BillingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BillingService {

    private final BillingRepository billingRepository;
    private final TaxUtilsService taxUtilsService;
    private final BillingResponseService billingResponseService;

    public BillingResponse getMonthlyBillingResponse(LocalDate date) {
        return billingResponseService.getMonthlyBillingResponse(date);
    }

    public Billing saveBilling(BillingRequest billingRequest) {

        BigDecimal vatPercentMultiplier = taxUtilsService.getVATByType(billingRequest.getVatType());
        BigDecimal citPercentMultiplier = taxUtilsService.getCITByType(billingRequest.getCitType());
        BigDecimal citValue = billingRequest.getNetEarnings().multiply(citPercentMultiplier);
        BigDecimal vatValue = billingRequest.getNetEarnings().multiply(vatPercentMultiplier);

        if (billingRequest.getBillingId() != null) {
            Optional<Billing> existingBillingOptional = billingRepository.findById(billingRequest.getBillingId());
            if (existingBillingOptional.isPresent()) {
                Billing existingBilling = existingBillingOptional.get();
                existingBilling.setNetEarnings(billingRequest.getNetEarnings());
                existingBilling.setVatValue(vatValue);
                existingBilling.setGrossEarnings(billingRequest.getNetEarnings().add(vatValue));
                existingBilling.setCitValue(citValue);
                existingBilling.setEarningsOnHand(calculateEarningsOnHand(billingRequest.getNetEarnings(), citValue));
                existingBilling.setVatType(billingRequest.getVatType());
                existingBilling.setCitType(billingRequest.getCitType());
                return billingRepository.save(existingBilling);
            } else {
                throw new RuntimeException("Billing id not found during update");
            }
        } else {
            Billing billing = Billing.builder()
                    .date(billingRequest.getDate())
                    .netEarnings(billingRequest.getNetEarnings())
                    .vatValue(vatValue)
                    .grossEarnings(billingRequest.getNetEarnings().add(vatValue))
                    .citValue(citValue)
                    .earningsOnHand(calculateEarningsOnHand(billingRequest.getNetEarnings(), citValue))
                    .vatType(billingRequest.getVatType())
                    .citType(billingRequest.getCitType())
                    .build();
            return billingRepository.save(billing);
        }
    }

    //TODO add costs
    private BigDecimal calculateEarningsOnHand(BigDecimal netEarnings, BigDecimal CITTax) {
        return netEarnings.subtract(CITTax);
    }

    public Long deleteBilling(Long billingId) {
        Optional<Billing> billingOptional = billingRepository.findById(billingId);
        if (billingOptional.isPresent()) {
            Billing billingToRemove = billingOptional.get();
            billingRepository.delete(billingToRemove);
            return billingId;
        } else {
            throw new RuntimeException("Could not find billing with id" + billingId);
        }
    }
}
