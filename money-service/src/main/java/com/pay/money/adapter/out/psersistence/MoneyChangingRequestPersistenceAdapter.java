package com.pay.money.adapter.out.psersistence;

import com.pay.common.PersistenceAdapter;
import com.pay.money.application.port.out.CreateMemberMoneyPort;
import com.pay.money.application.port.out.DecreaseMoneyPort;
import com.pay.money.application.port.out.IncreaseMoneyPort;
import com.pay.money.domain.MemberMoney;
import com.pay.money.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@PersistenceAdapter
@RequiredArgsConstructor
public class MoneyChangingRequestPersistenceAdapter implements IncreaseMoneyPort, DecreaseMoneyPort, CreateMemberMoneyPort {
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
    public MemberMoneyJpaEntity decreaseMoney(MemberMoney.MembershipId membershipId, int decreaseMoneyAmount) {
        MemberMoneyJpaEntity entity;
        try {
            List<MemberMoneyJpaEntity> entityList = springDataMemberMoneyRepository.findByMembershipId(Long.parseLong(membershipId.getMembershipId()));
            entity = entityList.get(0);
            if (entity.getBalance() + decreaseMoneyAmount < 0){
                return null;
            }
            entity.setBalance(entity.getBalance() + decreaseMoneyAmount);
            return springDataMemberMoneyRepository.save(entity);
        } catch (Exception e) {
            entity = new MemberMoneyJpaEntity(Long.parseLong(membershipId.getMembershipId()),
                    decreaseMoneyAmount,"");
            return springDataMemberMoneyRepository.save(entity);
        }
    }

    @Override
    public MemberMoneyJpaEntity increaseMoney(MemberMoney.MembershipId membershipId, int increaseMoneyAmount) {
        MemberMoneyJpaEntity entity;
        try {
            List<MemberMoneyJpaEntity> entityList = springDataMemberMoneyRepository.findByMembershipId(Long.parseLong(membershipId.getMembershipId()));
            entity = entityList.get(0);
            entity.setBalance(entity.getBalance() + increaseMoneyAmount);
            return springDataMemberMoneyRepository.save(entity);

        } catch (Exception e) {
            entity = new MemberMoneyJpaEntity(Long.parseLong(membershipId.getMembershipId()),
                    increaseMoneyAmount,"");
            return springDataMemberMoneyRepository.save(entity);
        }

    }

    @Override
    public void createMemberMoney(MemberMoney.MembershipId memberId, MemberMoney.MoneyAggregateIdentifier aggregateIdentifier) {
        MemberMoneyJpaEntity entity = new MemberMoneyJpaEntity(
                Long.parseLong(memberId.getMembershipId()),
                0,
                aggregateIdentifier.getMoneyAggregateIdentifier()
        );
        springDataMemberMoneyRepository.save(entity);
    }
}
