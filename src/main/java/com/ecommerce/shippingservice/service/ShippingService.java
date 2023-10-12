package com.ecommerce.shippingservice.service;

import java.util.List;

import com.ecommerce.shippingservice.model.ShipmentDto;

public interface ShippingService {

	List<ShipmentDto> getAllShipments();

	ShipmentDto getShipmentById(long id);

	ShipmentDto createShipment(ShipmentDto shipment);

	ShipmentDto updateShipment(long id, ShipmentDto shipment);

	void deleteShipment(long id);
}
