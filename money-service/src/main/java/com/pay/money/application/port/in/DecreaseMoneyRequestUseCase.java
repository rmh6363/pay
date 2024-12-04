package com.pay.money.application.port.in;


import com.pay.money.domain.MoneyChangingRequest;


public interface DecreaseMoneyRequestUseCase {
    MoneyChangingRequest decreaseMoneyRequest(DecreaseMoneyRequestCommand command);
    MoneyChangingRequest decreaseMoneyRequestAsync(DecreaseMoneyRequestCommand command);

}
