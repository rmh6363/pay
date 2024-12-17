package com.pay.settlement.tasklet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class FirmbankingRequestInfo {

    private String bankName;
    private String bankAccountNumber;
    private int moneyAmount;

    public FirmbankingRequestInfo(String bankName, String bankAccountNumber) {
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
    }
}