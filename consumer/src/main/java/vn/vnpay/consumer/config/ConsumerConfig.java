package vn.vnpay.consumer.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConsumerConfig {

    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        ((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory()).setConnectTimeout(10000);
        ((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory()).setReadTimeout(10000);
        return restTemplate;
    }
}
