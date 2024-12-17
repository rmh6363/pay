package com.pay.franchise.application.port.in;

import com.pay.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class RegisteredFranchiseCommand extends SelfValidating<RegisteredFranchiseCommand> {
   private final String name; // 가맹점 이름

   private final String contact; // 가맹점 연락처


   private final String bankName;


   private final String bankAccountNumber;

   private final boolean isValid;
}
