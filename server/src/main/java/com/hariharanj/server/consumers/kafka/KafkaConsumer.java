package com.hariharanj.server.consumers.kafka;

import java.util.List;

import com.hariharanj.server.consumers.IConsumer;

public class KafkaConsumer implements IConsumer {

    @Override
    public List<String> consume(String topic) {
        throw new UnsupportedOperationException("Unimplemented method 'consume'");
    }
}
