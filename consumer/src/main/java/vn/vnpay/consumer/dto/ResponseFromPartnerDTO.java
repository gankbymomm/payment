package vn.vnpay.consumer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseFromPartnerDTO {

    private int code;
    private String message;
    private Object body;
}
