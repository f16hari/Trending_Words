package com.hariharanj.server.models;

public class Message {
    
    private String topic;
    private String data;

    public void setTopic(String topic){
        this.topic = topic;
    }

    public void setData(String data){
        this.data = data;
    }

    public String getTopic(){
        return topic;
    }

    public String getData(){
        return data;
    }
}
