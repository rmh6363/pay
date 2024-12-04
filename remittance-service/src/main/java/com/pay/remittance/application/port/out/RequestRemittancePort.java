package com.pay.remittance.application.port.out;


import com.pay.remittance.adapter.out.persistence.RemittanceRequestJpaEntity;
import com.pay.remittance.application.port.in.RequestRemittanceCommand;

public interface RequestRemittancePort {

    RemittanceRequestJpaEntity createRemittanceRequestHistory(RequestRemittanceCommand command);
    boolean saveRemittanceRequestHistory(RemittanceRequestJpaEntity entity);
}
