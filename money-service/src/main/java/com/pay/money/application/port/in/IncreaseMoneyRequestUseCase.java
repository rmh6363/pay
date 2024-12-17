package com.pay.money.application.port.in;


import com.pay.money.domain.MoneyChangingRequest;


public interface IncreaseMoneyRequestUseCase {
    MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command);

    void increaseMoneyRequestByEvent(IncreaseMoneyRequestCommand command);

}
