package com.pay.franchise.adapter.in.web;

import com.pay.common.WebAdapter;
import com.pay.franchise.application.port.in.FindFranchiseCommand;
import com.pay.franchise.application.port.in.FindFranchiseUseCase;
import com.pay.franchise.application.port.in.ModifyFranchiseCommand;
import com.pay.franchise.application.port.in.ModifyFranchiseUseCase;
import com.pay.franchise.domain.Franchise;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class ModifyFranchiseController {

    private final ModifyFranchiseUseCase modifyFranchiseUseCase;

    @PostMapping(path = "/franchise/{franchiseId}")
    Franchise registerFranchise(@RequestBody ModifyFranchiseRequest request) {
        ModifyFranchiseCommand command = ModifyFranchiseCommand.builder().
                franchiseeId(request.getFranchiseeId()).
                bankName(request.getBankName()).
                bankAccountNumber(request.getBankAccountNumber()).
                contact(request.getContact()).
                isValid(request.isValid()).name(request.getName()).
                build();

        return modifyFranchiseUseCase.modifyFranchiseUseCase(command);
    }
}
