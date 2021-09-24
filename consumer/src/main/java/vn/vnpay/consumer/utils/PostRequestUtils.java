package vn.vnpay.consumer.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import vn.vnpay.consumer.common.PaymentEnum;

public class PostRequestUtils {

    private static final Logger logger = LogManager.getLogger(PostRequestUtils.class);

    public static ResponseEntity<Object> postRequest(Object object, String url) throws CustomException {
        logger.info("Data send to partner : {} and to url : {} ", object.toString(), url);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
            headers.setContentType(MediaType.APPLICATION_JSON);
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<Object> requestBody = new HttpEntity<>(object, headers);
            ResponseEntity<Object> result = restTemplate.postForEntity(url, requestBody, Object.class);
            logger.info("Response from partner : {} and status code : {} ", result, result.getStatusCodeValue());
            return result;
        } catch (Exception e) {
            ThreadContext.clearMap();
            logger.error("Can not send data to partner " , e);
           throw ExceptionUtils.getException(PaymentEnum.INCORRECT_URL.getCode(), PaymentEnum.INCORRECT_URL.getMessage());
        }
    }
}
