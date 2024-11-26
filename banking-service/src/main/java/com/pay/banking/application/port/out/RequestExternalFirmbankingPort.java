package com.pay.banking.application.port.out;

import com.pay.banking.adapter.out.external.bank.ExternalFirmbankingRequest;
import com.pay.banking.adapter.out.external.bank.FirmbankingResult;

public interface RequestExternalFirmbankingPort {
    FirmbankingResult requestExternalFirmbanking(ExternalFirmbankingRequest request);

}
