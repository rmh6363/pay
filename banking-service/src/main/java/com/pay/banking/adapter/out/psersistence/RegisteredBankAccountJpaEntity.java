package com.pay.banking.adapter.out.psersistence;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "registered_bank_account")
@Data
@NoArgsConstructor
public class RegisteredBankAccountJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private  Long registerBankAccountId;
    @Getter private  String membershipId;
    @Getter private  String bankName;
    @Getter private  String bankAccountNumber;
    @Getter private  boolean linkedStatusIsValid;

    public RegisteredBankAccountJpaEntity(String membershipId , String bankName, String bankAccountNumber, boolean linkedStatusIsValid) {
        this.membershipId = membershipId;
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
        this.linkedStatusIsValid = linkedStatusIsValid;
    }
    @Override
    public String toString() {
        return "RegisteredBankAccountJpaEntity{" +
                "registerBankAccountId='" + registerBankAccountId + '\'' +
                ", membershipId='" + membershipId + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankAccountNumber='" + bankAccountNumber + '\'' +
                ", linkedStatusIsValid=" + linkedStatusIsValid +
                '}';
    }
}