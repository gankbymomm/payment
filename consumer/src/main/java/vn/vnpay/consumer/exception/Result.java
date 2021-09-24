package vn.vnpay.consumer.exception;

public class Result {
    private String field;

    private String message;

    public String getField() {
        return field;
    }

    public Result(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
