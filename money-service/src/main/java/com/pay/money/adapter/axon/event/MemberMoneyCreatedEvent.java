package com.pay.money.adapter.axon.event;

import com.pay.common.SelfValidating;
import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberMoneyCreatedEvent extends SelfValidating<MemberMoneyCreatedEvent> {

    private String membershipId;


}
