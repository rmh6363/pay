package com.pay.settlement.port.out.franchise;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisteredFranchise {
    private String franchiseeId;
    private String name; // 가맹점 이름

    private String contact; // 가맹점 연락처

    private String bankName;

    private String bankAccountNumber;

    private boolean isValid;

}
