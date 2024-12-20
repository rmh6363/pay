package com.pay.money.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.Date;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberMoney {
    @Getter private final String memberMoneyId;

    @Getter private final String membershipId;

    // 잔액
    @Getter private final int balance;
    @Getter private final String aggregateIdentifier;
    // @Getter private final int linkedBankAccount;

    public static MemberMoney generateMemberMoney (
            MemberMoneyId memberMoneyId,
            MembershipId membershipId,
            MoneyBalance moneyBalance,
            MoneyAggregateIdentifier moneyAggregateIdentifier
    ){
        return new MemberMoney(
                memberMoneyId.memberMoneyId,
                membershipId.membershipId,
                moneyBalance.balance,
                moneyAggregateIdentifier.getMoneyAggregateIdentifier()
        );
    }

    @Value
    public static class MemberMoneyId {
        public MemberMoneyId(String value) {
            this.memberMoneyId = value;
        }
        String memberMoneyId ;
    }

    @Value
    public static class MembershipId {
        public MembershipId(String value) {
            this.membershipId = value;
        }
        String membershipId ;
    }

    @Value
    public static class MoneyBalance {
        public MoneyBalance(int value) {
            this.balance = value;
        }
        int balance ;
    }
    @Value
    public static class MoneyAggregateIdentifier {
        public MoneyAggregateIdentifier(String value) {
            this.moneyAggregateIdentifier = value;
        }
        String moneyAggregateIdentifier ;
    }
}