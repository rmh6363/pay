package com.pay.membership.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterMembershioRequest {

    private String name;
    private String email;
    private String address;
    private String aggregateIdentifier;


}
