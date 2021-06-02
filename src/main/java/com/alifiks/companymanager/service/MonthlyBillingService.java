package com.alifiks.companymanager.service;

import com.alifiks.companymanager.dto.MonthlyBillingRequest;
import com.alifiks.companymanager.entity.MonthlyBilling;
import com.alifiks.companymanager.repository.MonthlyBillingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class MonthlyBillingService {

    private final MonthlyBillingRepository monthlyBillingRepository;
    private final TaxUtilsService taxUtilsService;

    public List<MonthlyBilling> getMonthlyBillingByDate(LocalDate date) {
        return monthlyBillingRepository.findAllByDate(date);
    }

    public MonthlyBilling saveMonthlyBilling(MonthlyBillingRequest monthlyBillingRequest) {

        BigDecimal VATRate = taxUtilsService.getVATByType(monthlyBillingRequest.getVatType());
        BigDecimal CITRate = taxUtilsService.getCITByType(monthlyBillingRequest.getCitType());
        BigDecimal CITTax = monthlyBillingRequest.getNetEarnings().multiply(CITRate);

        BigDecimal vatAmount = monthlyBillingRequest.getNetEarnings().multiply(VATRate);

        MonthlyBilling monthlyBilling = MonthlyBilling.builder()
                .date(monthlyBillingRequest.getDate().withDayOfMonth(1))
                .netEarnings(monthlyBillingRequest.getNetEarnings())
                .vatValue(vatAmount)
                .grossEarnings(monthlyBillingRequest.getNetEarnings().add(vatAmount))
                .citValue(CITTax)
                .earningsOnHand(calculateEarningsOnHand(monthlyBillingRequest.getNetEarnings(), CITTax))
                .vatType(monthlyBillingRequest.getVatType())
                .citType(monthlyBillingRequest.getCitType())
                .build();

        return monthlyBillingRepository.save(monthlyBilling);
    }

    //TODO add costs
    private BigDecimal calculateEarningsOnHand(BigDecimal netEarnings, BigDecimal CITTax) {
        return netEarnings.subtract(CITTax);
    }
}
