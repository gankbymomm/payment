package vn.vnpay.payment.validate;

import org.apache.logging.log4j.util.Strings;
import vn.vnpay.payment.common.PaymentEnum;
import vn.vnpay.payment.dto.PaymentDTO;
import vn.vnpay.payment.utils.CustomException;
import vn.vnpay.payment.utils.ExceptionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ValidatorPayment {

    private static boolean checkDateFormat(String checkDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            dateFormat.parse(checkDate);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public static boolean validate(PaymentDTO paymentDTO) throws CustomException {

        if (!checkDateFormat(paymentDTO.getPayDate())){
            throw ExceptionUtils.getException(PaymentEnum.WRONG_FORMAT_DATE.getCode(), PaymentEnum.WRONG_FORMAT_DATE.getMessage());
        }

        if (Double.parseDouble(paymentDTO.getRealAmount()) > paymentDTO.getDebitAmount()){
            throw ExceptionUtils.getException(PaymentEnum.ERROR_AMOUNT.getCode(), PaymentEnum.WRONG_FORMAT_DATE.getMessage());
        }
        if (Double.parseDouble(paymentDTO.getRealAmount()) != paymentDTO.getDebitAmount()){
            if (Strings.isBlank(paymentDTO.getPromotionCode())){
                throw ExceptionUtils.getException(PaymentEnum.PROMOTION_NULL.getCode(), PaymentEnum.PROMOTION_NULL.getMessage());
            }
        }
        return true;
    }
}
