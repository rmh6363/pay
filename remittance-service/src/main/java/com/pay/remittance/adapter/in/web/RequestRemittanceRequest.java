package com.pay.remittance.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestRemittanceRequest {
    // 송금 요청을 위한 정보
    private String fromMembershipId; // from membeership
    private String toMembershipId; // to membeership
    private String toBankName;
    private String toBankAccountNumber;
    
    private int remittanceType; // 0 : membership(내부고객) , 1 : bank (외부은행)

    //송금 요청금액
    private int amount;
}
