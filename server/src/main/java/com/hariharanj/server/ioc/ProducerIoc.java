package com.hariharanj.server.ioc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hariharanj.server.producers.IProducer;
import com.hariharanj.server.producers.kafka.KafkaProducer;

@Configuration
public class ProducerIoc {
    @Bean
    public IProducer producer(){
        return new KafkaProducer();
    }    
}
