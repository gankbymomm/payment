package vn.vnpay.consumer.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.vnpay.consumer.constant.RabbitConstant;
import vn.vnpay.consumer.constant.SystemConstant;
import vn.vnpay.consumer.dto.PaymentDTO;
import vn.vnpay.consumer.dto.ResponseFromPartnerDTO;
import vn.vnpay.consumer.entity.PaymentEntity;
import vn.vnpay.consumer.mapper.PaymentConverter;
import vn.vnpay.consumer.repository.PaymentRepository;
import vn.vnpay.consumer.service.PaymentService;
import vn.vnpay.consumer.utils.PostRequestUtils;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger logger = LogManager.getLogger(PaymentServiceImpl.class);

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentConverter paymentConverter;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    @RabbitListener(queues = {RabbitConstant.QUEUE_DATA1})
    public void receiveFromQueue(PaymentDTO paymentDTO) {
        
        ThreadContext.put(SystemConstant.TOKEN, paymentDTO.getTokenKey());
        logger.info("Receive from queue : {} ", paymentDTO.toString());

        //save data to database
        PaymentEntity paymentEntity = paymentConverter.toEntity(paymentDTO);
        logger.info("Data insert to database : {} ", paymentEntity.toString());
        paymentRepository.save(paymentEntity);

        // send data and receive response to partner
        ResponseEntity<Object> responsePartner = PostRequestUtils.postRequest(paymentDTO, SystemConstant.URL_API_VNPAY);
        logger.info("Response from URL : {} ", responsePartner);
        ResponseFromPartnerDTO response = new ResponseFromPartnerDTO();
        if (responsePartner != null) {
            response.setStatusCode(responsePartner.getStatusCodeValue());
            response.setMessage(SystemConstant.SUCCESS);
            response.setData(responsePartner.getBody());
        }
        logger.info("Response receive from partner : {} ", response.toString());

        // send response to queue
        rabbitTemplate.convertAndSend(RabbitConstant.TOPIC_EXCHANGE1, RabbitConstant.ROUTING_KEY2, response);
        ThreadContext.clearMap();
        
        }
    }
}
