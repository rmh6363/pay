package com.pay.money.adapter.in.web;

import com.pay.common.WebAdapter;
import com.pay.money.application.port.in.DecreaseMoneyRequestCommand;
import com.pay.money.application.port.in.DecreaseMoneyRequestUseCase;
import com.pay.money.application.port.in.IncreaseMoneyRequestCommand;
import com.pay.money.application.port.in.IncreaseMoneyRequestUseCase;
import com.pay.money.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestMoneyChangingController {

    private final IncreaseMoneyRequestUseCase increaseMoneyRequestUseCase;

    private final DecreaseMoneyRequestUseCase decreaseMoneyRequestUseCase;
    @PostMapping(path = "/money/increase")
    MoneyChangingResultDetail increaseMoneyChangingRequest(@RequestBody IncreaseMoneyChangingRequest request ){
        IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
                .targetMembershipId(request.getTargetMembershipId())
                .amount(request.getAmount())
                .build();
        MoneyChangingRequest moneyChangingRequest = increaseMoneyRequestUseCase.increaseMoneyRequest(command);

        MoneyChangingResultDetail resultDetail = new MoneyChangingResultDetail(
                moneyChangingRequest.getMoneyChangingRequestId(),
                0,
                1,
                moneyChangingRequest.getChangingMoneyAmount()
        );
        return resultDetail;
    }
    @PostMapping(path = "/money/increase-async")
    MoneyChangingResultDetail increaseMoneyChangingRequestAsync(@RequestBody IncreaseMoneyChangingRequest request ){
        IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
                .targetMembershipId(request.getTargetMembershipId())
                .amount(request.getAmount())
                .build();
        MoneyChangingRequest moneyChangingRequest = increaseMoneyRequestUseCase.increaseMoneyRequestAsync(command);

        MoneyChangingResultDetail resultDetail = new MoneyChangingResultDetail(
                moneyChangingRequest.getMoneyChangingRequestId(),
                0,
                1,
                moneyChangingRequest.getChangingMoneyAmount()
        );
        return resultDetail;
    }
    @PostMapping(path = "/money/decrease")
    MoneyChangingResultDetail decreaseMoneyChangingRequest(@RequestBody IncreaseMoneyChangingRequest request ){
        DecreaseMoneyRequestCommand command = DecreaseMoneyRequestCommand.builder()
                .targetMembershipId(request.getTargetMembershipId())
                .amount(request.getAmount())
                .build();
        MoneyChangingRequest moneyChangingRequest = decreaseMoneyRequestUseCase.decreaseMoneyRequest(command);

        MoneyChangingResultDetail resultDetail = new MoneyChangingResultDetail(
                moneyChangingRequest.getMoneyChangingRequestId(),
                1,
                1,
                moneyChangingRequest.getChangingMoneyAmount()
        );
        return resultDetail;
    }
    @PostMapping(path = "/money/decrease-async")
    MoneyChangingResultDetail decreaseMoneyChangingRequestAsync(@RequestBody IncreaseMoneyChangingRequest request ){
        DecreaseMoneyRequestCommand command = DecreaseMoneyRequestCommand.builder()
                .targetMembershipId(request.getTargetMembershipId())
                .amount(request.getAmount())
                .build();
        MoneyChangingRequest moneyChangingRequest = decreaseMoneyRequestUseCase.decreaseMoneyRequestAsync(command);

        MoneyChangingResultDetail resultDetail = new MoneyChangingResultDetail(
                moneyChangingRequest.getMoneyChangingRequestId(),
                1,
                1,
                moneyChangingRequest.getChangingMoneyAmount()
        );
        return resultDetail;
    }
}
