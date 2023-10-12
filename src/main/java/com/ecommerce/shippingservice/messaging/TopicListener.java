package com.ecommerce.shippingservice.messaging;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ecommerce.shippingservice.model.BillDto;
import com.ecommerce.shippingservice.model.InventoryDto;
import com.ecommerce.shippingservice.model.ItemDto;
import com.ecommerce.shippingservice.model.OrderDto;
import com.ecommerce.shippingservice.model.ShipmentDto;
import com.ecommerce.shippingservice.service.ShippingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class TopicListener {

	@Value("${consumer.config.inventory.service.topic")
	private String inventoryTopicName;

	@Value("${consumer.config.bill.service.topic")
	private String billTopicName;

	@Autowired
	ShippingService shippingService;

	@Autowired
	private RestTemplate restTemplate;

	@KafkaListener(topics = "${consumer.config.bill.service.topic}", groupId = "${consumer.config.bill.service.group-id}", containerFactory = "kafkaBillingListenerContainerFactory")
	public void consumeBill(ConsumerRecord<String, BillDto> payload) {
		log.info("Topic : {}", billTopicName);
		log.info("Key : {}", payload.key());
		log.info("Headers : {}", payload.headers());
		log.info("Partion : {}", payload.partition());
		log.info("Bill : {}", payload.value());

		BillDto bill = payload.value();

		ResponseEntity<OrderDto> orderResponse = restTemplate.exchange(
				"http://localhost:8081/api/orders/" + bill.getOrderId(), HttpMethod.GET, null, OrderDto.class);
		if (orderResponse != null && orderResponse.getStatusCode() == HttpStatus.OK) {
			OrderDto order = orderResponse.getBody();

			for (ItemDto item : order.getItems()) {
				ResponseEntity<InventoryDto> inventoryResponse = restTemplate.exchange(
						"http://localhost:8082/api/inventory/" + item.getInventoryId(), HttpMethod.GET, null,
						InventoryDto.class);
				if (inventoryResponse != null && inventoryResponse.getStatusCode() == HttpStatus.OK) {
					InventoryDto inventory = inventoryResponse.getBody();
					ShipmentDto shipment = new ShipmentDto("S30654", "Abhishek", "No.1, Nehru Street, Chennai.",
							"IND091", "AIRWAY", bill.getOrderId(), inventory.getId(), "No.4, Gandhi Street, Hyderabad.",
							"HYD023", order.getTotal(), "READY");
					shippingService.createShipment(shipment);
				}
			}
		}
	}

	@KafkaListener(topics = "${consumer.config.inventory.service.topic}", groupId = "${consumer.config.inventory.service.group-id}", containerFactory = "kafkaInventoryListenerContainerFactory")
	public void consumeInventory(ConsumerRecord<String, InventoryDto> payload) {
		log.info("Topic : {}", inventoryTopicName);
		log.info("Key : {}", payload.key());
		log.info("Headers : {}", payload.headers());
		log.info("Partion : {}", payload.partition());
		log.info("Inventory : {}", payload.value());

		InventoryDto inventory = payload.value();
		inventory.getVendor();

		// Save the flag
	}

}