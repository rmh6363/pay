package com.pay.membership.application.port.in;

import com.pay.membership.adapter.out.psersistence.MembershipJpaEntity;
import com.pay.membership.domain.Membership;
import common.UseCase;


public interface RegisterMembershipUseCase {
    Membership registerMembership(RegisterMembershipCommand command);

}
