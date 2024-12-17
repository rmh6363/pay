package com.pay.banking.adapter.out.psersistence;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "request_firmbanking")
@Data
@NoArgsConstructor
public class FirmbankingRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long requestFirmbankingId;
    @Getter
    private String fromBankName;
    @Getter
    private String fromBankAccountNumber;
    @Getter
    private String toBankName;
    @Getter
    private String toBankAccountNumber;
    @Getter
    private int moneyAmount;
    @Getter
    private int firmbankingStatus; // 0: 요청 , 1: 완료, 2: 실패

    private String uuid;

    @Getter
    private String aggregateIdentifier;
    public FirmbankingRequestEntity( String fromBankName, String fromBankAccountNumber, String toBankName, String toBankAccountNumber, int moneyAmount, int firmbankingStatus, UUID uuid,String aggregateIdentifier) {

        this.fromBankName = fromBankName;
        this.fromBankAccountNumber = fromBankAccountNumber;
        this.toBankName = toBankName;
        this.toBankAccountNumber = toBankAccountNumber;
        this.moneyAmount = moneyAmount;
        this.firmbankingStatus = firmbankingStatus;
        this.uuid=uuid.toString();
        this.aggregateIdentifier=aggregateIdentifier;
    }

    @Override
    public String toString() {
        return "FirmbankingRequestEntity{" +
                "requestFirmbankingId=" + requestFirmbankingId +
                ", fromBankName='" + fromBankName + '\'' +
                ", fromBankAccountNumber='" + fromBankAccountNumber + '\'' +
                ", toBankName='" + toBankName + '\'' +
                ", toBankAccountNumber='" + toBankAccountNumber + '\'' +
                ", moneyAmount=" + moneyAmount +
                ", firmbankingStatus=" + firmbankingStatus +
                '}';
    }
}