package com.pay.payment.application.service;

import com.pay.payment.application.port.in.FinishSettlementCommand;
import com.pay.payment.application.port.in.RequestPaymentCommand;
import com.pay.payment.application.port.in.RequestPaymentUseCase;
import com.pay.payment.application.port.out.*;
import com.pay.payment.application.port.out.bank.GetRegisteredBankAccountPort;
import com.pay.payment.application.port.out.bank.RegisteredBankAccountAggregateIdentifier;
import com.pay.payment.application.port.out.franchise.FranchiseStatus;
import com.pay.payment.application.port.out.franchise.GetFranchisePort;
import com.pay.payment.application.port.out.membership.GetMembershipPort;
import com.pay.payment.application.port.out.membership.MembershipStatus;
import com.pay.payment.application.port.out.money.GetMoneyPort;
import com.pay.payment.application.port.out.money.MoneyInfo;
import com.pay.payment.domain.Payment;
import com.pay.common.UseCase;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.List;

@UseCase
@RequiredArgsConstructor
@Transactional
public class PaymentService implements RequestPaymentUseCase {

    private final CreatePaymentPort createPaymentPort;

    private final GetMembershipPort getMembershipPort;
    private final GetRegisteredBankAccountPort getRegisteredBankAccountPort;

    private final GetMoneyPort getMoneyPort;

    private final GetFranchisePort getFranchisePort;


    @Override
    public Payment requestPayment(RequestPaymentCommand command) {

        // 멤버십 유효성 확인
        MembershipStatus membershipStatus = getMembershipPort.getMembership(command.getRequestMembershipId());
        if (!membershipStatus.isValid()){
            return null;
        }
        // 뱅킹 유효성 확인
        RegisteredBankAccountAggregateIdentifier registeredBankAccountAggregateIdentifier=getRegisteredBankAccountPort.getRegisteredBankAccount(command.getRequestMembershipId());
        if (registeredBankAccountAggregateIdentifier == null){
            return null;
        }
        // 머니 유효성 확인
        MoneyInfo moneyInfo =getMoneyPort.getMoneyInfo(membershipStatus.getMembershipId());
        if (moneyInfo == null){
            return null;
        }
        // 프렌차이즈 유효성 확인
        FranchiseStatus franchiseStatus = getFranchisePort.getFranchise(command.getFranchiseId());
        if (!franchiseStatus.isValid()){
            return null;
        }
        // createPaymentPort
        return createPaymentPort.createPayment(
                command.getRequestMembershipId(),
                command.getRequestPrice(),
                command.getFranchiseId(),
                command.getFranchiseFeeRate());
    }

    @Override
    public List<Payment> getNormalStatusPayments() {
        return createPaymentPort.getNormalStatusPayments();
    }

    @Override
    public Payment finishPayment(FinishSettlementCommand command) {
        return createPaymentPort.changePaymentRequestStatus(command.getPaymentId(), 2);
    }
}
