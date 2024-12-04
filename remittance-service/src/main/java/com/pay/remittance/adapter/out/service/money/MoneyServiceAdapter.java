package com.pay.remittance.adapter.out.service.money;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pay.common.CommonHttpClient;
import com.pay.common.ExternalSystemAdapter;
import com.pay.remittance.application.port.out.money.MoneyInfo;
import com.pay.remittance.application.port.out.money.MoneyPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

@ExternalSystemAdapter
@RequiredArgsConstructor
public class MoneyServiceAdapter implements MoneyPort {

    private final CommonHttpClient moneyServiceHttpClient;

    @Value("${service.money.url}")
    private String moneyServiceEndpoint;

    @Override
    public MoneyInfo getMoneyInfo(String membershipId) {
        String buildUrl = String.join("/", this.moneyServiceEndpoint,"money" ,membershipId);
        try {
            String jsonResponse = moneyServiceHttpClient.sendGetRequest(buildUrl).body();
            ObjectMapper mapper = new ObjectMapper();
            MemberMoney mem = mapper.readValue(jsonResponse, MemberMoney.class);
            return new MoneyInfo(mem.getMembershipId(), mem.getBalance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean requestMoneyRecharging(String membershipId, int amount) {
        return false;
    }

    @Override
    public boolean requestMoneyIncrease(String membershipId, int amount) {
        String buildUrl = String.join("/", this.moneyServiceEndpoint,"money", "increase-async");
        try {
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writeValueAsString(new MoneyChangingRequestBody(membershipId,amount));
            CompletableFuture<HttpResponse<String>> responseFuture = moneyServiceHttpClient.sendPostRequest(buildUrl, requestBody);
            return responseFuture.thenApply(response -> {
                try {
                    // 응답 본문을 MoneyChangingResponseBody 객체로 변환
                    MoneyChangingResponseBody mem = mapper.readValue(response.body(), MoneyChangingResponseBody.class);
                    // 성공 여부 판단 (예: balance가 0보다 크면 성공)
                    return mem != null; // 성공 여부 반환
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).join(); // CompletableFuture의 결과를 동기적으로 대기
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean requestMoneyDecrease(String membershipId, int amount) {
        String buildUrl = String.join("/", this.moneyServiceEndpoint,"money" ,"decrease-async");
        
        try {
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writeValueAsString(new MoneyChangingRequestBody(membershipId,amount));
            CompletableFuture<HttpResponse<String>> responseFuture = moneyServiceHttpClient.sendPostRequest(buildUrl, requestBody);
            return responseFuture.thenApply(response -> {
                try {
                    // 응답 본문을 MoneyChangingResponseBody 객체로 변환
                    MoneyChangingResponseBody mem = mapper.readValue(response.body(), MoneyChangingResponseBody.class);
                    // 성공 여부 판단 (예: balance가 0보다 크면 성공)
                    return mem != null; // 성공 여부 반환
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).join(); // CompletableFuture의 결과를 동기적으로 대기
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}



