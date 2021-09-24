package vn.vnpay.payment.service;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import vn.vnpay.payment.dto.PaymentDTO;

@RunWith(MockitoJUnitRunner.class)
public class PaymentServiceTest {

    private static final PaymentDTO paymentDTO = new PaymentDTO();

    @BeforeAll
    public static void setUp() {
        paymentDTO.setTokenKey("1601353776839FT19310RH6P1");
        paymentDTO.setApiID("restPayment");
        paymentDTO.setPayDate("20200929112923");
        paymentDTO.setAccountNo("0001100014211002");
        paymentDTO.setDebitAmount(11200);
        paymentDTO.setAddtionalData("");
        paymentDTO.setMobile("0145225630");
        paymentDTO.setMessageType("1");
        paymentDTO.setOrderCode("FT19310RH6P1");
        paymentDTO.setUserName("cntest001");
        paymentDTO.setRealAmount("11200");
        paymentDTO.setRespCode("00");
        paymentDTO.setTraceTransfer("FT19310RH6P1");
        paymentDTO.setRespDesc("SUCCESS");
        paymentDTO.setBankCode("970445");
        paymentDTO.setCheckSum("40e670720b754324af3d3a0ff49b52fb");
        paymentDTO.setPromotionCode("");
        paymentDTO.setAddValue("{\"payMethod\":\"01\",\"payMethodMMS\":1}");
    }

    @Test
    public void checkToken(){

    }

}
