package com.pay.money.adapter.out.psersistence;

import com.pay.common.PersistenceAdapter;
import com.pay.money.application.port.out.GetBalancePort;
import com.pay.money.application.port.out.IncreaseMoneyPort;
import com.pay.money.domain.MemberMoney;
import com.pay.money.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@PersistenceAdapter
@RequiredArgsConstructor
public class GetBalanceRequestPersistenceAdapter implements GetBalancePort {

    private final SpringDataMemberMoneyRepository springDataMemberMoneyRepository;


    @Override
    public MemberMoneyJpaEntity getBalance(MemberMoney.MembershipId membershipId) {
        MemberMoneyJpaEntity entity;
        List<MemberMoneyJpaEntity> entityList =  springDataMemberMoneyRepository.findByMembershipId(Long.parseLong(membershipId.getMembershipId()));
        if(entityList.size() == 0){
            entity = new MemberMoneyJpaEntity(
                    Long.parseLong(membershipId.getMembershipId()),
                    0, ""
            );
            entity = springDataMemberMoneyRepository.save(entity);
            return entity;
        }
        return  entityList.get(0);
    }
}
