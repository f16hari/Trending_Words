package com.hariharanj.server.consumers;

import java.util.List;

public interface IConsumer {
    List<String> consume(String topic);
}