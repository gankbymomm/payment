package vn.vnpay.payment.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.vnpay.payment.constant.RabbitConstant;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue queue(){
        return new Queue(RabbitConstant.QUEUE_DATA1, false);
    }

    @Bean
    public Queue queue1(){
        return new Queue(RabbitConstant.QUEUE_DATA2, false);
    }

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(RabbitConstant.TOPIC_EXCHANGE1);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue()).to(topicExchange()).with(RabbitConstant.ROUTING_KEY1);
    }

    @Bean
    public Binding binding1(){
        return BindingBuilder.bind(queue1()).to(topicExchange()).with(RabbitConstant.ROUTING_KEY2);
    }

    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

}
