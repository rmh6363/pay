package com.pay.banking.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFirmBankingRequest {

    private String firmbankingRequestAggregateIdentifier;


    private int status;


}
