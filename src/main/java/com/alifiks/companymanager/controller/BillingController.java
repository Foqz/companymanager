package com.alifiks.companymanager.controller;

import com.alifiks.companymanager.dto.BillingRequest;
import com.alifiks.companymanager.entity.Billing;
import com.alifiks.companymanager.service.BillingService;
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
@RequestMapping("/api/billing")
@AllArgsConstructor
public class BillingController {

    private final BillingService billingService;

    @GetMapping()
    public ResponseEntity<List<Billing>> getMonthlyBillingByDate(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date) {
        List<Billing> billingList = billingService.getMonthlyBillingByDate(date);
        return status(HttpStatus.OK).body(billingList);
    }

    @PostMapping
    public ResponseEntity<Long> saveBilling(@RequestBody BillingRequest billingRequest) {
        Billing billing =  billingService.saveBilling(billingRequest);
        return status(HttpStatus.CREATED).body(billing.getBillingId());
    }
}
