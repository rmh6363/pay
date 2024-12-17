package com.pay.banking.adapter.in.web;

import com.pay.banking.adapter.out.external.bank.BankAccount;
import com.pay.banking.application.port.in.FindBankAccountCommand;
import com.pay.banking.application.port.in.FindBankAccountUseCase;
import com.pay.banking.domain.RegisteredBankAccount;
import com.pay.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class FindBankAccountController {
    private final FindBankAccountUseCase findBankAccountUseCase;
    @GetMapping(path = "/banking/account/{membershipId}")
    ResponseEntity<RegisteredBankAccount> findRegisteredBankingAccountByMemberId(@PathVariable String membershipId ){
        FindBankAccountCommand command = FindBankAccountCommand.builder()
                .membershipId(membershipId)
                .build();

        return ResponseEntity.ok(findBankAccountUseCase.findBankAccount(command));
    }
}
