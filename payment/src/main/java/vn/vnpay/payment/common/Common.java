package vn.vnpay.payment.common;

import vn.vnpay.payment.dto.ResponsePaymentDTO;

public class Common {

    public static ResponsePaymentDTO responsePartner(String code, String message,
                                                     boolean checkSum, Object response){
        return ResponsePaymentDTO.builder()
                .code(code)
                .message(message)
                .checkSum(checkSum)
                .response(response)
                .build();
    }
}
