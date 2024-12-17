package com.pay.membership.application.port.out;

import com.pay.membership.adapter.out.psersistence.MembershipJpaEntity;
import com.pay.membership.domain.Membership;


public interface ModifyMembershipPort {
    MembershipJpaEntity modifyMembership(
            Membership.MembershipId membershipId
            , Membership.MembershipName membershipName
            , Membership.MembershipEmail membershipEmail
            , Membership.MembershipAddress membershipAddress
            , Membership.MembershipIsValid membershipIsValid
            , Membership.MembershipRefreshToken refreshToken
            , Membership.MembershipAggregateIdentifier membershipAggregateIdentifier

    );
}
