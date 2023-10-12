package com.ecommerce.shippingservice.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.ecommerce.shippingservice.model.BillDto;
import com.ecommerce.shippingservice.model.InventoryDto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {

	@Value("${consumer.config.bootstrap-servers}")
	private String consumerServers;

	@Value("${consumer.config.bill.service.group-id}")
	private String billingGroupId;

	@Value("${consumer.config.inventory.service.group-id}")
	private String inventoryGroupId;

	@Bean
	public ConsumerFactory<String, BillDto> billingConsumerFactory() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, consumerServers);
		configProps.put(ConsumerConfig.GROUP_ID_CONFIG, billingGroupId);
		configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
		configProps.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
		configProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.ecommerce.shippingservice.model.BillDto");

		return new DefaultKafkaConsumerFactory<>(configProps);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, BillDto> kafkaBillingListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, BillDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(billingConsumerFactory());

		return factory;
	}

	@Bean
	public ConsumerFactory<String, InventoryDto> inventoryConsumerFactory() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, consumerServers);
		configProps.put(ConsumerConfig.GROUP_ID_CONFIG, inventoryGroupId);
		configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
		configProps.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
		configProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.ecommerce.shippingservice.model.InventoryDto");

		return new DefaultKafkaConsumerFactory<>(configProps);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, InventoryDto> kafkaInventoryListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, InventoryDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(inventoryConsumerFactory());

		return factory;
	}

}