package com.pay.payment.application.port.out.franchise;

import com.pay.payment.application.port.out.membership.MembershipStatus;

public interface GetFranchisePort {
    public FranchiseStatus getFranchise(String membershipId);
}
