package vn.vnpay.consumer.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class PostRequestUtils {

    private static final Logger logger = LogManager.getLogger(PostRequestUtils.class);

    public static ResponseEntity<Object> postRequest(Object object, String url) {
        logger.info("Data send to partner :{}", object.toString());
        logger.info("Url partner receive data :{}", url);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
            headers.setContentType(MediaType.APPLICATION_JSON);
            RestTemplate restTemplate = new RestTemplate();

            HttpEntity<Object> requestBody = new HttpEntity<>(object, headers);
            ResponseEntity<Object> result = restTemplate.postForEntity(url, requestBody, Object.class);
            logger.info("Response from partner : {}", result);
            logger.info("Data status :{}", result.getStatusCodeValue());
            return result;
        } catch (Exception e) {
            logger.error("Can not send data to partner : " + e);
            ThreadContext.clearMap();
            return null;
        }
    }
}
