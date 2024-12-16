package com.pay.franchise.adapter.out.psersistence;

import com.pay.common.PersistenceAdapter;
import com.pay.common.vault.VaultAdapter;
import com.pay.franchise.application.port.out.FindFranchisePort;
import com.pay.franchise.application.port.out.RegisterFranchisePort;
import com.pay.franchise.domain.Franchise;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class RegisteredFranchisePersistenceAdapter implements RegisterFranchisePort, FindFranchisePort {
    private final SpringDataFranchiseRepository springDataFranchiseRepository;
    private final VaultAdapter vaultAdapter;

    @Override
    public FranchiseEntity createFranchisee(Franchise.Name name, Franchise.Contact contact, Franchise.BankName bankName, Franchise.BankAccountNumber bankAccountNumber, Franchise.IsValid isValid) {
        String encryptedContact = vaultAdapter.encrypt(contact.getContact());
        String encryptedBankAccountNumber = vaultAdapter.encrypt(bankAccountNumber.getBankAccountNumber());
        String encryptedName = vaultAdapter.encrypt(name.getName());
        FranchiseEntity entity = new FranchiseEntity(
                encryptedName,
                encryptedContact,
                bankName.getBankName(),
                encryptedBankAccountNumber,
                isValid.isValid()
        );
        springDataFranchiseRepository.save(entity);
        FranchiseEntity clone = entity.clone();
        clone.setContact(contact.getContact());
        clone.setName(name.getName());
        clone.setBankAccountNumber(bankAccountNumber.getBankAccountNumber());
        return clone;
    }

    @Override
    public FranchiseEntity FindFranchisee(Franchise.FranchiseeId franchiseeId) {
        FranchiseEntity entity = springDataFranchiseRepository.getById(Long.valueOf(franchiseeId.getFranchiseeId()));
        String encryptedName = entity.getName();
        String decryptedName = vaultAdapter.decrypt(encryptedName);
        String encryptedContact = entity.getContact();
        String decryptedContact = vaultAdapter.decrypt(encryptedContact);
        String encryptedBankAccountNumber = entity.getBankAccountNumber();
        String decryptedBankAccountNumber  = vaultAdapter.decrypt(encryptedBankAccountNumber);

        FranchiseEntity clone = entity.clone();
        clone.setName(decryptedName);
        clone.setContact(decryptedContact);
        clone.setBankAccountNumber(decryptedBankAccountNumber);
        return clone;
    }
}
