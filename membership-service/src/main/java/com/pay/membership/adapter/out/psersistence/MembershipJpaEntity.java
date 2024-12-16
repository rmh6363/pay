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
    private String refreshToken;
    private String aggregateIdentifier;

    public MembershipJpaEntity(String name, String email, String address, boolean isValid, String refreshToken, String aggregateIdentifier) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.isValid = isValid;
        this.refreshToken = refreshToken;
        this.aggregateIdentifier = aggregateIdentifier;
    }

    ;

    public MembershipJpaEntity clone() {
        return new MembershipJpaEntity(this.membershipId, this.name, this.email, this.address, this.isValid, this.refreshToken, this.aggregateIdentifier);
    }

}