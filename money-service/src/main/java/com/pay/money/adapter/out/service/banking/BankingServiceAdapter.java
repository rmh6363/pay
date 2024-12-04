package com.pay.money.adapter.out.service.banking;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pay.common.CommonHttpClient;
import com.pay.common.ExternalSystemAdapter;
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
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writeValueAsString(new BankingInfoRequestBody(membershipId));
            CompletableFuture<HttpResponse<String>> responseFuture = bankingServiceHttpClient.sendPostRequest(buildUrl, requestBody);
            return responseFuture.thenApply(response -> {
                try {
                    // 응답 본문을 MoneyChangingResponseBody 객체로 변환
                    BankingInfo bankingInfo = mapper.readValue(response.body(), BankingInfo.class);
                    // 성공 여부 판단 (예: balance가 0보다 크면 성공)
                    return bankingInfo; // 성공 여부 반환
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).join(); // CompletableFuture의 결과를 동기적으로 대기
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}
