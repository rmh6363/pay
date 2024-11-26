package com.pay.banking.adapter.out.psersistence;


import com.pay.banking.domain.FirmBankingRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class FirmbankingRequestMapper {
    public FirmBankingRequest mapToDomainEntity(FirmbankingRequestEntity requestFirmbankingEntity, UUID uuid){
        return FirmBankingRequest.generateFirmbankingRequest(
                new FirmBankingRequest.FirmbankingRequestId(requestFirmbankingEntity.getRequestFirmbankingId()+""),
                new FirmBankingRequest.FromBankName(requestFirmbankingEntity.getFromBankName()),
                new FirmBankingRequest.FromBankAccountNumber(requestFirmbankingEntity.getFromBankAccountNumber()),
                new FirmBankingRequest.ToBankName(requestFirmbankingEntity.getToBankName()),
                new FirmBankingRequest.ToBankAccountNumber(requestFirmbankingEntity.getToBankAccountNumber()),
                new FirmBankingRequest.MoneyAmount(requestFirmbankingEntity.getMoneyAmount()),
                new FirmBankingRequest.FirmbankingStatus(requestFirmbankingEntity.getFirmbankingStatus()),
                uuid
        );
    }
    // 리스트 조회 매핑
    public List<FirmBankingRequest> mapToDomainEntities(List<FirmbankingRequestEntity> requestFirmbankingEntities, UUID uuid) {
        return requestFirmbankingEntities.stream()
                .map(entity -> mapToDomainEntity(entity, uuid))
                .collect(Collectors.toList());
    }

}

