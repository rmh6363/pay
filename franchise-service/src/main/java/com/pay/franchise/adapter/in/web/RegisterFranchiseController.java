package com.pay.franchise.adapter.in.web;

import com.pay.common.WebAdapter;
import com.pay.franchise.application.port.in.RegisteredFranchiseCase;
import com.pay.franchise.application.port.in.RegisteredFranchiseCommand;
import com.pay.franchise.domain.Franchise;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@WebAdapter
@RestController
@RequiredArgsConstructor
public class RegisterFranchiseController {

    private final RegisteredFranchiseCase registeredFranchiseCase;
    @PostMapping(path = "/franchise/register")
    Franchise registerFranchise(@RequestBody RegisterFranchiseRequest request ){
        RegisteredFranchiseCommand command = RegisteredFranchiseCommand.builder()
                .name(request.getName())
                .bankName(request.getBankName())
                .bankAccountNumber(request.getBankAccountNumber())
                .isValid(true)
                .contact(request.getContact())
                .build();
        return registeredFranchiseCase.registeredFranchiseCase(command);
    }
}
