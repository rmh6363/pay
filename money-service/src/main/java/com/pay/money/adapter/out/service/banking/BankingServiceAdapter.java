package com.pay.money.adapter.out.service.banking;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pay.common.CommonHttpClient;
import com.pay.common.ExternalSystemAdapter;
import com.pay.money.adapter.out.service.membership.Membership;
import com.pay.money.application.port.out.banking.BankingPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

@ExternalSystemAdapter
@RequiredArgsConstructor
public class BankingServiceAdapter implements BankingPort {

    private final CommonHttpClient bankingServiceHttpClient;

    @Value("${service.banking.url}")
    private String bankingServiceEndpoint;


    @Override
    public BankingInfo getMembershipBankingInfo(String membershipId) {
        String buildUrl = String.join("/", this.bankingServiceEndpoint,"banking", "account",membershipId);
        try {
            String jsonResponse = bankingServiceHttpClient.sendGetRequest(buildUrl).body();
            ObjectMapper mapper = new ObjectMapper();
            RegisteredBankAccountResponseBody registeredBankAccount = mapper.readValue(jsonResponse, RegisteredBankAccountResponseBody.class);
            return new BankingInfo(
                    registeredBankAccount.getRegisteredBankAccountId()
                    , registeredBankAccount.getAggregateIdentifier()
                    , registeredBankAccount.getMembershipId()
                    , registeredBankAccount.getBankName()
                    , registeredBankAccount.getBankAccountNumber());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}
