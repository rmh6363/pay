package com.pay.franchise.application.port.out;

import com.pay.franchise.adapter.out.psersistence.FranchiseEntity;
import com.pay.franchise.domain.Franchise;


public interface FindFranchisePort {
    FranchiseEntity FindFranchisee(
            Franchise.FranchiseeId franchiseeId
            );
}
