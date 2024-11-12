package com.pay.membership.adapter.out.psersistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "membership")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MembershipJpaEntity {

    @Id
    @GeneratedValue
    private Long membershipId;

    private String name;

    private String address;

    private String email;

    private boolean isValid;

    private String aggregateIdentifier;

    public MembershipJpaEntity(String name,  String email ,String address, boolean isValid, String aggregateIdentifier) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.isValid = isValid;
        this.aggregateIdentifier = aggregateIdentifier;
    }

    @Override
    public String toString() {
        return "MembershipJpaEntity{" +
                "membershipId=" + membershipId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", isValid=" + isValid +
                ", aggregateIdentifier='" + aggregateIdentifier + '\'' +
                '}';
    }
}