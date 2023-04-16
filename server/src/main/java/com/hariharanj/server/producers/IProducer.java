package com.hariharanj.server.producers;

import com.hariharanj.server.models.Message;

public interface IProducer {
    boolean produce(Message message);
}
