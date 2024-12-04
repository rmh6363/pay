package com.pay.remittance.application.service;


import com.pay.common.UseCase;
import com.pay.remittance.adapter.out.persistence.RemittanceRequestJpaEntity;
import com.pay.remittance.adapter.out.persistence.RemittanceRequestMapper;
import com.pay.remittance.application.port.in.RequestRemittanceCommand;
import com.pay.remittance.application.port.in.RequestRemittanceUseCase;
import com.pay.remittance.application.port.out.RequestRemittancePort;
import com.pay.remittance.adapter.out.service.money.BankingInfo;
import com.pay.remittance.application.port.out.banking.BankingPort;
import com.pay.remittance.application.port.out.membership.MembershipPort;
import com.pay.remittance.application.port.out.membership.MembershipStatus;
import com.pay.remittance.application.port.out.money.MoneyInfo;
import com.pay.remittance.application.port.out.money.MoneyPort;
import com.pay.remittance.domain.RemittanceRequest;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class RequestRemittanceService implements RequestRemittanceUseCase {

    private final RequestRemittancePort requestRemittancePort;
    private final RemittanceRequestMapper mapper;
    private final MembershipPort membershipPort;
    private final MoneyPort moneyPort;
    private final BankingPort bankingPort;

    @Override
    public RemittanceRequest requestRemittance(RequestRemittanceCommand command) {
        // 0. 송금 요청 상태를 시작 상태로  기록 (persistance layer)
        RemittanceRequestJpaEntity entity = requestRemittancePort.createRemittanceRequestHistory(command);

        // 1. from 멤버십 상태 확인(mebership Svc)
        MembershipStatus membershipStatus = membershipPort.getMembershipStatus(command.getFromMembershipId());
        if (!membershipStatus.isValid()){
            return null;
        }


        // 2. 잔액 존재하는지 확인(money Svc)
        MoneyInfo moneyInfo = moneyPort.getMoneyInfo(command.getFromMembershipId());

        //잔액이 충분하지 않아 충전이 필요한 경우
        if (moneyInfo.getBalance() < command.getAmount()){
            // 만원 단위로 올림하는 Math 함수
            int rechargeAmout = (int) (Math.ceil((command.getAmount() - moneyInfo.getBalance()) / 10000.0) * 10000);

            // 2-1. 잔액이 충분하지 않을시 -> 충전요청(money Svc)
            boolean moneyResult = moneyPort.requestMoneyIncrease(command.getFromMembershipId(), rechargeAmout);
            if (!moneyResult){
                return null;
            }
        }



        // 3. 송금 타입 (고객/은행)
        if (command.getRemittanceType() == 0){
            // 3-1 내부 고객일 경우
            // from 고객 머니 감액, to 고개 머니 증액(money Svc)
            boolean ToRemittanceResult = false;
            boolean FromRemittanceResult = false;
            ToRemittanceResult = moneyPort.requestMoneyDecrease(command.getFromMembershipId(), command.getAmount());
            FromRemittanceResult = moneyPort.requestMoneyIncrease(command.getToMembershipId(),command.getAmount());
            if (!ToRemittanceResult || !FromRemittanceResult){
                return null;
            }
        } else if (command.getRemittanceType() == 1) {
            // 3-2 외부 은행 경우
            // 외부 은행 계좌가 적절한지 확인(banking Svc)
            BankingInfo ExternalbankingInfo =  bankingPort.getMembershipBankingInfo(command.getToMembershipId());
            boolean remittanceResult =false;
            if (ExternalbankingInfo != null){
                // 법인계좌 -> 외부 은행 계좌 펌뱅킹 요청(banking Svc)
                BankingInfo LawBankingInfo =  bankingPort.getMembershipBankingInfo(command.getFromMembershipId());
                remittanceResult= bankingPort.requestFirmbanking(LawBankingInfo.getBankName(),LawBankingInfo.getBankAccountNumber(),ExternalbankingInfo.getBankName(), ExternalbankingInfo.getBankAccountNumber(),command.getAmount());
            }
            if (!remittanceResult){
                return null;
            }
        }




        // 4. 송금 요청 상태를 성공으로 기록 (persistance layer)
        // 송금 기록(persistance layer) // 송금 완료된 기록.
        entity.setRemittanceStatus("success");
        boolean result = requestRemittancePort.saveRemittanceRequestHistory(entity);
        if (result){
            return mapper.mapToDomainEntity(entity);
        }



        return null;
    }
}

