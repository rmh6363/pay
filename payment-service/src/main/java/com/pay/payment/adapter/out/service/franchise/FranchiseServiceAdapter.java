package com.pay.payment.adapter.out.service.franchise;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pay.common.CommonHttpClient;
import com.pay.payment.adapter.out.service.membership.Membership;
import com.pay.payment.application.port.out.franchise.FranchiseStatus;
import com.pay.payment.application.port.out.franchise.GetFranchisePort;
import com.pay.payment.application.port.out.membership.GetMembershipPort;
import com.pay.payment.application.port.out.membership.MembershipStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FranchiseServiceAdapter implements GetFranchisePort {

    private final CommonHttpClient commonHttpClient;

    private final String franchiseServiceUrl;

    public FranchiseServiceAdapter(CommonHttpClient commonHttpClient,
                                   @Value("${service.franchise.url}") String membershipServiceUrl) {
        this.commonHttpClient = commonHttpClient;
        this.franchiseServiceUrl = membershipServiceUrl;
    }

    @Override
    public FranchiseStatus getFranchise(String franchiseeId) {

        String url = String.join("/", franchiseServiceUrl, "franchise", franchiseeId);
        try {
            String jsonResponse = commonHttpClient.sendGetRequest(url).body();
            ObjectMapper mapper = new ObjectMapper();
            Franchise franchise = mapper.readValue(jsonResponse, Franchise.class);
            return new FranchiseStatus(franchise.isValid());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
