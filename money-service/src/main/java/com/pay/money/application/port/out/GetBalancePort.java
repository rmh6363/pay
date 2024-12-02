package com.pay.money.application.port.out;

import com.pay.money.adapter.out.psersistence.MemberMoneyJpaEntity;

public interface GetBalancePort {
    public MemberMoneyJpaEntity getBalance(String membershipId);


}
