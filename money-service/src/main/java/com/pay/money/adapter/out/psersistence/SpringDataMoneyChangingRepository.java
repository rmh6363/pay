package com.pay.money.adapter.out.psersistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataMoneyChangingRepository extends JpaRepository<MoneyChangingRequestJpaEntity,Long> {

}
