package vn.vnpay.consumer.common;

public enum PaymentEnum {

    ERROR_QUEUE("09", "Error when send to queue"),
    INCORRECT_URL("10", "URL wrong address")
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
