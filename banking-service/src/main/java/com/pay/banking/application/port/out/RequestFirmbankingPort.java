package com.pay.banking.application.port.out;

import com.pay.banking.adapter.out.psersistence.FirmbankingRequestEntity;
import com.pay.banking.domain.FirmBankingRequest;
import com.pay.banking.domain.RegisteredBankAccount;


public interface RequestFirmbankingPort {
    FirmbankingRequestEntity createFirmbankingRequest(
            FirmBankingRequest.FromBankName fromBankName,
            FirmBankingRequest.FromBankAccountNumber fromBankAccountNumber,
            FirmBankingRequest.ToBankName toBankName,
            FirmBankingRequest.ToBankAccountNumber toBankAccountNumber,
            FirmBankingRequest.MoneyAmount moneyAmount,
            FirmBankingRequest.FirmbankingStatus firmbankingStatus
    );
    FirmbankingRequestEntity modifyFirmbankingRequest(
            FirmbankingRequestEntity entity
    );
}
