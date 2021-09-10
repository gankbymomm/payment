package vn.vnpay.consumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.vnpay.consumer.constant.RabbitConstant;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue queue(){
        return new Queue(RabbitConstant.QUEUE_DATA1);
    }

    @Bean
    public Queue queue1(){
        return new Queue(RabbitConstant.QUEUE_DATA2);
    }

    // receive
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(RabbitConstant.TOPIC_EXCHANGE1);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue()).to(topicExchange()).with(RabbitConstant.ROUTING_KEY1);
    }

    // send
   /* @Bean
    public TopicExchange topicExchange2(){
        return new TopicExchange(RabbitConstant.TOPIC_EXCHANGE2);
    }*/

    @Bean
    public Binding binding2(){
        return BindingBuilder.bind(queue1()).to(topicExchange()).with(RabbitConstant.ROUTING_KEY2);
    }

    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }
}
