package com.example.taskapi.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TaskEventConsumer {

    @KafkaListener(topics = "task-events", groupId = "task-api-group")
    public void consume(TaskEvent event) {
        log.info("Received event: {} for task id: {} - title: {}", 
                event.eventType(), event.id(), event.title());
        
        switch (event.eventType()) {
            case CREATED -> handleCreated(event);
            case UPDATED -> handleUpdated(event);
            case DELETED -> handleDeleted(event);
        }
    }

    private void handleCreated(TaskEvent event) {
        log.info("Processing new task: {}", event.title());
    }

    private void handleUpdated(TaskEvent event) {
        log.info("Processing task update: {} - completed: {}", event.title(), event.completed());
    }

    private void handleDeleted(TaskEvent event) {
        log.info("Processing task deletion: {}", event.id());
    }
}
