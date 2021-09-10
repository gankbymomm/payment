package vn.vnpay.consumer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePaymentDTO implements Serializable {

    private String code;
    private String message;
    private String responseId;
    private String checkSum;
    private String addValue;
}
