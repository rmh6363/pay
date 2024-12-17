package com.pay.franchise.application.port.in;

import com.pay.franchise.domain.Franchise;


public interface FindFranchiseUseCase {
    Franchise findFranchiseUseCase(FindFranchiseCommand command);

}
