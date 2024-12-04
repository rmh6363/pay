package com.pay.money.adapter.out.service.membership;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Membership {
    private String membershipId;
    private  String name;
    private  String email;
    private  String address;
    private  boolean isValid;
    private  String aggregateIdentifier;
}
