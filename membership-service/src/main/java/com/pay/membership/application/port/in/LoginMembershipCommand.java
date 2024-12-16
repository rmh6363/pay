package com.pay.membership.application.port.in;

import com.pay.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class LoginMembershipCommand extends SelfValidating<LoginMembershipCommand> {
   private final String membershipId;
}
