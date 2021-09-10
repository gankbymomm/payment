package vn.vnpay.consumer.service;

import vn.vnpay.consumer.dto.PaymentDTO;

public interface PaymentService {

    void receiveFromQueue(PaymentDTO paymentDTO);
}
