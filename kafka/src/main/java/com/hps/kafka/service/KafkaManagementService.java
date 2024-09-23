package com.hps.kafka.service;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Service
public class KafkaManagementService {
    private final KafkaAdmin kafkaAdmin;
    private final Map<String, Object> adminConfigs;

    @Autowired
    public KafkaManagementService(KafkaAdmin kafkaAdmin, Map<String, Object> adminConfigs) {
        this.kafkaAdmin = kafkaAdmin;
        this.adminConfigs = adminConfigs;
    }

    public void createTopic(String topicName, int numPartitions, short replicationFactor) {
        NewTopic newTopic = new NewTopic(topicName, numPartitions, replicationFactor);
        kafkaAdmin.createOrModifyTopics(newTopic);
    }

    public void deleteTopic(String topicName) throws ExecutionException, InterruptedException {
        try (AdminClient adminClient = AdminClient.create(adminConfigs)) {
            adminClient.deleteTopics(Collections.singletonList(topicName)).all().get();
        }
    }

    public void subscribeToTopic(String topicName, String groupId) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, adminConfigs.get("bootstrap.servers"));
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        KafkaConsumer<String, Object> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topicName));

        new Thread(() -> {
            while (true) {
                ConsumerRecords<String,
                        Object> records = consumer.poll(Duration.ofMillis(100));
                records.forEach(record -> {
                    System.out.printf("Consumed record with key %s and value %s%n", record.key(), record.value());
                });
            }
        }).start();
    }

    public void produceMessage(String topicName, String key, Object value) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, adminConfigs.get("bootstrap.servers"));
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());

        try (KafkaProducer<String, Object> producer = new KafkaProducer<>(props)) {
            ProducerRecord<String, Object> record = new ProducerRecord<>(topicName, key, value);
            producer.send(record);
        }
    }
}
