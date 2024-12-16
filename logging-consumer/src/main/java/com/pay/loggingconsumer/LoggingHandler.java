package com.pay.loggingconsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Component
public class LoggingHandler {

    private final KafkaConsumer<String, String> loggingConsumer;


    public LoggingHandler(KafkaConsumer<String, String> loggingConsumer) {
        this.loggingConsumer=loggingConsumer;
        this.consume();


    }
    public void consume(){
        Thread consumerThread = new Thread(() -> {
            try {
                while (true) {
                    ConsumerRecords<String,String> records =loggingConsumer.poll(Duration.ofMillis(1));
                    if (records == null){
                        continue;
                    }
                    for (ConsumerRecord<String,String> record : records){
                        handle(record);
                    }
                }
            } finally {
                loggingConsumer.close();
            }
        });
        consumerThread.start();
    }

    private void handle(ConsumerRecord<String,String> record){
        if (!record.value().startsWith("[logging]")){
            return;
        }
        System.out.println("record.value() = " + record.value());

    }
}