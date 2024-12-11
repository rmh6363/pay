package com.pay.franchise.adapter.out.psersistence;

import com.pay.franchise.domain.Franchise;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "franchise")
@Data
@NoArgsConstructor
public class FranchiseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long franchiseeId; // 가맹점 고유 ID

    @Getter
    private String name; // 가맹점 이름

    @Getter
    private String contact; // 가맹점 연락처

    @Getter
    private String bankName; // 은행 이름

    @Getter
    private String bankAccountNumber; // 은행 계좌 번호

    @Getter
    private boolean isValid;

    public FranchiseEntity(String name, String contact, String bankName, String bankAccountNumber, boolean isValid) {

        this.name = name;
        this.contact = contact;
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
        this.isValid = isValid;
    }
}