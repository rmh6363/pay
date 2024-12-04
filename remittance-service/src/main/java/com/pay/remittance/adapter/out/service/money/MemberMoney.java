package com.pay.remittance.adapter.out.service.money;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberMoney {
    private String memberMoneyId;
    private  String membershipId;
    private  int balance;


}
