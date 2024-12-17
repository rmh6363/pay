package com.pay.money.query.application.service;

import com.pay.common.UseCase;
import com.pay.money.query.adapter.axon.QueryMoneySumByAddress;
import com.pay.money.query.application.port.in.QueryMoneySumByRegionQuery;
import com.pay.money.query.application.port.in.QueryMoneySumByRegionUseCase;
import com.pay.money.query.domain.MoneySumByRegion;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryGateway;

import javax.transaction.Transactional;
import java.util.concurrent.ExecutionException;

@UseCase
@RequiredArgsConstructor
@Transactional
public class QueryMoneySumByRegionService implements QueryMoneySumByRegionUseCase {
    private final QueryGateway queryGateway;
    @Override
    public MoneySumByRegion queryMoneySumByRegion(QueryMoneySumByRegionQuery query) {
        try {
            return queryGateway.query(new QueryMoneySumByAddress(query.getAddress())
                    , MoneySumByRegion.class).get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
