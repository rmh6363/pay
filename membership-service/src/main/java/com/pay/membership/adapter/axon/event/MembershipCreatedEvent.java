package com.pay.membership.adapter.axon.event;

import com.pay.common.SelfValidating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MembershipCreatedEvent extends SelfValidating<MembershipCreatedEvent> {


    private  String name;


    private String email;


    private String address;


    private Boolean isValid;




}
