package com.pay.money.application.port.out;

import com.pay.money.adapter.out.psersistence.MemberMoneyJpaEntity;
import com.pay.money.domain.MemberMoney;

public interface GetBalancePort {
    MemberMoneyJpaEntity getBalance(MemberMoney.MembershipId membershipId);


}
