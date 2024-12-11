package com.pay.payment.application.port.out.money;

public interface GetMoneyPort {
    MoneyInfo getMoneyInfo(String membershipId);
    boolean requestMoneyRecharging(String membershipId,int amount);

    boolean requestMoneyIncrease(String membershipId,int amount);
    boolean requestMoneyDecrease(String membershipId,int amount);
}
