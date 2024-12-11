package com.pay.money.application.service;


import com.pay.common.CountDownLatchManager;
import com.pay.common.RechargingMoneyTask;
import com.pay.common.SubTask;
import com.pay.common.UseCase;
import com.pay.money.adapter.axon.command.RechargingMoneyRequestCreateCommand;
import com.pay.money.adapter.out.psersistence.MemberMoneyJpaEntity;
import com.pay.money.adapter.out.psersistence.MoneyChangingRequestMapper;
import com.pay.money.application.port.in.DecreaseMoneyRequestCommand;
import com.pay.money.application.port.in.DecreaseMoneyRequestUseCase;
import com.pay.money.application.port.in.IncreaseMoneyRequestCommand;
import com.pay.money.application.port.out.DecreaseMoneyPort;
import com.pay.money.application.port.out.GetBalancePort;
import com.pay.money.application.port.out.SendRechargingMoneyTaskPort;
import com.pay.money.application.port.out.membership.MembershipPort;
import com.pay.money.application.port.out.membership.MembershipStatus;
import com.pay.money.domain.MemberMoney;
import com.pay.money.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
@Transactional
public class DecreaseMoneyRequestService implements DecreaseMoneyRequestUseCase {
    private final CountDownLatchManager countDownLatchManager;
    private final SendRechargingMoneyTaskPort sendRechargingMoneyTaskPort;
    private final MembershipPort membershipPort;
    private final DecreaseMoneyPort decreaseMoneyPort;

    private final MoneyChangingRequestMapper moneyChangingRequestMapper;
    private final CommandGateway commandGateway;
    private final GetBalancePort getBalancePort;
    @Override
    public MoneyChangingRequest decreaseMoneyRequest(DecreaseMoneyRequestCommand command) {
        // 머니의충천,증액 과정
        // 1. 고객 정보가 정상인지 확인(멤버)
        MembershipStatus membershipStatus = membershipPort.getMembershipStatus(command.getTargetMembershipId());
        if (!membershipStatus.isValid()){
            return null;
        }
        // 2. 고객의 연동된 계좌가 있는지 확인, 고객의 연동된 계좌의 잔액이 충분한지 확인(뱅킹)

        // 3. 법인 계좌 상태도 정상인지 확인(뱅킹)

        // 4. 증액을 위한 기록. 요청 상태로 MoneyChangingRequest 생성

        // 5. 펌뱅킹 수행하고(고객의 연동된 계좌 -> 페이의 법인 계좌)(뱅킹)

        // 6-1. 결과 정상라면 ,성공이라고 MoneyChangingRequest 상태값 변경 후 리턴
        // 성공 시에 멤버의 MemberMoney 값 증액
        MemberMoneyJpaEntity memberMoneyJpaEntity = decreaseMoneyPort.decreaseMoney(
                new MemberMoney.MembershipId(command.getTargetMembershipId()),
                command.getAmount());
        if (memberMoneyJpaEntity != null) {
            return moneyChangingRequestMapper.mapToDomainEntity(decreaseMoneyPort.createMoneyChangingRequest(
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

    @Override
    public MoneyChangingRequest decreaseMoneyRequestAsync(DecreaseMoneyRequestCommand command) {
        // Subtask
        // 각 서비스에 특정 membershipId 로 Validation 을 하기 위한 task

        // 1. Subtask, Task
        SubTask validMemberTask = SubTask.builder()
                .subTaskName("validMemberTask : " + "멤버십 유효성 검사")
                .membershipID(command.getTargetMembershipId())
                .taskType("membership")
                .status("ready")
                .build();


        // Banking Sub task
        // Banking Account Validation
        SubTask validBankingAccountTask = SubTask.builder()
                .subTaskName("validBankingAccountTask : " + "뱅킹 계좌 유효성 검사")
                .membershipID(command.getTargetMembershipId())
                .taskType("membership")
                .status("ready")
                .build();


        // Amount Money Firmbanking -> 무조건 ok 받았다고 가정.
        List<SubTask> subTaskList = new ArrayList<>();
        subTaskList.add(validMemberTask);
        subTaskList.add(validBankingAccountTask);

        RechargingMoneyTask task = RechargingMoneyTask.builder()
                .taskID(UUID.randomUUID().toString())
                .taskName("Decrease Money Task / 머니 충전 Task")
                .subTaskList(subTaskList)
                .moneyAmount(command.getAmount())
                .membershipID(command.getTargetMembershipId())
                .toBankName("pay")
                .build();

        // 2. Kafka Cluster Produce
        // Task Produce
        sendRechargingMoneyTaskPort.sendRechargingMoneyTaskPort(task);
        countDownLatchManager.addCountDownLatch(task.getTaskID());
        // 3. Wait
        try {
            countDownLatchManager.getCountDownLatch(task.getTaskID()).await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // 3-1. tast-consumer
        //  등록된 sub-task, status 모두 ok -> task 결과를 Produce

        // 4. Task Result Consume
        // 받은 응답을 다시 ,countDownLatchManager 를 통해서 결과 데이터 받기.
        String result = countDownLatchManager.getDataForKey(task.getTaskID());
        if (result.equals("success")) {
            // 4-1 Consume ok, Logic
            MemberMoneyJpaEntity memberMoneyJpaEntity = decreaseMoneyPort.decreaseMoney(
                    new MemberMoney.MembershipId(command.getTargetMembershipId()),
                    command.getAmount());
            if (memberMoneyJpaEntity != null) {
                return moneyChangingRequestMapper.mapToDomainEntity(decreaseMoneyPort.createMoneyChangingRequest(
                        new MoneyChangingRequest.TargetMembershipId(command.getTargetMembershipId()),
                        new MoneyChangingRequest.MoneyChangingType(1),
                        new MoneyChangingRequest.ChangingMoneyAmount(command.getAmount()),
                        new MoneyChangingRequest.MoneyChangingStatus(1),
                        new MoneyChangingRequest.Uuid(UUID.randomUUID().toString())
                ));

            }
        } else {
            // 4-2 Consume fail, Logic
            return null;
        }
        // 5. Consume ok -> Logic exec
        return null;
    }

    @Override
    public void decreaseMoneyRequestByEvent(DecreaseMoneyRequestCommand command) {
        MemberMoneyJpaEntity entity = getBalancePort.getBalance(
                new MemberMoney.MembershipId(command.getTargetMembershipId())
        );
        System.out.println("entity = " + entity);
        String memberMoneyAggregateIdentifier = entity.getAggregateIdentifier();
        System.out.println("saga시작 command");
        //saga시작 command
        commandGateway.send(new RechargingMoneyRequestCreateCommand(memberMoneyAggregateIdentifier,
                        UUID.randomUUID().toString(),
                        command.getTargetMembershipId(),
                        command.getAmount()))
                .whenComplete((result, throwable) -> {
                    if (throwable != null) {
                        throwable.printStackTrace();
                        throw new RuntimeException(throwable);
                    } else {
                        System.out.println("result = " + result);

                    }
                });
    }
}
