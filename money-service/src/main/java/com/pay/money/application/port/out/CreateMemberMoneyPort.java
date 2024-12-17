package com.pay.money.application.port.out;

import com.pay.money.adapter.out.psersistence.MemberMoneyJpaEntity;
import com.pay.money.domain.MemberMoney;

public interface CreateMemberMoneyPort {
    MemberMoneyJpaEntity createMemberMoney(
            MemberMoney.MembershipId memberId,
            MemberMoney.MoneyAggregateIdentifier aggregateIdentifier
    );
}
