package com.pay.franchise.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Franchise {

    @Getter
    private final String franchiseeId; // 가맹점 고유 ID
    @Getter
    private final String name; // 가맹점 이름
    @Getter
    private final String contact; // 가맹점 연락처

    @Getter
    private final String bankName;

    @Getter
    private final String bankAccountNumber;

    @Getter
    private final boolean isValid;


    public static Franchise generateFranchisee(
            FranchiseeId franchiseeId,
            Name name,
            Contact contact,
            BankName bankName,
            BankAccountNumber bankAccountNumber,
            IsValid isValid) {
        return new Franchise(
                franchiseeId.franchiseeId,
                name.name,
                contact.contact,
                bankName.bankName,
                bankAccountNumber.bankAccountNumber,
                isValid.isValid
        );
    }

    @Value
    public static class FranchiseeId {
        public FranchiseeId(String value) {
            this.franchiseeId = value;
        }

        String franchiseeId;
    }

    @Value
    public static class Name {
        public Name(String value) {
            this.name = value;
        }

        String name;
    }

    @Value
    public static class Contact {
        public Contact(String value) {
            this.contact = value;
        }

        String contact;
    }

    @Value
    public static class BankName {
        public BankName(String value) {
            this.bankName = value;
        }

        String bankName;
    }

    @Value
    public static class BankAccountNumber {
        public BankAccountNumber(String value) {
            this.bankAccountNumber = value;
        }

        String bankAccountNumber;
    }
    @Value
    public static class IsValid {
        public IsValid(boolean value) {
            this.isValid = value;
        }

        boolean isValid;
    }


}
