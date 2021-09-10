package vn.vnpay.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePartnerDTO {

    private int statusCode;
    private String message;
    private Object data;
}
