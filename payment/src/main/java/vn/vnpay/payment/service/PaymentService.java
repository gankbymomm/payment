package vn.vnpay.payment.service;

import vn.vnpay.payment.dto.PaymentDTO;
import vn.vnpay.payment.dto.ResponsePaymentDTO;
import vn.vnpay.payment.utils.CustomException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public interface PaymentService {

    ResponsePaymentDTO sendDataToRabbit(PaymentDTO paymentDTO) throws CustomException, IOException, NoSuchAlgorithmException;
}
