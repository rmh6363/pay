package com.pay.payment.adapter.out.service.franchise;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Franchise {

    private String franchiseeId; // 가맹점 고유 ID

    private String name; // 가맹점 이름

    private String contact; // 가맹점 연락처


    private String bankName;


    private String bankAccountNumber;


    private boolean isValid;
}
