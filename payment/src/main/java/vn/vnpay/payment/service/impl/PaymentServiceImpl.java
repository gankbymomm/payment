package vn.vnpay.payment.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.vnpay.payment.common.PaymentEnum;
import vn.vnpay.payment.constant.RabbitConstant;
import vn.vnpay.payment.constant.SystemConstant;
import vn.vnpay.payment.dto.PaymentDTO;
import vn.vnpay.payment.dto.ResponsePartnerDTO;
import vn.vnpay.payment.service.PaymentService;
import vn.vnpay.payment.utils.CustomException;
import vn.vnpay.payment.utils.ExceptionUtils;
import vn.vnpay.payment.validate.ValidatorPayment;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger logger = LogManager.getLogger(PaymentServiceImpl.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public PaymentDTO sendDataToRabbit(PaymentDTO paymentDTO) throws CustomException {
        ThreadContext.put(SystemConstant.TOKEN, paymentDTO.getTokenKey());

        logger.info("Data received from request : {} ", paymentDTO.toString());

        logger.info("Validate payment : {} ", ValidatorPayment.validate(paymentDTO));
        if (!ValidatorPayment.validate(paymentDTO)){
            ThreadContext.clearMap();
            throw ExceptionUtils.getException(PaymentEnum.VALIDATE_FAILED.getCode(),
                            PaymentEnum.VALIDATE_FAILED.getMessage());
        }

        checkToken(paymentDTO.getTokenKey(), paymentDTO);
        logger.info("Check expire token in redis done !!!");

        // send data to rabbit(consumer receive)
        rabbitTemplate.convertSendAndReceive(RabbitConstant.TOPIC_EXCHANGE1,
                                                RabbitConstant.ROUTING_KEY1, paymentDTO);
        ThreadContext.clearMap();
        return paymentDTO;
    }

    /*
    * Receive reply consumer
    * */
    @RabbitListener(queues = {RabbitConstant.QUEUE_DATA2})
    public void replyConsumer(ResponsePartnerDTO responsePartnerDTO){
        logger.info("Reply from consumer in rabbitmq : {} ", responsePartnerDTO);

        if (responsePartnerDTO.getStatusCode() != HttpStatus.OK.value()){
            responsePartnerDTO.setStatusCode(responsePartnerDTO.getStatusCode());
            responsePartnerDTO.setMessage(SystemConstant.FAILED);
            responsePartnerDTO.setData(SystemConstant.DATA_NULL);
            logger.info("Data receive partner : {} ", responsePartnerDTO);
        }
        ThreadContext.clearMap();
    }

    /*
    * Check exist and extend for token in redis
    * */
    private void checkToken(String token, Object objects) throws CustomException {
        logger.info("Check token in list redis : {} ", token);

        if (exists(token)){
            throw ExceptionUtils.getException(PaymentEnum.TOKEN_EXIST.getCode(), PaymentEnum.TOKEN_EXIST.getMessage());
        } else {
            redisTemplate.opsForValue().set(token, objects);
            redisTemplate.expireAt(token, Date.from(LocalDateTime.now()
                    .with(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant()));
            Long newSize = redisTemplate.opsForValue().size(token);
            logger.info("Size of list in redis after set : {} ", newSize);
        }
    }
    private boolean exists(final String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}
