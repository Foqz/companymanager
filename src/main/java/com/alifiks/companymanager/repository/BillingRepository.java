package com.alifiks.companymanager.repository;

import com.alifiks.companymanager.entity.Billing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Long> {
    List<Billing> findAllByDateGreaterThanEqualAndDateLessThanEqual (LocalDate startDate, LocalDate endDate);
}
