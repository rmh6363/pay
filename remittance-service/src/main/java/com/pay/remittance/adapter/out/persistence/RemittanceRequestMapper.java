package com.pay.remittance.adapter.out.persistence;


import com.pay.remittance.domain.RemittanceRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RemittanceRequestMapper {
    public RemittanceRequest mapToDomainEntity(RemittanceRequestJpaEntity remittanceRequestJpaEntity) {
        return RemittanceRequest.generateRemittanceRequest(
                new RemittanceRequest.RemittanceRequestId(remittanceRequestJpaEntity.getFromMembershipId()),
                new RemittanceRequest.RemittanceFromMembershipId(remittanceRequestJpaEntity.getFromMembershipId()),
                new RemittanceRequest.ToBankName(remittanceRequestJpaEntity.getToBankName()),
                new RemittanceRequest.ToBankAccountNumber(remittanceRequestJpaEntity.getToBankAccountNumber()),
                new RemittanceRequest.RemittanceType(remittanceRequestJpaEntity.getRemittanceType()),
                new RemittanceRequest.Amount(remittanceRequestJpaEntity.getAmount()),
                new RemittanceRequest.RemittanceStatus(remittanceRequestJpaEntity.getRemittanceStatus())

        );
    }

    // 리스트 조회 매핑
    public List<RemittanceRequest> mapToDomainEntities(List<RemittanceRequestJpaEntity> remittanceRequestJpaEntities) {
        return remittanceRequestJpaEntities.stream()
                .map(entity -> mapToDomainEntity(entity))
                .collect(Collectors.toList());
    }

}
