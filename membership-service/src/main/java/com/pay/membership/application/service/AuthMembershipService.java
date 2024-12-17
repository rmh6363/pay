package com.pay.membership.application.service;

import com.pay.common.UseCase;
import com.pay.membership.adapter.out.psersistence.MembershipJpaEntity;
import com.pay.membership.adapter.out.psersistence.MembershipMapper;
import com.pay.membership.application.port.in.*;
import com.pay.membership.application.port.out.AuthMembershipPort;
import com.pay.membership.application.port.out.FindMembershipPort;
import com.pay.membership.application.port.out.ModifyMembershipPort;
import com.pay.membership.domain.JwtToken;
import com.pay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class AuthMembershipService implements AuthMembershipUseCase {
    private final AuthMembershipPort authMembershipPort;

    private final FindMembershipPort findMembershipPort;

    private final ModifyMembershipPort membershipPort;

    private final MembershipMapper membershipMapper;
    @Override
    public JwtToken loginMemebership(LoginMembershipCommand command) {

        String membershipId= command.getMembershipId();
        MembershipJpaEntity entity = findMembershipPort.findMembership(new Membership.MembershipId(membershipId));
        if (entity.isValid()){
            String jwtToken = authMembershipPort.generateJwtToken(
                    new Membership.MembershipId(membershipId)
            );
            String refreshToken = authMembershipPort.generateRefreshJwtToken(
                    new Membership.MembershipId(membershipId)
            );
            // membership jpa에 refresh token 저장
            membershipPort.modifyMembership(
                    new Membership.MembershipId(membershipId),
                    new Membership.MembershipName(entity.getName()),
                    new Membership.MembershipEmail(entity.getEmail()),
                    new Membership.MembershipAddress(entity.getAddress()),
                    new Membership.MembershipIsValid(entity.isValid()),
                    new Membership.MembershipRefreshToken(refreshToken),
                    new Membership.MembershipAggregateIdentifier(entity.getAggregateIdentifier())
            );
            return JwtToken.generateJwtToken(
                    new JwtToken.MembershipId(membershipId),
                    new JwtToken.MembershipJwtToken(jwtToken),
                    new JwtToken.MembershipRefreshToken(refreshToken)
            );
        }

        return null;
    }

    @Override
    public JwtToken refreshJwtTokenByRefreshToken(RefreshTokenCommand command) {
        String requestRefreshToken =command.getRefreshToken();
        boolean isValid = authMembershipPort.validateJwtToken(requestRefreshToken);
        if (isValid){
            Membership.MembershipId membershipId = authMembershipPort.parseMembershipIdFromToken(requestRefreshToken);
            String membershipIdStr = membershipId.getMembershipId();

           MembershipJpaEntity membershipJpaEntity =findMembershipPort.findMembership(membershipId);
           // 고객의 refresh token 정보와, 요청받은 refresh token 정보가 일치하는지 확인
           if (!membershipJpaEntity.getRefreshToken().equals(requestRefreshToken)){
               return null;
           }
           if (membershipJpaEntity.isValid()){
               String newJwtToken = authMembershipPort.generateJwtToken(
                       new Membership.MembershipId(membershipIdStr)
               );
               return JwtToken.generateJwtToken(
                       new JwtToken.MembershipId(membershipIdStr),
                       new JwtToken.MembershipJwtToken(newJwtToken),
                       new JwtToken.MembershipRefreshToken(requestRefreshToken)
               );
           }
        }

        return null;
    }

    @Override
    public boolean validateJwtToken(ValidateTokenCommand command) {
        String jwtToken = command.getJwtToken();
        return authMembershipPort.validateJwtToken(jwtToken);
    }

    @Override
    public Membership getMembershipByJwtToken(GetMembershipByJwtTokenCommand command) {
        String jwtToken = command.getJwtToken();
        boolean isValid = authMembershipPort.validateJwtToken(jwtToken);
        if (isValid) {
            Membership.MembershipId membershipId = authMembershipPort.parseMembershipIdFromToken(jwtToken);
            MembershipJpaEntity membershipJpaEntity = findMembershipPort.findMembership(membershipId);
            // 고객의 refresh token 정보와, 요청받은 refresh token 정보가 일치하는지 확인
            if (!membershipJpaEntity.getRefreshToken().equals(command.getJwtToken())) {
                return null;
            }
            return membershipMapper.mapToDomainEntity(membershipJpaEntity);
        }
        return null;
    }
}
