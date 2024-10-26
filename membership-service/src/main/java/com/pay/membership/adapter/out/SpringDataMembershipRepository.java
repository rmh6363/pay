package com.pay.membership.adapter.out;

import com.pay.membership.adapter.out.psersistence.MembershipJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataMembershipRepository extends JpaRepository<MembershipJpaEntity,Long> {
}
