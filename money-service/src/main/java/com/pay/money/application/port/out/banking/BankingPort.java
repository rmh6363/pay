package com.pay.money.application.port.out.banking;

import com.pay.money.adapter.out.service.banking.BankingInfo;

public interface BankingPort {
    BankingInfo getMembershipBankingInfo(String membershipId);


}
