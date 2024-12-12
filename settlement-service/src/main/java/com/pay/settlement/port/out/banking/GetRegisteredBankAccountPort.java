package com.pay.settlement.port.out.banking;

public interface GetRegisteredBankAccountPort {
    RegisteredBankAccountAggregateIdentifier getRegisteredBankAccount(String membershipId);

    // 타겟 계좌, 금액
    void requestFirmbanking(String bankName, String bankAccountNumber, int moneyAmount);
}
