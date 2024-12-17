package com.pay.membership.application.service;

import com.pay.common.UseCase;
import com.pay.membership.adapter.axon.command.MembershipCreatedCommand;
import com.pay.membership.adapter.out.psersistence.MembershipJpaEntity;
import com.pay.membership.adapter.out.psersistence.MembershipMapper;
import com.pay.membership.application.port.in.RegisterMembershipCommand;
import com.pay.membership.application.port.in.RegisterMembershipUseCase;
import com.pay.membership.application.port.out.RegisterMembershipPort;
import com.pay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;

import javax.transaction.Transactional;
@UseCase
@RequiredArgsConstructor
@Transactional
public class RegisterMembershipService implements RegisterMembershipUseCase {
    private final RegisterMembershipPort registerMembershipPort;

    private final MembershipMapper membershipMapper;

    private final CommandGateway commandGateway;
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
                new Membership.MembershipAggregateIdentifier("")
        );


        // entity -> Membership
        return membershipMapper.mapToDomainEntity(jpaEntity);
    }

    @Override
    public void registerMembershipEvent(RegisterMembershipCommand command) {
        MembershipCreatedCommand axonCommand = new MembershipCreatedCommand(
                command.getName(),
                command.getEmail(),
                command.getAddress(),
                command.getIsValid(),
                ""
        );
        commandGateway.send(axonCommand).whenComplete((result, throwable) -> {
            if (throwable != null) {
                throw new RuntimeException(throwable);
            } else {
                registerMembershipPort.createMembership(
                        new Membership.MembershipName(command.getName()),
                        new Membership.MembershipEmail(command.getEmail()),
                        new Membership.MembershipAddress(command.getAddress()),
                        new Membership.MembershipIsValid(command.getIsValid()),
                        new Membership.MembershipAggregateIdentifier(result.toString())
                );
            }
        });

    }
}
