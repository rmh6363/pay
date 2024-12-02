package com.pay.money.adapter.out.psersistence;

import com.pay.common.PersistenceAdapter;
import com.pay.money.application.port.out.IncreaseMoneyPort;
import com.pay.money.domain.MemberMoney;
import com.pay.money.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@PersistenceAdapter
@RequiredArgsConstructor
public class MoneyChangingRequestPersistenceAdapter implements IncreaseMoneyPort {
    private final SpringDataMoneyChangingRepository springDataMoneyChangingRepository;
    private final SpringDataMemberMoneyRepository springDataMemberMoneyRepository;



    @Override
    public MoneyChangingRequestJpaEntity createMoneyChangingRequest(MoneyChangingRequest.TargetMembershipId targetMembershipId, MoneyChangingRequest.MoneyChangingType moneyChangingType, MoneyChangingRequest.ChangingMoneyAmount changingMoneyAmount, MoneyChangingRequest.MoneyChangingStatus moneyChangingStatus, MoneyChangingRequest.Uuid uuid) {
        return springDataMoneyChangingRepository.save(new MoneyChangingRequestJpaEntity(
                targetMembershipId.getTargetMembershipId(),
                moneyChangingType.getMoneyChangingType(),
                changingMoneyAmount.getChangingMoneyAmount(),
                new Timestamp(System.currentTimeMillis()),
                moneyChangingStatus.getChangingMoneyStatus(),
                UUID.randomUUID()
        ));
    }

    @Override
    public MemberMoneyJpaEntity increaseMoney(MemberMoney.MembershipId membershipId, int increaseMoneyAmount) {
        MemberMoneyJpaEntity entity ;
        try {
            List<MemberMoneyJpaEntity> entityList = springDataMemberMoneyRepository.findAllByMembershipId(Long.parseLong(membershipId.getMembershipId()));
            entity = entityList.get(0);
            entity.setBalance(entity.getBalance()+increaseMoneyAmount);
            return springDataMemberMoneyRepository.save(entity);

        }catch (Exception e){
            entity = new MemberMoneyJpaEntity(Long.parseLong(membershipId.getMembershipId()),
                    increaseMoneyAmount);
            return springDataMemberMoneyRepository.save(entity);
        }

    }
}
