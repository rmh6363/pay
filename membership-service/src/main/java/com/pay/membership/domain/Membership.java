package com.pay.membership.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Membership {

    @Getter private final String membershipId;

    @Getter private final String name;

    @Getter private final String email;

    @Getter private final String address;

    @Getter private final Boolean isValid;

    @Getter private final Boolean isCorp;

    @Value
    public static class MembershipId{
        public MembershipId(String value){
            this.membershipId=value;
        }
        String membershipId;
    }

}
