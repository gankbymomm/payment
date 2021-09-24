package vn.vnpay.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponsePaymentDTO {

    private String code;
    private String message;
    private boolean checkSum;
    private Object response;
}
