package com.pay.franchise.adapter.in.web;

import com.pay.common.WebAdapter;

import com.pay.franchise.application.port.in.FindFranchiseCommand;
import com.pay.franchise.application.port.in.FindFranchiseUseCase;
import com.pay.franchise.domain.Franchise;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class FindFranchiseController {

    private final FindFranchiseUseCase FindFranchiseUseCase;
    @GetMapping(path = "/franchise/{franchiseId}")
    Franchise registerFranchise(@PathVariable String franchiseId ){
        FindFranchiseCommand command = FindFranchiseCommand.builder().
                franchiseeId(franchiseId).
                build();

        return FindFranchiseUseCase.findFranchiseUseCase(command);
    }
}
