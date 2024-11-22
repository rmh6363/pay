package com.pay.banking.adapter.in.web;

import com.pay.banking.application.port.in.RequestFirmbankingCommand;
import com.pay.banking.application.port.in.RequestFirmbankingUseCase;
import com.pay.banking.domain.FirmBankingRequest;
import com.pay.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestFirmBankingController {

    private final RequestFirmbankingUseCase requestFirmBankUseCase;
    @PostMapping(path = "/banking/firmbanking/request")
    FirmBankingRequest requestFirmbanking(@RequestBody RequestFirmBankingRequest request ){
        RequestFirmbankingCommand command = RequestFirmbankingCommand.builder()
                .toBankAccountNumber(request.getToBankAccountNumber())
                .toBankName(request.getToBankName())
                .fromBankName(request.getFromBankName())
                .fromBankAccountNumber(request.getFromBankAccountNumber())
                .moneyAmount(request.getMoneyAmount())
                .build();

        return requestFirmBankUseCase.requestFirmbanking(command);
    }
}
