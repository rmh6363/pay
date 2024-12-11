package com.pay.franchise.adapter.out.psersistence;

import com.pay.franchise.domain.Franchise;
import org.springframework.stereotype.Component;

@Component
public class FranchiseMapper {
    public Franchise mapToDomainEntity(FranchiseEntity entity){
        return Franchise.generateFranchisee(
                new Franchise.FranchiseeId(entity.getFranchiseeId()+""),
                new Franchise.Name(entity.getName()),
                new Franchise.Contact(entity.getContact()),
                new Franchise.BankName(entity.getBankName()),
                new Franchise.BankAccountNumber(entity.getBankAccountNumber()),
                new Franchise.IsValid(entity.isValid())
        );
    }
}
