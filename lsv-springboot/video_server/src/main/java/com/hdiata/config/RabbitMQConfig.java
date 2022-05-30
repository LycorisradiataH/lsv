package com.hdiata.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.hdiata.common.constant.MQPrefixConst.EMAIL_EXCHANGE;
import static com.hdiata.common.constant.MQPrefixConst.EMAIL_QUEUE;

/**
 * rabbitmq 配置类
 * @author Lin Hua
 * @version 1.0
 * @date 2022/3/20 17:39
 */
@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue emailQueue() {
        return new Queue(EMAIL_QUEUE, true);
    }

    @Bean
    public FanoutExchange emailExchange() {
        return new FanoutExchange(EMAIL_EXCHANGE, true, false);
    }

    @Bean
    public Binding bindingEmailDirect() {
        return BindingBuilder.bind(emailQueue()).to(emailExchange());
    }

}
