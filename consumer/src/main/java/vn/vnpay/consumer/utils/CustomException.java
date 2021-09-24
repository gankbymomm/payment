package vn.vnpay.consumer.utils;

import lombok.Data;

@Data
public class CustomException extends Exception{

    public CustomException(String code, String message) {
        super(message);
        this.code = code;
    }

    private String code;
    private String responseId;
}
