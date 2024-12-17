package com.pay.money.application.port.in;

import com.pay.money.domain.MemberMoney;

public interface CreateMemberMoneyUseCase {
    MemberMoney createMemberMoney(CreateMemberMoneyCommand command);
}
