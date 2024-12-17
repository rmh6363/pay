package com.pay.banking.application.port.in;

import com.pay.banking.domain.FirmBankingRequest;

import java.util.List;


public interface FindFirmbankingUseCase {
    List<FirmBankingRequest> findFirmbanking(FindFirmbankingCommand command);

}
