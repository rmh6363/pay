package com.pay.settlement.adapter.out.service.banking;

import com.pay.common.CommonHttpClient;
import com.pay.settlement.port.out.banking.GetRegisteredBankAccountPort;
import com.pay.settlement.port.out.banking.RegisteredBankAccountAggregateIdentifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BankingServiceAdapter implements GetRegisteredBankAccountPort {

    private final CommonHttpClient commonHttpClient;

    private final String bankingServiceUrl;

    public BankingServiceAdapter(CommonHttpClient commonHttpClient,
                                 @Value("${service.banking.url}") String membershipServiceUrl) {
        this.commonHttpClient = commonHttpClient;
        this.bankingServiceUrl = membershipServiceUrl;
    }

    @Override
    public RegisteredBankAccountAggregateIdentifier getRegisteredBankAccount(String membershipId){
        String url = String.join("/", bankingServiceUrl, "banking/account", membershipId);
        try {
            String jsonResponse = commonHttpClient.sendGetRequest(url).body();
            // json RegisteredBankAccount

            ObjectMapper mapper = new ObjectMapper();
            RegisteredBankAccount registeredBankAccount = mapper.readValue(jsonResponse, RegisteredBankAccount.class);

            return new RegisteredBankAccountAggregateIdentifier(
                    registeredBankAccount.getRegisteredBankAccountId()
                    , registeredBankAccount.getAggregateIdentifier()
                    , registeredBankAccount.getBankName()
                    , registeredBankAccount.getBankName()
                    , registeredBankAccount.getBankAccountNumber()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void requestFirmbanking(String bankName, String bankAccountNumber, int moneyAmount) {
        String url = String.join("/", bankingServiceUrl, "banking/firmbanking/request");
        try {
            ObjectMapper mapper = new ObjectMapper();
            RequestFirmbankingRequest request = new RequestFirmbankingRequest(
                    "pay",
                    "123-333-9999",
                    bankName,
                    bankAccountNumber,
                    moneyAmount);

            commonHttpClient.sendPostRequest(url, mapper.writeValueAsString(request));
            // FirmbankingRequest response = mapper.readValue(jsonResponse, new TypeReference<>() {});

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
