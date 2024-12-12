package com.pay.settlement.adapter.out.service.franchise;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pay.common.CommonHttpClient;
import com.pay.settlement.adapter.out.service.FinishSettlementRequest;
import com.pay.settlement.adapter.out.service.payment.Payment;
import com.pay.settlement.port.out.franchise.GetRegisteredFranchisePort;
import com.pay.settlement.port.out.franchise.RegisteredFranchise;
import com.pay.settlement.port.out.payment.PaymentPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FranchiseServiceAdapter implements GetRegisteredFranchisePort {

    private final CommonHttpClient commonHttpClient;

    private final String franchiseServiceUrl;

    public FranchiseServiceAdapter(CommonHttpClient commonHttpClient,
                                   @Value("${service.franchise.url}") String franchiseServiceUrl) {
        this.commonHttpClient = commonHttpClient;
        this.franchiseServiceUrl = franchiseServiceUrl;
    }


    @Override
    public RegisteredFranchise getRegisteredFranchisePort(String franchiseeId) {
        String url = String.join("/", franchiseServiceUrl, "franchise", franchiseeId);

        try {
            String jsonResponse = commonHttpClient.sendGetRequest(url).body();
            ObjectMapper mapper = new ObjectMapper();
            RegisteredFranchise registeredFranchise = mapper.readValue(jsonResponse, RegisteredFranchise.class);
            return registeredFranchise;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
