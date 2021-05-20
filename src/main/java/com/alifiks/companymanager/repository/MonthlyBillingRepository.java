package com.alifiks.companymanager.repository;

import com.alifiks.companymanager.entity.MonthlyBilling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MonthlyBillingRepository extends JpaRepository<MonthlyBilling, Long> {
    List<MonthlyBilling> findAllByDate (LocalDate date);
}
