package com.pay.payment.adapter.out.service.money;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberMoneyRequestBody {
    private String membershipId;
    private int amount;
}
