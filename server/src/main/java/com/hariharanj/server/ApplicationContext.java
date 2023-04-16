package com.hariharanj.server;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import com.hariharanj.server.ioc.ConsumerIoc;
import com.hariharanj.server.ioc.ProducerIoc;

public class ApplicationContext {
    
    public static AnnotationConfigApplicationContext context;

    public static GenericApplicationContext getInstance(){
        if(context == null){
            context = new AnnotationConfigApplicationContext();

            context.register(ConsumerIoc.class);
            context.register(ProducerIoc.class);
            context.refresh();
        }

        return context;
    }

}
