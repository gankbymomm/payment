package vn.vnpay.payment.validate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import vn.vnpay.payment.common.PaymentEnum;
import vn.vnpay.payment.dto.PaymentDTO;
import vn.vnpay.payment.utils.CustomException;
import vn.vnpay.payment.utils.ExceptionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ValidatorPayment {

    private static final Logger logger = LogManager.getLogger(ValidatorPayment.class);

    private static boolean checkDateFormat(String date) throws CustomException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        Date transactionTime;
        Date presentTime;
        try {
            dateFormat.parse(date);
            transactionTime = dateFormat.parse(date);
            presentTime = dateFormat.parse(now);
            long differentTime = (Math.abs(transactionTime.getTime() - presentTime.getTime()))/1000;
            if (differentTime > 300){
                throw ExceptionUtils.getException(PaymentEnum.TIME_OUT.getCode(), PaymentEnum.TIME_OUT.getMessage());
            }
        } catch (ParseException e) {
            logger.error("Check format date error ", e);
            return false;
        }
        return true;
    }

    public static boolean validate(PaymentDTO paymentDTO) throws CustomException {
        if (!checkDateFormat(paymentDTO.getPayDate())){
            logger.debug("Check format pay date : {} ", paymentDTO.getPayDate());
            throw ExceptionUtils.getException(PaymentEnum.WRONG_FORMAT_DATE.getCode(), PaymentEnum.WRONG_FORMAT_DATE.getMessage());
        }
        if (Double.parseDouble(paymentDTO.getRealAmount()) > paymentDTO.getDebitAmount()){
            logger.debug("Real amount : {} , debit amount : {} ", paymentDTO.getRealAmount(), paymentDTO.getDebitAmount());
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
