package com.pay.remittance.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataRemittanceRequestRepository extends JpaRepository<RemittanceRequestJpaEntity, Long> {
    List<RemittanceRequestJpaEntity> findByFromMembershipId(String membershipId);

}
