package com.pay.banking.application.port.out;

import com.pay.banking.adapter.out.service.Membership;


public interface GetMembershipPort {
    public MembershipStatus getMembership(String membershipId);


}
