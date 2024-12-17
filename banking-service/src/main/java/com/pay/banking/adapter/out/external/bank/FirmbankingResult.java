package com.pay.banking.adapter.out.external.bank;

import lombok.Data;
import lombok.Getter;

@Data
public class FirmbankingResult {
    @Getter
    private int resultCode; // 0 : 성공, 1 : 싪패

    public FirmbankingResult(int resultCode) {
        this.resultCode = resultCode;
    }
}
