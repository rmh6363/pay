package com.pay.franchise.application.port.in;

import com.pay.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class FindFranchiseCommand extends SelfValidating<FindFranchiseCommand> {
   private final String franchiseeId;

}
