package com.pay.membership.application.port.in;

import com.pay.membership.domain.Membership;


public interface FindMembershipUseCase {
    Membership findMembership(FindMembershipCommand command);

}
