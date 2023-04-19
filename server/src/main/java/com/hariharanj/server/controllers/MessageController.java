package com.hariharanj.server.controllers;

import java.util.List;

import org.springframework.context.support.GenericApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hariharanj.server.ApplicationContext;
import com.hariharanj.server.consumers.IConsumer;
import com.hariharanj.server.dfs.IDistFileSystem;
import com.hariharanj.server.models.Message;
import com.hariharanj.server.producers.IProducer;

@RestController
public class MessageController {
    
    public static GenericApplicationContext context = ApplicationContext.getInstance();    

    @PostMapping("post")
    public String postMessage(@RequestBody Message message){
        IProducer producer = context.getBean("producer", IProducer.class);

        IDistFileSystem dfs = context.getBean("distFileSystem", IDistFileSystem.class);

        try{
            dfs.AppendInFile(message.getTopic() + ".txt", message.getData());
        }
        catch(Exception e){
            System.out.println("Error Has Occurred!!!");
        }

        if(producer.produce(message)) return "Successful";

        return "Error Has Occured!!";
    }

    @GetMapping("view")
    public List<String> viewMessages(@RequestParam String topic){
        IConsumer consumer = context.getBean("consumer", IConsumer.class);
        return consumer.consume(topic);
    }
}