package vn.vnpay.payment.common;

public enum PaymentEnum {

    TOKEN_EXIST("02","Token exist in redis"),
    WRONG_FORMAT_DATE("03", "Time is wrong format"),
    ERROR_AMOUNT("04", "Real amount can not greater than debit amount"),
    PROMOTION_NULL("05", "Promotion code can not null when real amount different debit amount"),
    VALIDATE_FAILED("06", "Validate failed")
    ;

    private String code;
    private String message;

    PaymentEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
