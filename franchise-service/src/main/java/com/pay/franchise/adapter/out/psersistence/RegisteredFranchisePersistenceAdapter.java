package com.pay.franchise.adapter.out.psersistence;

import com.pay.common.PersistenceAdapter;
import com.pay.franchise.application.port.out.RegisterFranchisePort;
import com.pay.franchise.domain.Franchise;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class RegisteredFranchisePersistenceAdapter implements RegisterFranchisePort {
    private final SpringDataFranchiseRepository springDataFranchiseRepository;


    @Override
    public FranchiseEntity createFranchisee(Franchise.Name name, Franchise.Contact contact, Franchise.BankName bankName, Franchise.BankAccountNumber bankAccountNumber, Franchise.IsValid isValid) {
        FranchiseEntity entity = springDataFranchiseRepository.save(new FranchiseEntity(
                name.getName(),
                contact.getContact(),
                bankName.getBankName(),
                bankAccountNumber.getBankAccountNumber(),
                isValid.isValid()
        ));
        return entity;
    }
}
