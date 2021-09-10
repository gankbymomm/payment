package vn.vnpay.payment.utils;

import lombok.Data;

@Data
public class CustomException extends Exception{

    public CustomException(Exception ex) {
        super(ex);
    }

    public CustomException(String code, Exception ex) {
        super(ex);
        this.code = code;
    }

    public CustomException(String code, String message) {
        super(message);
        this.code = code;
    }

    private String code;
    private String responseId;
}
