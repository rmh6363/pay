package com.pay.remittance.application.service;

import com.pay.remittance.adapter.out.persistence.RemittanceRequestJpaEntity;
import com.pay.remittance.adapter.out.persistence.RemittanceRequestMapper;
import com.pay.remittance.application.port.in.RequestRemittanceCommand;
import com.pay.remittance.application.port.out.RequestRemittancePort;
import com.pay.remittance.application.port.out.banking.BankingPort;
import com.pay.remittance.application.port.out.membership.MembershipPort;
import com.pay.remittance.application.port.out.membership.MembershipStatus;
import com.pay.remittance.application.port.out.money.MoneyPort;
import com.pay.remittance.domain.RemittanceRequest;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TestRequestRemittanceService {

    @InjectMocks
    private RequestRemittanceService requestRemittanceService;
    @Mock
    private RequestRemittancePort requestRemittancePort;
    @Mock
    private RemittanceRequestMapper mapper;
    @Mock
    private MembershipPort membershipPort;
    @Mock
    private MoneyPort moneyPort;
    @Mock
    private BankingPort bankingPort;


    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        requestRemittanceService = new RequestRemittanceService(requestRemittancePort,mapper,membershipPort,moneyPort,bankingPort);
    }

    public static Stream<RequestRemittanceCommand> provideRequestRemittanceCommand(){
        return Stream.of(
                RequestRemittanceCommand.builder()
                        .fromMembershipId("1")
                        .toMembershipId("4")
                        .toBankName("bank")
                        .remittanceType(0)
                        .toBankAccountNumber("123-456-789")
                        .amount(15000)
                        .build()
        );

    }
    //송금이 유효하지않은 경우 Test
    @ParameterizedTest
    @MethodSource("provideRequestRemittanceCommand")
    public void testRequestRemittanceServiceWhenFromMembershipInvalid(RequestRemittanceCommand testCommand){
        RemittanceRequest want = null;



        //mocking
        when(requestRemittancePort.createRemittanceRequestHistory(testCommand))
                .thenReturn(null);

        when(membershipPort.getMembershipStatus(testCommand.getFromMembershipId()))
                .thenReturn(new MembershipStatus(testCommand.getFromMembershipId(),false));


        RemittanceRequest got = requestRemittanceService.requestRemittance(testCommand);

        verify(requestRemittancePort,times(1)).createRemittanceRequestHistory(testCommand);
        verify(membershipPort,times(1)).getMembershipStatus(testCommand.getFromMembershipId());

        assertEquals(want,got);


    }

}
