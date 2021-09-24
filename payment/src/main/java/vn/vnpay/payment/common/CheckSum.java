package vn.vnpay.payment.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.vnpay.payment.dto.PaymentDTO;
import vn.vnpay.payment.utils.CustomException;
import vn.vnpay.payment.utils.ExceptionUtils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CheckSum {

    private static final Logger logger = LogManager.getLogger(CheckSum.class);

    private static String hash256(String input) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = messageDigest.digest(input.getBytes(StandardCharsets.UTF_8));
        BigInteger convert = new BigInteger(1, hashBytes);
        return convert.toString(16);
    }

    public static boolean dataCheckSum(PaymentDTO paymentDTO) throws NoSuchAlgorithmException, CustomException {
        String privateKey = CheckBankCode.getPrivateKey(paymentDTO.getBankCode());
        logger.info("Access key of {} : {} ", paymentDTO.getBankCode(),  privateKey);
        if (privateKey == null) {
            throw ExceptionUtils.getException(PaymentEnum.INVALID_KEY.getCode(), PaymentEnum.INVALID_KEY.getMessage());
        }
        String stringCheckSum = paymentDTO.getMobile() + paymentDTO.getBankCode() +
                paymentDTO.getAccountNo() + paymentDTO.getDebitAmount() +
                paymentDTO.getRespCode() + paymentDTO.getTraceTransfer() +
                paymentDTO.getMessageType() + privateKey;
        String sum = hash256(stringCheckSum).toUpperCase();
        logger.info("String data check sum : {} and before hash-256 : {} ", stringCheckSum, sum);
        return paymentDTO.getCheckSum().equals(sum);
    }
}
