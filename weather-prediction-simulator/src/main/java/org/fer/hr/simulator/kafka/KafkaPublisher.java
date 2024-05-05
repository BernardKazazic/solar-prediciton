package org.fer.hr.simulator.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

@Slf4j
public class KafkaPublisher {

    private final KafkaProducer<String, String> producer;

    public KafkaPublisher() {
        // TODO initialize better
        this.producer = new KafkaProducer<>(null);

        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
    }

    public void publish(String topic, String jsonMessage) {
        producer.send(new ProducerRecord<>(topic, jsonMessage), (metadata, exception) -> {
            //TODO retry logic
            if (exception != null) {
                log.error("Error publishing message to topic {}", topic, exception);
            }

            log.info("Message sent to partition {} with offset {}", metadata.partition(), metadata.offset());
        });
    }

    private void shutdown() {
        producer.close();
    }
}
