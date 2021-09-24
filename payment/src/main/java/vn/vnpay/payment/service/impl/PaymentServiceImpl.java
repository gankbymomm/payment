package vn.vnpay.payment.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.vnpay.payment.common.CheckSum;
import vn.vnpay.payment.common.Common;
import vn.vnpay.payment.common.PaymentEnum;
import vn.vnpay.payment.constant.RabbitConstant;
import vn.vnpay.payment.constant.SystemConstant;
import vn.vnpay.payment.dto.PaymentDTO;
import vn.vnpay.payment.dto.ResponseFromPartnerDTO;
import vn.vnpay.payment.dto.ResponsePaymentDTO;
import vn.vnpay.payment.service.PaymentService;
import vn.vnpay.payment.utils.CustomException;
import vn.vnpay.payment.utils.ExceptionUtils;
import vn.vnpay.payment.validate.ValidatorPayment;

import java.security.NoSuchAlgorithmException;
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

    private static ResponseFromPartnerDTO res;

    private static Object messageFromQueue;

    @Override
    public ResponsePaymentDTO sendDataToRabbit(PaymentDTO paymentDTO) throws CustomException, NoSuchAlgorithmException {
        logger.info("Data received from request : {} ", paymentDTO.toString());
        boolean validate = ValidatorPayment.validate(paymentDTO);
        logger.info("Validate payment : {} ", validate);
        if (!validate) {
            throw ExceptionUtils.getException(PaymentEnum.VALIDATE_FAILED.getCode(),
                    PaymentEnum.VALIDATE_FAILED.getMessage());
        }
        boolean checkSum = CheckSum.dataCheckSum(paymentDTO);
        logger.info("Check sum : {}", checkSum);
        if (!checkSum) {
            throw ExceptionUtils.getException(PaymentEnum.CHECK_SUM_FAIL.getCode(),
                    PaymentEnum.CHECK_SUM_FAIL.getMessage());
        }
        checkToken(paymentDTO.getTokenKey(), paymentDTO);
        logger.info("Check expire token in redis done !!!");
        String message = sendToQueue(paymentDTO);
        checkUrl();
        return Common.responsePartner(SystemConstant.SUCCESS_CODE, SystemConstant.SUCCESS_MESSAGE, true, message);
    }

    /*
     * Send to queue
     * */
    private String sendToQueue(PaymentDTO paymentDTO) {
        return String.valueOf(rabbitTemplate.convertSendAndReceive(RabbitConstant.TOPIC_EXCHANGE1,
                RabbitConstant.ROUTING_KEY1, paymentDTO, message -> {
                    message.getMessageProperties().setMessageId("Consumer received message !!!");
                    return message;
                }));
    }

    /*
     * Receive reply consumer
     * */
    @RabbitListener(queues = {RabbitConstant.QUEUE_DATA2})
    private void replyConsumer(ResponseFromPartnerDTO responsePartnerDTO, Message message) {
        res = responsePartnerDTO;
        messageFromQueue = message;
    }

    private void checkUrl() throws CustomException {
        logger.info("Response from partner , status code : {} and body : {} ", res.getCode(), res);
        logger.info("Message from queue : {} ", messageFromQueue);
        if (res.getCode() != HttpStatus.OK.value()) {
            throw ExceptionUtils.getException(PaymentEnum.TRANSACTION_ERROR.getCode(), PaymentEnum.TRANSACTION_ERROR.getMessage());
        }
    }

    /*
     * Check exist and extend for token in redis
     * */
    private void checkToken(String token, Object objects) throws CustomException {
        logger.info("Check token in list redis : {} ", token);
        if (exists(token)) {
            logger.warn("Token {} is exist in redis !!! ", token);
            throw ExceptionUtils.getException(PaymentEnum.TOKEN_EXIST.getCode(), PaymentEnum.TOKEN_EXIST.getMessage());
        } else {
            redisTemplate.opsForValue().set(token, objects);
            redisTemplate.expireAt(token, Date.from(LocalDateTime.now()
                    .with(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant()));
        }
    }

    private boolean exists(final String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}
