package com.pay.banking.application.port.out;

import com.pay.banking.adapter.out.psersistence.RegisteredBankAccountJpaEntity;
import com.pay.banking.domain.RegisteredBankAccount;


public interface FindBankAccountPort {
    RegisteredBankAccountJpaEntity findRegisteredBankAccount(
            RegisteredBankAccount.MembershipId membershipId
    );

}
