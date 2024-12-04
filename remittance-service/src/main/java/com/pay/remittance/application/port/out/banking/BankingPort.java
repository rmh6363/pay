package com.pay.remittance.application.port.out.banking;

import com.pay.remittance.adapter.out.service.money.BankingInfo;

public interface BankingPort {
    BankingInfo getMembershipBankingInfo(String membershipId);

    boolean requestFirmbanking(String FromBankName ,String FromBankAccountNumber,String ToBankName ,String ToBankAccountNumber,int moneyAmount);
}
