package com.dgw.clinicai.billing.infrastructure.config;

import com.dgw.clinicai.billing.infrastructure.adapter.in.messaging.dto.AppointmentCompletedEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Bean
    public ConsumerFactory<String, AppointmentCompletedEvent> appointmentCompletedEventConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*"); // Trust all packages for deserialization
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(AppointmentCompletedEvent.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AppointmentCompletedEvent> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AppointmentCompletedEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(appointmentCompletedEventConsumerFactory());
        return factory;
    }
}