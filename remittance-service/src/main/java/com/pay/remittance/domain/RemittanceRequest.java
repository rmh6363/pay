package com.pay.remittance.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RemittanceRequest {
    @Getter
    private final String remittanceRequestId;
    @Getter
    private final String remittanceFromMembershipId;

    @Getter
    private final String toBankName;
    @Getter
    private final String toBankAccountNumber;
    @Getter
    private final int remittanceType;
    @Getter
    private final int amount;
    @Getter
    private final String remittanceStatus;

    @Value
    public static class RemittanceRequestId {
        public RemittanceRequestId(String value) {
            this.remittanceRequestId = value;
        }

        String remittanceRequestId;
    }

    @Value
    public static class RemittanceFromMembershipId {
        public RemittanceFromMembershipId(String value) {
            this.remittanceFromMembershipId = value;
        }

        String remittanceFromMembershipId;
    }

    @Value
    public static class ToBankName {
        public ToBankName(String value) {
            this.toBankName = value;
        }

        String toBankName;
    }

    @Value
    public static class ToBankAccountNumber {
        public ToBankAccountNumber(String value) {
            this.toBankAccountNumber = value;
        }

        String toBankAccountNumber;
    }

    @Value
    public static class RemittanceStatus {
        public RemittanceStatus(String value) {
            this.remittanceStatus = value;
        }

        String remittanceStatus;
    }

    @Value
    public static class RemittanceType {
        public RemittanceType(int value) {
            this.remittanceType = value;
        }

        int remittanceType;
    }

    @Value
    public static class Amount {
        public Amount(int value) {
            this.amount = value;
        }

        int amount;
    }

    public static RemittanceRequest generateRemittanceRequest(
            RemittanceRequest.RemittanceRequestId remittanceRequestId,
            RemittanceRequest.RemittanceFromMembershipId remittanceFromMembershipId,
            RemittanceRequest.ToBankName toBankName,
            RemittanceRequest.ToBankAccountNumber bankAccountNumber,
            RemittanceRequest.RemittanceType remittanceType,
            RemittanceRequest.Amount amount,
            RemittanceRequest.RemittanceStatus remittanceStatus
    ) {
        return new RemittanceRequest(
                remittanceRequestId.remittanceRequestId,
                remittanceFromMembershipId.remittanceFromMembershipId,
                toBankName.toBankName,
                bankAccountNumber.toBankAccountNumber,
                remittanceType.remittanceType,
                amount.amount,
                remittanceStatus.remittanceStatus
        );
    }
}
