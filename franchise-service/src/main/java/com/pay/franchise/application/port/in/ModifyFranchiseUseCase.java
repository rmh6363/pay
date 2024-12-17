package com.pay.franchise.application.port.in;

import com.pay.franchise.domain.Franchise;


public interface ModifyFranchiseUseCase {
    Franchise modifyFranchiseUseCase(ModifyFranchiseCommand command);

}
