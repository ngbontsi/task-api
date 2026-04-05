package com.example.taskapi.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskEventProducer {

    private static final String TOPIC = "task-events";

    private final KafkaTemplate<String, TaskEvent> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendEvent(TaskEvent event) {
        try {
            String key = String.valueOf(event.id());
            kafkaTemplate.send(TOPIC, key, event)
                    .whenComplete((result, ex) -> {
                        if (ex == null) {
                            log.info("Event sent: {} for task id: {}", event.eventType(), event.id());
                        } else {
                            log.error("Failed to send event for task id: {}", event.id(), ex);
                        }
                    });
        } catch (Exception e) {
            log.error("Error sending task event", e);
        }
    }
}
