package com.hariharanj.server.producers.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import com.hariharanj.server.models.Message;
import com.hariharanj.server.producers.IProducer;

public class KafkaProducer implements IProducer{

    private Producer<String, String> producer;

    public KafkaProducer(){
        Properties properties = new Properties();

        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        producer = new org.apache.kafka.clients.producer.KafkaProducer<>(properties);
    }

    @Override
    public boolean produce(Message message) {
        try{
            ProducerRecord<String, String> messagRecord = new ProducerRecord<String,String>(message.getTopic(), null, message.getData());
            producer.send(messagRecord);
            producer.flush();
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
}
