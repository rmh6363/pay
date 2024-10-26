package com.pay.membership.application.service;

import com.pay.membership.adapter.out.psersistence.MembershipJpaEntity;
import com.pay.membership.adapter.out.psersistence.MembershipMapper;
import com.pay.membership.application.port.in.RegisterMembershipCommand;
import com.pay.membership.application.port.in.RegisterMembershipUseCase;
import com.pay.membership.application.port.out.RegisterMembershipPort;
import com.pay.membership.domain.Membership;
import common.UseCase;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
@UseCase
@RequiredArgsConstructor
@Transactional
public class RegisterMembershipService implements RegisterMembershipUseCase {
    private final RegisterMembershipPort registerMembershipPort;

    private final MembershipMapper membershipMapper;
    @Override
    public Membership registerMembership(RegisterMembershipCommand command) {
        // command -> DB

        // biz logic -> DB

        // external system
        // port, adapter
       MembershipJpaEntity jpaEntity =  registerMembershipPort.createMembership(
                new Membership.MembershipName(command.getName()),
                new Membership.MembershipEmail(command.getEmail()),
                new Membership.MembershipAddress(command.getAddress()),
                new Membership.MembershipIsValid(command.getIsValid()),
                new Membership.MembershipAggregateIdentifier(command.getAggregateIdentifier())
        );


        // entity -> Membership
        return membershipMapper.mapToDomainEntity(jpaEntity);
    }
}
