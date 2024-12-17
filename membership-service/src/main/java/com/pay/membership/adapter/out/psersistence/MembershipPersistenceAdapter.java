package com.pay.membership.adapter.out.psersistence;

import com.pay.common.PersistenceAdapter;
import com.pay.common.vault.VaultAdapter;
import com.pay.membership.application.port.out.FindMembershipPort;
import com.pay.membership.application.port.out.ModifyMembershipPort;
import com.pay.membership.application.port.out.RegisterMembershipPort;

import com.pay.membership.domain.Membership;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class MembershipPersistenceAdapter implements RegisterMembershipPort, FindMembershipPort, ModifyMembershipPort {
    private final SpringDataMembershipRepository membershipRepository;

    private final VaultAdapter vaultAdapter;

    @Override
    public MembershipJpaEntity createMembership(Membership.MembershipName membershipName, Membership.MembershipEmail membershipEmail, Membership.MembershipAddress membershipAddress, Membership.MembershipIsValid membershipIsValid, Membership.MembershipAggregateIdentifier membershipAggregateIdentifier) {
        String encryptedEmail = vaultAdapter.encrypt(membershipEmail.getEmailValue());
        String encryptedAddress = vaultAdapter.encrypt(membershipAddress.getAddressValue());
        String encryptedName = vaultAdapter.encrypt(membershipName.getNameValue());

        MembershipJpaEntity entity = new MembershipJpaEntity(
                encryptedName,
                encryptedEmail,
                encryptedAddress,
                membershipIsValid.isValidValue(),
                "",
                membershipAggregateIdentifier.getAggregateIdentifier()
        );
        membershipRepository.save(entity);
        MembershipJpaEntity clone = entity.clone();
        clone.setEmail(membershipEmail.getEmailValue());
        clone.setAddress(membershipAddress.getAddressValue());
        clone.setName(membershipName.getNameValue());
        return clone;

    }

    @Override
    public MembershipJpaEntity findMembership(Membership.MembershipId membershipId) {
        MembershipJpaEntity membershipJpaEntity = membershipRepository.getById(Long.parseLong(membershipId.getMembershipId()));
        String encryptedEmail = membershipJpaEntity.getEmail();
        String decryptedEmail = vaultAdapter.decrypt(encryptedEmail);
        String encryptedAddress = membershipJpaEntity.getAddress();
        String decryptedAddress = vaultAdapter.decrypt(encryptedAddress);
        String encryptedName = membershipJpaEntity.getName();
        String decryptedName  = vaultAdapter.decrypt(encryptedName);

        MembershipJpaEntity clone = membershipJpaEntity.clone();
        clone.setEmail(decryptedEmail);
        clone.setAddress(decryptedAddress);
        clone.setName(decryptedName);
        return clone;
    }

    @Override
    public MembershipJpaEntity modifyMembership(Membership.MembershipId membershipId, Membership.MembershipName membershipName, Membership.MembershipEmail membershipEmail, Membership.MembershipAddress membershipAddress, Membership.MembershipIsValid membershipIsValid, Membership.MembershipRefreshToken membershipRefreshToken, Membership.MembershipAggregateIdentifier membershipAggregateIdentifier) {
        MembershipJpaEntity entity = membershipRepository.getById(Long.parseLong(membershipId.getMembershipId()));
        entity.setName(vaultAdapter.encrypt(membershipName.getNameValue()));
        entity.setEmail(vaultAdapter.encrypt(membershipEmail.getEmailValue()));
        entity.setAddress(vaultAdapter.encrypt(membershipAddress.getAddressValue()));
        entity.setAggregateIdentifier(membershipAggregateIdentifier.getAggregateIdentifier());
        entity.setValid(membershipIsValid.isValidValue());
        entity.setRefreshToken(membershipRefreshToken.getMembershipRefreshToken());
        return membershipRepository.save(entity);
    }
}
