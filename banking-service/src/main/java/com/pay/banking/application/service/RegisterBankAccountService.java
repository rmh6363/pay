package com.pay.banking.application.service;

import com.pay.banking.adapter.out.external.bank.BankAccount;
import com.pay.banking.adapter.out.external.bank.GetBankAccountRequest;
import com.pay.banking.adapter.out.psersistence.RegisteredBankAccountJpaEntity;
import com.pay.banking.adapter.out.psersistence.RegisteredBankAccountMapper;
import com.pay.banking.application.port.in.RegisterBankAccountCommand;
import com.pay.banking.application.port.in.RegisterBankAccountUseCase;
import com.pay.banking.application.port.out.GetMembershipPort;
import com.pay.banking.application.port.out.MembershipStatus;
import com.pay.banking.application.port.out.RegisterBankAccountPort;
import com.pay.banking.application.port.out.RequestBankAccountInfoPort;
import com.pay.banking.domain.RegisteredBankAccount;
import com.pay.common.UseCase;

import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class RegisterBankAccountService implements RegisterBankAccountUseCase {
    private final RegisterBankAccountPort registerBankAccountPort;
    private final GetMembershipPort getMembershipPort;
    private final RequestBankAccountInfoPort requestBankAccountInfoPort;
    private final RegisteredBankAccountMapper registeredBankAccountMapper;

    @Override
    public RegisteredBankAccount registerBankAccount(RegisterBankAccountCommand command) {

        // 은행 계좌를 등록해야하는 서비스 (비즈니스 로직)


        // call membership svc , 정상인지 확인
        // call external bank svc 정상인지 확인
        MembershipStatus membershipStatus = getMembershipPort.getMembership(command.getMembershipId());
        if (!membershipStatus.isValid()){
            return null;
        }

        // 1. 등록된 계좌인지 확인.
        // 외부의 은행이 이 계좌 정상인지? 체크
        // Biz Login -> External System
        // port -> adapter -> External System
        // port
        // 실제 외부의 은행 계좌 정보 Get
        BankAccount bankAccount = requestBankAccountInfoPort.getBankAccountInfo(new GetBankAccountRequest(command.getBankName(), command.getBankAccountNumber(), command.isValid()));

        // 1. 중복 계좌 체크
        long count = registerBankAccountPort.countRegisteredBankAccount(new RegisteredBankAccount.BankName(command.getBankName()),new RegisteredBankAccount.BankAccountNumber(command.getBankAccountNumber()));
        if (count > 0) {
            throw new IllegalArgumentException("이미 등록된 계좌입니다.");
        }
        boolean accountIsValid = bankAccount.isValid();

        // 2. 등록가능한 계좌라면, 등록. 성공하면 , 등록에 성공한 등록 정보를 리턴

        // 2-1. 등록가능하지 않은 계좌. 에러 린턴
        if (accountIsValid) {
            // 등록 정보 저장
            RegisteredBankAccountJpaEntity jpaEntity = registerBankAccountPort.createRegisteredBankAccount(
                    new RegisteredBankAccount.MembershipId(command.getMembershipId() + ""),
                    new RegisteredBankAccount.BankName(command.getBankName()),
                    new RegisteredBankAccount.BankAccountNumber(command.getBankAccountNumber()),
                    new RegisteredBankAccount.LinkedStatusIsValid(command.isValid())
            );
            return registeredBankAccountMapper.mapToDomainEntity(jpaEntity);
        } else {
            return null;
        }

    }
}
