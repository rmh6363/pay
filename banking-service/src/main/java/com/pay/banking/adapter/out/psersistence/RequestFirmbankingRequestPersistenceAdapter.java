package com.pay.banking.adapter.out.psersistence;

import com.pay.banking.application.port.out.FindBankAccountPort;
import com.pay.banking.application.port.out.FindFirmbankingPort;
import com.pay.banking.application.port.out.RegisterBankAccountPort;
import com.pay.banking.application.port.out.RequestFirmbankingPort;
import com.pay.banking.domain.FirmBankingRequest;
import com.pay.banking.domain.RegisteredBankAccount;
import com.pay.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@PersistenceAdapter
@RequiredArgsConstructor
public class RequestFirmbankingRequestPersistenceAdapter implements RequestFirmbankingPort, FindFirmbankingPort {
    private final SpringDataFirmbankingRequestRepository repository;


    @Override
    public FirmbankingRequestEntity createFirmbankingRequest(FirmBankingRequest.FromBankName fromBankName, FirmBankingRequest.FromBankAccountNumber fromBankAccountNumber, FirmBankingRequest.ToBankName toBankName, FirmBankingRequest.ToBankAccountNumber toBankAccountNumber, FirmBankingRequest.MoneyAmount moneyAmount, FirmBankingRequest.FirmbankingStatus firmbankingStatus,FirmBankingRequest.AggregateIdentifier aggregateIdentifier) {
        FirmbankingRequestEntity entity =repository.save(new FirmbankingRequestEntity(
                fromBankName.getFromBankName(),
                fromBankAccountNumber.getFromBankAccountNumber(),
                toBankName.getToBankName(),
                toBankAccountNumber.getToBankAccountNumber(),
                moneyAmount.getMoneyAmount(),
                firmbankingStatus.getFirmbankingStatus(),
                UUID.randomUUID(),
                aggregateIdentifier.getAggregateIdentifier()
        ));
        return entity;
    }

    @Override
    public FirmbankingRequestEntity modifyFirmbankingRequest(FirmbankingRequestEntity entity) {
        return repository.save(entity);
    }

    @Override
    public FirmbankingRequestEntity getFirmbankingRequest(FirmBankingRequest.AggregateIdentifier aggregateIdentifier) {
        List<FirmbankingRequestEntity> entityList = repository.findByAggregateIdentifier(aggregateIdentifier.getAggregateIdentifier());
        if (entityList.size() >= 1){
            return entityList.get(0);
        }
        return null;
    }


    @Override
    public List<FirmbankingRequestEntity> findfirmbanking(FirmBankingRequest.FromBankAccountNumber fromBankAccountNumber, FirmBankingRequest.FromBankName fromBankName) {
        return repository.findByBankDetails(fromBankAccountNumber.getFromBankAccountNumber(),fromBankName.getFromBankName());
    }
}
