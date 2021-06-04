package com.alifiks.companymanager.service;

import com.alifiks.companymanager.constants.BillingConstants;
import com.alifiks.companymanager.dto.BillingResponse;
import com.alifiks.companymanager.entity.Billing;
import com.alifiks.companymanager.enumeration.CurrencyEnum;
import com.alifiks.companymanager.repository.BillingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
@AllArgsConstructor
public class BillingResponseService {

    private final BillingRepository billingRepository;

    public BillingResponse getMonthlyBillingResponse(LocalDate date) {
        BigDecimal sumNetEarnings;
        BigDecimal sumGrossEarnings;
        BigDecimal sumVatValue;
        BigDecimal sumCitValue;
        BigDecimal sumEarningsOnHand;
        List<Billing> monthlyBillingList = getMonthlyBillingByDate(date);

        sumNetEarnings = monthlyBillingList.stream()
                .map(Billing::getNetEarnings)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        sumGrossEarnings = monthlyBillingList.stream()
                .map(Billing::getGrossEarnings)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        sumVatValue = monthlyBillingList.stream()
                .map(Billing::getVatValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        sumCitValue = monthlyBillingList.stream()
                .map(Billing::getCitValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        sumEarningsOnHand = monthlyBillingList.stream()
                .map(Billing::getEarningsOnHand)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return BillingResponse.builder()
                .billings(monthlyBillingList)
                .sumNetEarnings(sumNetEarnings)
                .sumGrossEarnings(sumGrossEarnings)
                .sumVatValue(sumVatValue)
                .sumCitValue(sumCitValue)
                .sumEarningsOnHand(sumEarningsOnHand)
                .currency(CurrencyEnum.PLN)
                .zusValue(BillingConstants.ZUS_NORMAL)
                .build();
    }

    private List<Billing> getMonthlyBillingByDate(LocalDate date) {
        return billingRepository.findAllByDateGreaterThanEqualAndDateLessThanEqual(date.with(TemporalAdjusters.firstDayOfMonth()), date.with(TemporalAdjusters.lastDayOfMonth()));
    }
}
