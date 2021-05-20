package com.alifiks.companymanager.service;

import com.alifiks.companymanager.constants.BillingConstants;
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

    public List<MonthlyBilling> getMonthlyBillingByDate(LocalDate date) {
        return monthlyBillingRepository.findAllByDate(date);
    }

    public MonthlyBilling saveMonthlyBilling(MonthlyBillingRequest monthlyBillingRequest) {

        BigDecimal vatAmount = monthlyBillingRequest.getNetEarnings().multiply(BillingConstants.VAT_PL);

        MonthlyBilling monthlyBilling = MonthlyBilling.builder()
                .date(monthlyBillingRequest.getDate().withDayOfMonth(1))
                .netEarnings(monthlyBillingRequest.getNetEarnings())
                .vat(vatAmount)
                .grossEarnings(monthlyBillingRequest.getNetEarnings().add(vatAmount))
                .build();

        return monthlyBillingRepository.save(monthlyBilling);
    }
}
