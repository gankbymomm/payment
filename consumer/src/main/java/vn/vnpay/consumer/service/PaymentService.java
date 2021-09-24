package vn.vnpay.consumer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.amqp.core.Message;
import vn.vnpay.consumer.dto.PaymentDTO;
import vn.vnpay.consumer.utils.CustomException;

public interface PaymentService {

    //Object receiveFromQueue(PaymentDTO paymentDTO, Message message) throws JsonProcessingException, CustomException;
}
