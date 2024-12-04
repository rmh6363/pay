package com.pay.remittance.adapter.out.service.banking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestFirmBankingResponseBody {

    private String fromBankName;
    private String fromBankAccountNumber;
    private String toBankName;
    private String toBankAccountNumber;
    private int moneyAmount;

    private int firmbankingStatus; // 0: 요청 , 1: 완료, 2: 실패


}
