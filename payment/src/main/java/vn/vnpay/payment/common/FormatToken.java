package vn.vnpay.payment.common;

import org.apache.logging.log4j.util.Strings;

import java.util.UUID;

public class FormatToken {

    public static String store(String tokens){
        String token = "";
        if (Strings.isBlank(tokens)){
            token = UUID.randomUUID().toString();
        }else {
            token = tokens;
        }
        return token;
    }
}
