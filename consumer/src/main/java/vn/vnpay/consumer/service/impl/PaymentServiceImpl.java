package vn.vnpay.consumer.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.vnpay.consumer.constant.RabbitConstant;
import vn.vnpay.consumer.constant.SystemConstant;
import vn.vnpay.consumer.dto.PaymentDTO;
import vn.vnpay.consumer.dto.ResponseFromPartnerDTO;
import vn.vnpay.consumer.entity.PaymentEntity;
import vn.vnpay.consumer.converter.PaymentConverter;
import vn.vnpay.consumer.repository.PaymentRepository;
import vn.vnpay.consumer.utils.CustomException;
import vn.vnpay.consumer.utils.PostRequestUtils;

@Service
public class PaymentServiceImpl {

    private static final Logger logger = LogManager.getLogger(PaymentServiceImpl.class);

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentConverter paymentConverter;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = {RabbitConstant.QUEUE_DATA1})
    public Object receiveFromQueue(PaymentDTO paymentDTO, Message message) throws JsonProcessingException, CustomException {
        ThreadContext.put(SystemConstant.TOKEN, paymentDTO.getTokenKey());
        logger.info("Message : {} ", message);
        logger.info("Receive from queue : {} ", paymentDTO.toString());
        //save data to database
        PaymentEntity paymentEntity = paymentConverter.toEntity(paymentDTO);
        paymentRepository.save(paymentEntity);
        logger.info("Insert to database done !!!");
        // send data and receive response from partner
        ResponseEntity<Object> responsePartner = PostRequestUtils.postRequest(paymentDTO, SystemConstant.URL_VNPAY);
        logger.info("Response from URL : {} ", responsePartner);
        ResponseFromPartnerDTO response = new ResponseFromPartnerDTO();
        if (responsePartner.getStatusCode() == HttpStatus.OK) {
            response.setCode(responsePartner.getStatusCodeValue());
            response.setMessage(SystemConstant.SUCCESS);
            response.setBody(responsePartner.getBody());
        }
        logger.info("Response receive from partner : {} ", response.toString());
        // send response to queue
        rabbitTemplate.convertAndSend(RabbitConstant.TOPIC_EXCHANGE1, RabbitConstant.ROUTING_KEY2, response);
        ObjectMapper mapper = new ObjectMapper();
        String value = mapper.writeValueAsString(paymentDTO);
        ThreadContext.clearMap();
        return value;
    }
}