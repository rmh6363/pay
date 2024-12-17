package com.pay.money.query.adapter.out.aws.dynamodb;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Map;

@Component
public class MoneySumByAddressMapper {
    public MoneySumByAddress mapToMoneySumByAddress(Map<String, AttributeValue> item){
        return  new MoneySumByAddress(
                item.get("PK").s(),
                item.get("SK").s(),
                Integer.parseInt(item.get("balance").n())
        );
    }
}
