package com.hariharanj.server.controllers;

import org.springframework.context.support.GenericApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hariharanj.server.ApplicationContext;
import com.hariharanj.server.models.Message;
import com.hariharanj.server.producers.IProducer;

@RestController
public class MessageController {
    
    public static GenericApplicationContext context = ApplicationContext.getInstance();    

    @PostMapping("post")
    public String postMessage(@RequestBody Message message){
        IProducer producer = context.getBean("producer", IProducer.class);
        
        if(producer.produce(message)) return "Successful";
        return "Error Has Occured!!";
    }
}