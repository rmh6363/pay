package com.pay.banking.adapter.axon.event;

import com.pay.common.SelfValidating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Data
public class FirmbankingRequestUpdatedEvent extends SelfValidating<FirmbankingRequestUpdatedEvent> {

    private int firmbankingStatus;


}
