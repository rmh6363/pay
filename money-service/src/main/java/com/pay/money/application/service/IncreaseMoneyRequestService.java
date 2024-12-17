package com.pay.money.application.service;


import com.pay.common.CountDownLatchManager;
import com.pay.common.RechargingMoneyTask;
import com.pay.common.SubTask;
import com.pay.common.UseCase;
import com.pay.money.adapter.axon.command.RechargingMoneyRequestCreateCommand;
import com.pay.money.adapter.out.psersistence.GetBalanceRequestMapper;
import com.pay.money.adapter.out.psersistence.MemberMoneyJpaEntity;
import com.pay.money.adapter.out.psersistence.MoneyChangingRequestMapper;
import com.pay.money.application.port.in.CreateMemberMoneyCommand;
import com.pay.money.application.port.in.CreateMemberMoneyUseCase;
import com.pay.money.application.port.in.IncreaseMoneyRequestCommand;
import com.pay.money.application.port.in.IncreaseMoneyRequestUseCase;
import com.pay.money.application.port.out.CreateMemberMoneyPort;
import com.pay.money.application.port.out.GetBalancePort;
import com.pay.money.application.port.out.IncreaseMoneyPort;
import com.pay.money.application.port.out.SendRechargingMoneyTaskPort;
import com.pay.money.application.port.out.banking.BankingPort;
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
public class IncreaseMoneyRequestService implements IncreaseMoneyRequestUseCase, CreateMemberMoneyUseCase {
    private final CountDownLatchManager countDownLatchManager;
    private final SendRechargingMoneyTaskPort sendRechargingMoneyTaskPort;
    private final MembershipPort membershipPort;
    private final IncreaseMoneyPort increaseMoneyPort;

    private final MoneyChangingRequestMapper moneyChangingRequestMapper;

    private final BankingPort bankingPort;
    private final CreateMemberMoneyPort createMemberMoneyPort;
    private final CommandGateway commandGateway;
    private final GetBalancePort getBalancePort;

    private final GetBalanceRequestMapper getBalanceRequestMapper;


    @Override
    public MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command) {
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
                .taskName("Increase Money Task / 머니 충전 Task")
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
            MemberMoneyJpaEntity memberMoneyJpaEntity = increaseMoneyPort.increaseMoney(
                    new MemberMoney.MembershipId(command.getTargetMembershipId()),
                    command.getAmount());
            if (memberMoneyJpaEntity != null) {
                return moneyChangingRequestMapper.mapToDomainEntity(increaseMoneyPort.createMoneyChangingRequest(
                        new MoneyChangingRequest.TargetMembershipId(command.getTargetMembershipId()),
                        new MoneyChangingRequest.MoneyChangingType(0),
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
    public void increaseMoneyRequestByEvent(IncreaseMoneyRequestCommand command) {
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

    @Override
    public MemberMoney createMemberMoney(CreateMemberMoneyCommand command) {
        // 1. Subtask, Task
        SubTask validMemberTask = SubTask.builder()
                .subTaskName("validMemberTask : " + "멤버십 유효성 검사")
                .membershipID(command.getTargetMembershipId())
                .taskType("membership")
                .status("ready")
                .build();

        // SubTask 리스트 생성
        List<SubTask> subTaskList = new ArrayList<>();
        subTaskList.add(validMemberTask);

        // RechargingMoneyTask 생성 (뱅킹 관련 부분 제거)
        RechargingMoneyTask task = RechargingMoneyTask.builder()
                .taskID(UUID.randomUUID().toString())
                .taskName("Membership Validation Task")
                .subTaskList(subTaskList)
                .membershipID(command.getTargetMembershipId())
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

        // 4. Task Result Consume
        String taskResult = countDownLatchManager.getDataForKey(task.getTaskID());
        if (taskResult.equals("success")) {
            // 4-1 Consume ok, Logic
            MembershipStatus membershipStatus = membershipPort.getMembershipStatus(command.getTargetMembershipId());
            MemberMoneyJpaEntity memberMoneyJpaEntity =createMemberMoneyPort.createMemberMoney(
                    new MemberMoney.MembershipId(command.getTargetMembershipId()),
                    new MemberMoney.MoneyAggregateIdentifier(membershipStatus.getAggregateIdentifier())
            );
            return getBalanceRequestMapper.mapToDomainEntity(memberMoneyJpaEntity);
        }
        return null;
    }
}
