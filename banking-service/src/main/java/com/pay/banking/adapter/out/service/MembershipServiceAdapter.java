package com.pay.banking.adapter.out.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pay.banking.application.port.out.GetMembershipPort;
import com.pay.banking.application.port.out.MembershipStatus;
import com.pay.common.CommonHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MembershipServiceAdapter implements GetMembershipPort {
    private final CommonHttpClient httpClient;

    private final String membershipServiceUrl;

    public MembershipServiceAdapter(CommonHttpClient httpClient, @Value("${service.membership.url}") String membershipServiceUrl) {
        this.httpClient = httpClient;
        this.membershipServiceUrl = membershipServiceUrl;
    }


    @Override
    public MembershipStatus getMembership(String membershipId) {
        String url = String.join("/", membershipServiceUrl,"membership", membershipId);
        try {
            String jsonResponse = httpClient.sendGetRequest(url).body();

            // json Membership
            ObjectMapper mapper = new ObjectMapper();
            Membership membership = mapper.readValue(jsonResponse,Membership.class);
            if (membership.isValid()){
                return new MembershipStatus(membership.getMembershipId(),membership.getAggregateIdentifier(),true);
            }else {
                return new MembershipStatus(membership.getMembershipId(),membership.getAggregateIdentifier(),false);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
