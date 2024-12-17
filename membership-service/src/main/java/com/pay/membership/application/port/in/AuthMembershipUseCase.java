package com.pay.membership.application.port.in;

import com.pay.membership.domain.JwtToken;
import com.pay.membership.domain.Membership;


public interface AuthMembershipUseCase {
    JwtToken loginMemebership(LoginMembershipCommand command);
    JwtToken refreshJwtTokenByRefreshToken(RefreshTokenCommand command);

    boolean validateJwtToken(ValidateTokenCommand command);

    Membership getMembershipByJwtToken(GetMembershipByJwtTokenCommand command);

}
