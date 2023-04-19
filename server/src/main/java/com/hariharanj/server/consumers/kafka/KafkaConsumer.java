package com.hariharanj.server.consumers.kafka;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.support.GenericApplicationContext;

import com.hariharanj.server.ApplicationContext;
import com.hariharanj.server.consumers.IConsumer;
import com.hariharanj.server.dfs.IDistFileSystem;

public class KafkaConsumer implements IConsumer {

    public static GenericApplicationContext context = ApplicationContext.getInstance();
    public static final String topic = "foobar";

    public org.apache.kafka.clients.consumer.KafkaConsumer<String, String> consumer;
    public IDistFileSystem dfs;
    public boolean isRunning;

    public KafkaConsumer() {
        isRunning = false;
        Properties properties = new Properties();

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<String, String>(properties);
        consumer.subscribe(Arrays.asList(topic));

        dfs = context.getBean("distFileSystem", IDistFileSystem.class);
    }

    @Override
    public void consume() {
        if(isRunning) return;
        isRunning = true;

        Thread thread =  new Thread(() -> {
            while(true){
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));

                try{
                    for(ConsumerRecord<String, String> record: records){
                        dfs.AppendInFile(topic + ".txt", record.value());
                    }
        
                    consumer.commitAsync();
                }
                catch(Exception e){
                    System.out.println("Error occured while consuming message!!!");
                }
            }
        });

        thread.start();
    }
}