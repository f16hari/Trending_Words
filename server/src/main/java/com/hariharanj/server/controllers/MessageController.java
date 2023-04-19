package com.hariharanj.server.controllers;

import org.springframework.context.support.GenericApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hariharanj.server.ApplicationContext;
import com.hariharanj.server.dataprocessors.wordcount.WordCountProcessor;
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

    @GetMapping("process")
    public String processMessage(@RequestParam String topic){
        try{
            WordCountProcessor.performWordCount(topic);
            return "Successful";
        }
        catch(Exception e){
            return "Error Occured while Processing Messages!!";
        }
    }

    @GetMapping("trending")
    public String getTrendingWords(@RequestParam String topic){
        try{
            return WordCountProcessor.getProcessedResult(topic);
        }
        catch(Exception e){
            return "Error Occured while Retrieving Results!!";
        }
    }
}