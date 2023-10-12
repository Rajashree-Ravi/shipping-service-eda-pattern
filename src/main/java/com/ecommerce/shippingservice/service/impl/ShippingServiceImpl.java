package com.ecommerce.shippingservice.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ecommerce.shippingservice.entity.Shipment;
import com.ecommerce.shippingservice.exception.EcommerceException;
import com.ecommerce.shippingservice.model.ShipmentDto;
import com.ecommerce.shippingservice.repository.ShippingRepository;
import com.ecommerce.shippingservice.service.ShippingService;

@Service
public class ShippingServiceImpl implements ShippingService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ShippingServiceImpl.class);

	@Autowired
	private ShippingRepository shippingRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public List<ShipmentDto> getAllShipments() {
		List<ShipmentDto> shipments = new ArrayList<>();
		shippingRepository.findAll().forEach(shipment -> {
			shipments.add(mapper.map(shipment, ShipmentDto.class));
		});
		return shipments;
	}

	@Override
	public ShipmentDto getShipmentById(long id) {
		Optional<Shipment> shipment = shippingRepository.findById(id);
		return (shipment.isPresent() ? mapper.map(shipment.get(), ShipmentDto.class) : null);
	}

	@Override
	public ShipmentDto createShipment(ShipmentDto shipmentDto) {
		Shipment shipment = mapper.map(shipmentDto, Shipment.class);
		return mapper.map(shippingRepository.save(shipment), ShipmentDto.class);
	}

	@Override
	public ShipmentDto updateShipment(long id, ShipmentDto shipmentDto) {
		Optional<Shipment> updatedShipment = shippingRepository.findById(id).map(existingShipment -> {
			Shipment shipment = mapper.map(shipmentDto, Shipment.class);
			return shippingRepository.save(existingShipment.updateWith(shipment));
		});

		return (updatedShipment.isPresent() ? mapper.map(updatedShipment.get(), ShipmentDto.class) : null);
	}

	@Override
	public void deleteShipment(long id) {
		if (getShipmentById(id) != null) {
			shippingRepository.deleteById(id);
			LOGGER.info("Shipment deleted Successfully");
		} else {
			throw new EcommerceException("shipment-not-found", String.format("Shipment with id=%d not found", id),
					HttpStatus.NOT_FOUND);
		}
	}
}
