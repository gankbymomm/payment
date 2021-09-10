package vn.vnpay.payment.utils;

public class ExceptionUtils {

    public static CustomException getException(String code, String message, Object... objects) {
        return new CustomException(code, String.format(message, objects));
    }
}
