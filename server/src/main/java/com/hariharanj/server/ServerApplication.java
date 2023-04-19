package com.hariharanj.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.GenericApplicationContext;

import com.hariharanj.server.consumers.IConsumer;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);

		GenericApplicationContext context = ApplicationContext.getInstance();
		IConsumer consumer = context.getBean("consumer", IConsumer.class);
		consumer.consume();
	}
}
