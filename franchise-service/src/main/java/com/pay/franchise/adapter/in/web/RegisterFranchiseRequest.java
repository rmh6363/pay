package com.pay.franchise.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterFranchiseRequest {

    private String name; // 가맹점 이름

    private String contact; // 가맹점 연락처

    private String bankName;

    private String bankAccountNumber;

}
