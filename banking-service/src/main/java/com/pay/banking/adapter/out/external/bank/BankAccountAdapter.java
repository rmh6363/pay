package com.pay.banking.adapter.out.external.bank;


import com.pay.banking.adapter.out.psersistence.SpringDataRegisteredBankAccountRepository;

import com.pay.banking.application.port.out.RequestBankAccountInfoPort;

import com.pay.common.ExternalSystemAdapter;
import lombok.RequiredArgsConstructor;

@ExternalSystemAdapter
@RequiredArgsConstructor
public class BankAccountAdapter implements RequestBankAccountInfoPort {
    private final SpringDataRegisteredBankAccountRepository bankAccountRepository;


    @Override
    public BankAccount getBankAccountInfo(GetBankAccountRequest request) {
        // 실제로 외부 은행에 http 을 통해서
        // 실제 은행 계좌 정보를 가져오고

        // 실제 은행 계좌 -> BankAccount

        return new BankAccount(request.getBankName(),request.getBankAccountNumber(),true);

    }
}
