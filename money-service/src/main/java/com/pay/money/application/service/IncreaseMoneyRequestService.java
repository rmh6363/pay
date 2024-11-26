package com.pay.money.application.service;


import com.pay.common.UseCase;
import com.pay.money.adapter.out.psersistence.MemberMoneyJpaEntity;
import com.pay.money.adapter.out.psersistence.MoneyChangingRequestJpaEntity;
import com.pay.money.adapter.out.psersistence.MoneyChangingRequestMapper;
import com.pay.money.application.port.in.IncreaseMoneyRequestCommand;
import com.pay.money.application.port.in.IncreaseMoneyRequestUseCase;
import com.pay.money.application.port.out.IncreaseMoneyPort;
import com.pay.money.domain.MemberMoney;
import com.pay.money.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
@Transactional
public class IncreaseMoneyRequestService implements IncreaseMoneyRequestUseCase {
    private final IncreaseMoneyPort increaseMoneyPort;

    private final MoneyChangingRequestMapper moneyChangingRequestMapper;


    @Override
    public MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command) {
        // 머니의충천,증액 과정
        // 1. 고객 정보가 정상인지 확인(멤버)

        // 2. 고객의 연동된 계좌가 있는지 확인, 고객의 연동된 계좌의 잔액이 충분한지 확인(뱅킹)

        // 3. 법인 계좌 상태도 정상인지 확인(뱅킹)

        // 4. 증액을 위한 기록. 요청 상태로 MoneyChangingRequest 생성

        // 5. 펌뱅킹 수행하고(고객의 연동된 계좌 -> 페이의 법인 계좌)(뱅킹)

        // 6-1. 결과 정상라면 ,성공이라고 MoneyChangingRequest 상태값 변경 후 리턴
        // 성공 시에 멤버의 MemberMoney 값 증액
        MemberMoneyJpaEntity memberMoneyJpaEntity = increaseMoneyPort.increaseMoney(
                new MemberMoney.MembershipId(command.getTargetMembershipId()),
                command.getAmount());
        if (memberMoneyJpaEntity != null){
            return moneyChangingRequestMapper.mapToDomainEntity(increaseMoneyPort.createMoneyChangingRequest(
                    new MoneyChangingRequest.TargetMembershipId(command.getTargetMembershipId()),
                    new MoneyChangingRequest.MoneyChangingType(0),
                    new MoneyChangingRequest.ChangingMoneyAmount(command.getAmount()),
                    new MoneyChangingRequest.MoneyChangingStatus(1),
                    new MoneyChangingRequest.Uuid(UUID.randomUUID().toString())
            ));

        }

        return null;
        // 6-2. 결과 실패라면,실패라고 MoneyChangingRequest 상태값 변경 후 리턴


    }
}
