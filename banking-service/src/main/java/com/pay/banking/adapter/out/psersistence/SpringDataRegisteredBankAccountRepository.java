package com.pay.banking.adapter.out.psersistence;

import com.pay.banking.domain.RegisteredBankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataRegisteredBankAccountRepository extends JpaRepository<RegisteredBankAccountJpaEntity,Long> {
    long countByBankNameAndBankAccountNumber(String bankName, String bankAccountNumber);

    RegisteredBankAccountJpaEntity findByMembershipId(String membershipId);
}
