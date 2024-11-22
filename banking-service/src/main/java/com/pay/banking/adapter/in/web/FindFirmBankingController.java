package com.pay.banking.adapter.in.web;

import com.pay.banking.application.port.in.FindBankAccountCommand;
import com.pay.banking.application.port.in.FindBankAccountUseCase;
import com.pay.banking.application.port.in.FindFirmbankingCommand;
import com.pay.banking.application.port.in.FindFirmbankingUseCase;
import com.pay.banking.domain.FirmBankingRequest;
import com.pay.banking.domain.RegisteredBankAccount;
import com.pay.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class FindFirmBankingController {
    private final FindFirmbankingUseCase findFirmbankingUseCase;

    @PostMapping(path = "/banking/firmbanking/findFirmbanking")
    ResponseEntity<List<FirmBankingRequest>> findFirmbankingByFromBankAccountNumberAndfromBankName(@RequestBody RequestFirmBankingRequest request) {
        FindFirmbankingCommand command = FindFirmbankingCommand.builder()
                .fromBankAccountNumber(request.getFromBankAccountNumber())
                .fromBankName(request.getFromBankName())
                .build();
        return ResponseEntity.ok(findFirmbankingUseCase.findFirmbanking(command));
    }
}
