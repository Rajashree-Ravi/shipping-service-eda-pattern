package com.ecommerce.shippingservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.shippingservice.exception.EcommerceException;
import com.ecommerce.shippingservice.model.ShipmentDto;
import com.ecommerce.shippingservice.service.ShippingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(produces = "application/json", value = "Operations pertaining to manage shipment in e-commerce application")
@RequestMapping("/api/shipment")
public class ShippingController {

	@Autowired
	ShippingService shippingService;

	@GetMapping
	@ApiOperation(value = "View all shipments", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved all shipments"),
			@ApiResponse(code = 204, message = "Shipments list is empty"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	private ResponseEntity<List<ShipmentDto>> getAllShipments() {

		List<ShipmentDto> shipmentList = shippingService.getAllShipments();
		if (shipmentList.isEmpty())
			throw new EcommerceException("no-content", "Shipments list is empty", HttpStatus.NO_CONTENT);

		return new ResponseEntity<>(shipmentList, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Retrieve specific shipment with the specified shipment id", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved shipment with the shipment id"),
			@ApiResponse(code = 404, message = "Shipment with specified shipment id not found"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	private ResponseEntity<ShipmentDto> getShipmentById(@PathVariable("id") long id) {

		ShipmentDto shipment = shippingService.getShipmentById(id);
		if (shipment != null)
			return new ResponseEntity<>(shipment, HttpStatus.OK);
		else
			throw new EcommerceException("shipment-not-found", String.format("Shipment with id=%d not found", id),
					HttpStatus.NOT_FOUND);
	}

	@PostMapping
	@ApiOperation(value = "Create a new shipment", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successfully created a shipment"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	public ResponseEntity<ShipmentDto> createShipment(@RequestBody ShipmentDto shipment) {
		ShipmentDto savedShipment = shippingService.createShipment(shipment);
		return new ResponseEntity<>(savedShipment, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Update a shipment information", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updated shipment information"),
			@ApiResponse(code = 404, message = "Shipment with specified shipment id not found"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	public ResponseEntity<ShipmentDto> updateShipment(@PathVariable("id") long id, @RequestBody ShipmentDto shipment) {

		ShipmentDto updatedShipment = shippingService.updateShipment(id, shipment);
		if (updatedShipment != null)
			return new ResponseEntity<>(updatedShipment, HttpStatus.OK);
		else
			throw new EcommerceException("shipment-not-found", String.format("Shipment with id=%d not found", id),
					HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete a shipment", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Successfully deleted shipment information"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	private ResponseEntity<String> deleteShipment(@PathVariable("id") long id) {

		shippingService.deleteShipment(id);
		return new ResponseEntity<>("Shipment deleted successfully", HttpStatus.NO_CONTENT);
	}

}
