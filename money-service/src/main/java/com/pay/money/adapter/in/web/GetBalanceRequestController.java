package com.pay.money.adapter.in.web;

import com.pay.common.WebAdapter;
import com.pay.money.application.port.in.GetBalanceRequestCommand;
import com.pay.money.application.port.in.GetBalanceRequestUseCase;
import com.pay.money.application.port.in.IncreaseMoneyRequestCommand;
import com.pay.money.application.port.in.IncreaseMoneyRequestUseCase;
import com.pay.money.domain.MemberMoney;
import com.pay.money.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@WebAdapter
@RestController
@RequiredArgsConstructor
public class GetBalanceRequestController {

    private final GetBalanceRequestUseCase getBalanceRequestUseCase;
    @GetMapping(path = "/money/{membershipId}")
    MemberMoney getBalance(@PathVariable String membershipId ){
        GetBalanceRequestCommand command = GetBalanceRequestCommand.builder()
                .targetMembershipId(membershipId)
                        .build();

        return getBalanceRequestUseCase.getBalanceRequest(command);
    }

}
