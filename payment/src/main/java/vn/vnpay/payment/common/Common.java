package vn.vnpay.payment.common;

import vn.vnpay.payment.dto.ResponsePaymentDTO;

public class Common {

    public static ResponsePaymentDTO responsePartner(String code, String message, String responseId){
        return ResponsePaymentDTO.builder()
                .code(code)
                .message(message)
                .responseId(responseId)
                .build();
    }
}
