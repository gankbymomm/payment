package vn.vnpay.payment.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import vn.vnpay.payment.constant.RedisConstant;
import vn.vnpay.payment.utils.CustomException;
import vn.vnpay.payment.utils.ExceptionUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class CheckTokenInRedis {

    private static final Logger logger = LogManager.getLogger(CheckTokenInRedis.class);

  /*  @Autowired
    private static RedisTemplate<String, Object> redisTemplate;*/

    /*public static void checkToken(String token, Object objects) throws CustomException {
        logger.info("Check token in list redis : {} ", token);

        if (exists(token)){
            throw ExceptionUtils.getException(PaymentEnum.TOKEN_EXIST.getValue(), PaymentEnum.TOKEN_EXIST.getMessage());
        } else {
            redisTemplate.opsForValue().set(token, objects);
            redisTemplate.expireAt(token, Date.from(LocalDateTime.now()
                                    .with(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant()));
            Long newSize = redisTemplate.opsForValue().size(RedisConstant.TOKEN);
            logger.info("Size of list in redis after set : {} ", newSize);
        }
    }*/

    /*private static boolean exists(final String key){
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }*/
}

/*
if (sizeOfKey != null && sizeOfKey > 0){
        List<Object> listToken = redisTemplate.opsForList().range(RedisConstant.TOKEN, 0, sizeOfKey);
        if (listToken != null && listToken.contains(token)) {
        // exception
        logger.warn("Token is exist in redis ");
        }
        ThreadContext.clearMap();
        } else if (null == sizeOfKey){
        PaymentDTO paymentDTO = new PaymentDTO();
        redisTemplate.opsForHash().put(RedisConstant.TOKEN, paymentDTO.getTokenKey(), paymentDTO);
        redisTemplate.expireAt(RedisConstant.TOKEN, Date.from(LocalDateTime.now().with(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant()));
        logger.info("New size of list token : {}", redisTemplate.opsForList().size(RedisConstant.TOKEN));
        }*/

/*Long sizeOfKey = redisTemplate.opsForValue().size(RedisConstant.TOKEN);
        logger.info("Size of list in redis : {} ", sizeOfKey);

        if (sizeOfKey == null){
            redisTemplate.opsForValue().set(RedisConstant.TOKEN, token);
            Long newSize = redisTemplate.opsForValue().size(RedisConstant.TOKEN);
            logger.info("Size of list in redis after set : {} ", newSize);

            redisTemplate.expireAt(RedisConstant.TOKEN, Date.from(LocalDateTime.now().with(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant()));
        } else if (sizeOfKey > 0){
            List<Object> listToken = redisTemplate.opsForList().range(RedisConstant.TOKEN, 0, sizeOfKey);
            if (listToken != null && listToken.contains(token)) {
                logger.warn("Token is exist in redis ");
                throw ExceptionUtils.getException(Payment.TOKEN_EXIST.getValue(), Payment.TOKEN_EXIST.getMessage());
            }
        }*/