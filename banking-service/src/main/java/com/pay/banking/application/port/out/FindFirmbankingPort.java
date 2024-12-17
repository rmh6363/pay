package com.pay.banking.application.port.out;

import com.pay.banking.adapter.out.psersistence.FirmbankingRequestEntity;
import com.pay.banking.adapter.out.psersistence.RegisteredBankAccountJpaEntity;
import com.pay.banking.domain.FirmBankingRequest;
import com.pay.banking.domain.RegisteredBankAccount;

import java.util.List;


public interface FindFirmbankingPort {
    List<FirmbankingRequestEntity> findfirmbanking(
            FirmBankingRequest.FromBankAccountNumber fromBankAccountNumber,
            FirmBankingRequest.FromBankName fromBankName

    );

}
