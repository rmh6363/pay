package com.pay.membership.adapter.in.web;

import com.pay.common.WebAdapter;
import com.pay.membership.application.port.in.*;
import com.pay.membership.domain.JwtToken;
import com.pay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class AuthMembershipController {
    private final AuthMembershipUseCase authMembershipUseCase;
    @PostMapping(path = "/membership/login")
    JwtToken loginMemebership(@RequestBody LoginMembershioRequest request ){
        LoginMembershipCommand command = LoginMembershipCommand.builder().
                membershipId(request.getMembershipId()).
                build();
        return authMembershipUseCase.loginMemebership(command);
    }
    @PostMapping(path = "/membership/refresh-token")
    JwtToken refreshToken(@RequestBody RefreshTokenRequest request ){
        RefreshTokenCommand command = RefreshTokenCommand.builder().
                refreshToken(request.getRefreshToken()).
                build();
        return authMembershipUseCase.refreshJwtTokenByRefreshToken(command);
    }

    @PostMapping(path = "/membership/token-validate")
    boolean validateToken(@RequestBody ValidateTokenRequest request ){
        ValidateTokenCommand command = ValidateTokenCommand.builder().
                jwtToken(request.getJwtToken()).
                build();
        return authMembershipUseCase.validateJwtToken(command);
    }
    @PostMapping(path = "/membership/getMembership-JwtToken")
    Membership getMembershipByJwtToken(@RequestBody ValidateTokenRequest request ){
        GetMembershipByJwtTokenCommand command = GetMembershipByJwtTokenCommand.builder().
                jwtToken(request.getJwtToken()).
                build();
        return authMembershipUseCase.getMembershipByJwtToken(command);
    }
}
