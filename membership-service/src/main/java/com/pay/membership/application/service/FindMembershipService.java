package com.pay.membership.application.service;

import com.pay.common.UseCase;
import com.pay.membership.adapter.out.psersistence.MembershipJpaEntity;
import com.pay.membership.adapter.out.psersistence.MembershipMapper;
import com.pay.membership.application.port.in.FindMembershipCommand;
import com.pay.membership.application.port.in.FindMembershipUseCase;
import com.pay.membership.application.port.out.FindMembershipPort;
import com.pay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class FindMembershipService implements FindMembershipUseCase {
    private final FindMembershipPort findMembershipPort;
    private final MembershipMapper membershipMapper;

    @Override
    public Membership findMembership(FindMembershipCommand command) {
        MembershipJpaEntity jpaEntity =findMembershipPort.findMembership(new Membership.MembershipId(command.getMembershipId()));
        return  membershipMapper.mapToDomainEntity(jpaEntity);
    }
}