package com.pay.settlement.tasklet;

import com.pay.settlement.adapter.out.service.payment.Payment;
import com.pay.settlement.port.out.banking.GetRegisteredBankAccountPort;
import com.pay.settlement.port.out.franchise.GetRegisteredFranchisePort;
import com.pay.settlement.port.out.franchise.RegisteredFranchise;
import com.pay.settlement.port.out.payment.PaymentPort;
import com.pay.settlement.port.out.banking.RegisteredBankAccountAggregateIdentifier;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SettlementTasklet implements  Tasklet {
    private final GetRegisteredBankAccountPort getRegisteredBankAccountPort;
    private final PaymentPort paymentPort;

    private final GetRegisteredFranchisePort getRegisteredFranchisePort;
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext){
        // 1. payment service 에서 결제 완료된 결제 내역을 조회한다.
        List<Payment> normalStatusPaymentList = paymentPort.getNormalStatusPayments();

        // 2. 각 결제 내역의 franchiseId 에 해당하는 프렌차이즈 정보에 대한
        // 뱅킹 정보(계좌번호) 를 가져와서
        Map<String, FirmbankingRequestInfo> franchiseIdToBankAccountMap = new HashMap<>();
        for (Payment payment : normalStatusPaymentList) {
            RegisteredFranchise entity = getRegisteredFranchisePort.getRegisteredFranchisePort(payment.getFranchiseId());
            franchiseIdToBankAccountMap.put(payment.getFranchiseId(), new FirmbankingRequestInfo(entity.getBankName(), entity.getBankAccountNumber()));
        }

        // 3. 각 franchiseId 별로, 정산 금액을 계산해주고
        for (Payment payment : normalStatusPaymentList) {
            FirmbankingRequestInfo firmbankingRequestInfo = franchiseIdToBankAccountMap.get(payment.getFranchiseId());
            double fee = Double.parseDouble(payment.getFranchiseFeeRate());
            int caculatedPrice = (int) ((100 - fee) * payment.getRequestPrice() * 100);
            firmbankingRequestInfo.setMoneyAmount(firmbankingRequestInfo.getMoneyAmount() + caculatedPrice);
        }

        // 4. 계산된 금액을 펌뱅킹 요청해주고
        for (FirmbankingRequestInfo firmbankingRequestInfo : franchiseIdToBankAccountMap.values()) {
            getRegisteredBankAccountPort.requestFirmbanking(
                    firmbankingRequestInfo.getBankName()
                    , firmbankingRequestInfo.getBankAccountNumber()
                    , firmbankingRequestInfo.getMoneyAmount());
        }

        // 5. 정산 완료된 결제 내역은 정산 완료 상태로 변경해준다.
        for (Payment payment : normalStatusPaymentList) {
            paymentPort.finishSettlement(payment.getPaymentId());
        }

        return RepeatStatus.FINISHED;
    }
}
