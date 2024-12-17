package com.pay.remittance.application.port.in;


import com.pay.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class RequestRemittanceCommand extends SelfValidating<RequestRemittanceCommand> {
    @NotNull
    private String fromMembershipId; // from membeership
    private String toMembershipId; // to membeership
    private String toBankName;
    private String toBankAccountNumber;

    //송금 요청금액
    @NotNull
    private int amount;
    private int remittanceType; // 0 : membership(내부고객) , 1 : bank (외부은행)
    public RequestRemittanceCommand(String fromMembershipId, String toMembershipId, String toBankName, String toBankAccountNumber, int amount, int remittanceType) {
        this.fromMembershipId = fromMembershipId;
        this.toMembershipId = toMembershipId;
        this.toBankName = toBankName;
        this.toBankAccountNumber = toBankAccountNumber;
        this.amount = amount;
        this.remittanceType =remittanceType;
        this.validateSelf();
    }
}
