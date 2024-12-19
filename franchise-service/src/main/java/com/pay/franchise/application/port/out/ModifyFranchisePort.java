package com.pay.franchise.application.port.out;

import com.pay.franchise.adapter.out.psersistence.FranchiseEntity;
import com.pay.franchise.domain.Franchise;


public interface ModifyFranchisePort {
    FranchiseEntity ModifyFranchisee(
            Franchise.FranchiseeId franchiseeId,
            Franchise.Name name,
            Franchise.Contact contact,
            Franchise.BankName bankName,
            Franchise.BankAccountNumber bankAccountNumber,
            Franchise.IsValid isValid
            );
}
