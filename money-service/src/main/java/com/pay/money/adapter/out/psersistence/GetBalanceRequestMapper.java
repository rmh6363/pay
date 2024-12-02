package com.pay.money.adapter.out.psersistence;


import com.pay.money.domain.MemberMoney;
import com.pay.money.domain.MoneyChangingRequest;
import org.springframework.stereotype.Component;

@Component
public class GetBalanceRequestMapper {
    public MemberMoney mapToDomainEntity(MemberMoneyJpaEntity memberMoneyJpaEntity){
        return MemberMoney.generateMemberMoney(
                new MemberMoney.MemberMoneyId(memberMoneyJpaEntity.getMemberMoneyId()+""),
                new MemberMoney.MembershipId(memberMoneyJpaEntity.getMembershipId()+""),
                new MemberMoney.MoneyBalance(memberMoneyJpaEntity.getBalance())
        );
    }
}

