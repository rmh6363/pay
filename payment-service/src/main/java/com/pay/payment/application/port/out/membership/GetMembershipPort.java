package com.pay.payment.application.port.out.membership;

public interface GetMembershipPort {
    public MembershipStatus getMembership(String membershipId);
}
