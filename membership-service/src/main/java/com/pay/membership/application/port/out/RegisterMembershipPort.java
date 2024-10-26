package com.pay.membership.application.port.out;

import com.pay.membership.adapter.out.psersistence.MembershipJpaEntity;
import com.pay.membership.domain.Membership;


public interface RegisterMembershipPort {
    MembershipJpaEntity createMembership(
            Membership.MembershipName membershipName
            , Membership.MembershipEmail membershipEmail
            , Membership.MembershipAddress membershipAddress
            , Membership.MembershipIsValid membershipIsValid
            , Membership.MembershipAggregateIdentifier membershipAggregateIdentifier

    );
}
