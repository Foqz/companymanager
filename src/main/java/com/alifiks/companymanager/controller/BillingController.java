package com.alifiks.companymanager.controller;

import com.alifiks.companymanager.dto.BillingRequest;
import com.alifiks.companymanager.dto.BillingResponse;
import com.alifiks.companymanager.entity.Billing;
import com.alifiks.companymanager.service.BillingService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static org.springframework.http.ResponseEntity.status;

@Controller
@RequestMapping("/api/billing")
@AllArgsConstructor
public class BillingController {

    private final BillingService billingService;

    @GetMapping()
    public ResponseEntity<BillingResponse> getMonthlyBillingByDate(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date) {
        BillingResponse billingResponse = billingService.getMonthlyBillingResponse(date);
        return status(HttpStatus.OK).body(billingResponse);
    }

    @PostMapping
    public ResponseEntity<Long> createBilling(@RequestBody BillingRequest billingRequest) {
        Billing createdBilling =  billingService.saveBilling(billingRequest);
        return status(HttpStatus.CREATED).body(createdBilling.getBillingId());
    }

    @PutMapping
    public ResponseEntity<Long> updateBilling(@RequestBody BillingRequest billingRequest) {
        Billing updatedBilling = billingService.updateBilling(billingRequest);
        return status(HttpStatus.OK).body(updatedBilling.getBillingId());
    }

    @DeleteMapping
    public ResponseEntity<Long> deleteBilling(@RequestParam Long billingId) {
        return status(HttpStatus.OK).body(billingService.deleteBilling(billingId));
    }
}
