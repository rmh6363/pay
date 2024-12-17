package com.pay.membership.application.port.out;

import com.pay.membership.adapter.out.psersistence.MembershipJpaEntity;
import com.pay.membership.domain.JwtToken;
import com.pay.membership.domain.Membership;


public interface AuthMembershipPort {
    //membership id를 기준으로, jwt token 생성
    String generateJwtToken(
            Membership.MembershipId membershipId
    );
    //membership id를 기준으로, refresh token 생성
    String generateRefreshJwtToken(
            Membership.MembershipId membershipId
    );
    // jwtToken 유효한지 확인
    boolean validateJwtToken(String jwtToken);

    // jwtToken 으로, 어떤 멤버십 id 를 가지는지 확인
    Membership.MembershipId parseMembershipIdFromToken(String jwtToken);
}
