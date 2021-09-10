package vn.vnpay.payment.service;

import vn.vnpay.payment.dto.PaymentDTO;
import vn.vnpay.payment.utils.CustomException;

public interface PaymentService {

    PaymentDTO sendDataToRabbit(PaymentDTO paymentDTO) throws CustomException;
}
