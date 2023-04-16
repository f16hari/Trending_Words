package com.hariharanj.server.ioc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hariharanj.server.consumers.IConsumer;
import com.hariharanj.server.consumers.kafka.KafkaConsumer;

@Configuration
public class ConsumerIoc {
    @Bean
    public IConsumer consumer(){
        return new KafkaConsumer();
    }
}
