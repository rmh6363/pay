package com.pay.franchise.application.port.in;

import com.pay.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class ModifyFranchiseCommand extends SelfValidating<ModifyFranchiseCommand> {
   private String franchiseeId;
   private String name; // 가맹점 이름

   private String contact; // 가맹점 연락처

   private String bankName;

   private String bankAccountNumber;
   private boolean isValid;
}
