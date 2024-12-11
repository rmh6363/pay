package com.pay.banking.adapter.in.web;

import com.pay.banking.adapter.axon.command.CreateFirmbankingRequestCommand;
import com.pay.banking.application.port.in.RequestFirmbankingCommand;
import com.pay.banking.application.port.in.RequestFirmbankingUseCase;
import com.pay.banking.application.port.in.UpdateFirmbankingCommand;
import com.pay.banking.application.port.in.UpdateFirmbankingUseCase;
import com.pay.banking.domain.FirmBankingRequest;
import com.pay.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestFirmBankingController {

    private final RequestFirmbankingUseCase requestFirmBankUseCase;
    private final UpdateFirmbankingUseCase updateFirmbankingUseCase;
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

    @PostMapping(path = "/banking/firmbanking/request-eda")
    void requestFirmbankingByEvent(@RequestBody RequestFirmBankingRequest request ){
        RequestFirmbankingCommand command = RequestFirmbankingCommand.builder()
                .toBankAccountNumber(request.getToBankAccountNumber())
                .toBankName(request.getToBankName())
                .fromBankName(request.getFromBankName())
                .fromBankAccountNumber(request.getFromBankAccountNumber())
                .moneyAmount(request.getMoneyAmount())
                .build();
        requestFirmBankUseCase.requestFirmbankingByEvent(command);
    }
    @PutMapping(path = "/banking/firmbanking/update")
    void updateFirmbankingByEvent(@RequestBody UpdateFirmBankingRequest request ){
        UpdateFirmbankingCommand command = UpdateFirmbankingCommand.builder()
                        .firmbankingRequestAggregateIdentifier(request.getFirmbankingRequestAggregateIdentifier())
                                .firmbankingStatus(request.getStatus())
                                        .build();
        updateFirmbankingUseCase.updateFirmbankingByEvent(command);
    }
}
