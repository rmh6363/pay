package com.pay.banking.adapter.out.psersistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpringDataFirmbankingRequestRepository extends JpaRepository<FirmbankingRequestEntity,Long> {
    @Query("SELECT f FROM FirmbankingRequestEntity f WHERE " +
            "(f.fromBankName = :fromBankName AND f.fromBankAccountNumber = :fromBankAccountNumber) " +
            "OR (f.toBankName = :fromBankName AND f.toBankAccountNumber = :fromBankAccountNumber)")
    List<FirmbankingRequestEntity> findByBankDetails(
            @Param("fromBankName") String fromBankName,
            @Param("fromBankAccountNumber") String fromBankAccountNumber);
}
