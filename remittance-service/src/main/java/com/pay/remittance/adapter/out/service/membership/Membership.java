package com.pay.remittance.adapter.out.service.membership;

import lombok.*;

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
    private String refreshToken;
}
