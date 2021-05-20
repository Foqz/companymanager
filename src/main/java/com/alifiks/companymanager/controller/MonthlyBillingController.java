package com.alifiks.companymanager.controller;

import com.alifiks.companymanager.dto.MonthlyBillingRequest;
import com.alifiks.companymanager.entity.MonthlyBilling;
import com.alifiks.companymanager.service.MonthlyBillingService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@Controller
@RequestMapping("/api/monthly-billing")
@AllArgsConstructor
public class MonthlyBillingController {

    private final MonthlyBillingService monthlyBillingService;

    @GetMapping()
    public ResponseEntity<List<MonthlyBilling>> getMonthlyBillingByDate(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date) {
        List<MonthlyBilling> monthlyBillingList = monthlyBillingService.getMonthlyBillingByDate(date.withDayOfMonth(1));
        return status(HttpStatus.OK).body(monthlyBillingList);
    }

    @PostMapping
    public ResponseEntity<Long> saveMonthlyBilling(@RequestBody MonthlyBillingRequest monthlyBillingRequest) {
        MonthlyBilling monthlyBilling =  monthlyBillingService.saveMonthlyBilling(monthlyBillingRequest);
        return status(HttpStatus.CREATED).body(monthlyBilling.getBillingId());
    }
}
