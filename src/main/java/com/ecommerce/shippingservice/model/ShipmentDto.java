package com.ecommerce.shippingservice.model;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "Class representing a shipment in e-commerce application.")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentDto {

	@ApiModelProperty(notes = "Unique identifier of the shipment.", example = "1")
	private Long id;

	@ApiModelProperty(notes = "Shipment number.", example = "1123876", required = true)
	@NotBlank
	private String shipmentNo;

	@ApiModelProperty(notes = "Customer name.", example = "Abhishek", required = true)
	@NotBlank
	private String customerName;

	@ApiModelProperty(notes = "Address of the customer.", example = "Chennai, TamilNadu", required = true)
	@NotBlank
	private String customerAddress;

	@ApiModelProperty(notes = "Destination location code.", example = "IND091", required = true)
	@NotBlank
	private String destinationCode;

	@ApiModelProperty(notes = "Mode of Shipment.", example = "Airway", required = true)
	@NotBlank
	private String shipmentMode;

	@ApiModelProperty(notes = "Order ID.", example = "23456", required = true)
	@NotNull
	private Long orderId;

	@ApiModelProperty(notes = "Inventory ID.", example = "67467", required = true)
	@NotNull
	private Long inventoryId;

	@ApiModelProperty(notes = "Address of the inventory.", example = "Bhuj, Gujarat", required = true)
	@NotBlank
	private String inventoryAddress;

	@ApiModelProperty(notes = "Source location code.", example = "GUJ056", required = true)
	@NotBlank
	private String sourceCode;

	@ApiModelProperty(notes = "Total amount paid by the customer.", example = "450.00", required = true)
	@NotNull
	private BigDecimal paidAmount;

	@ApiModelProperty(notes = "Status of the shipment.", example = "IN-TRANSIT", required = true)
	@NotBlank
	private String shipmentStatus;

	public ShipmentDto(@NotBlank String shipmentNo, @NotBlank String customerName, @NotBlank String customerAddress,
			@NotBlank String destinationCode, @NotBlank String shipmentMode, @NotNull Long orderId,
			@NotNull Long inventoryId, @NotBlank String inventoryAddress, @NotBlank String sourceCode,
			@NotNull BigDecimal paidAmount, @NotBlank String shipmentStatus) {
		super();
		this.shipmentNo = shipmentNo;
		this.customerName = customerName;
		this.customerAddress = customerAddress;
		this.destinationCode = destinationCode;
		this.shipmentMode = shipmentMode;
		this.orderId = orderId;
		this.inventoryId = inventoryId;
		this.inventoryAddress = inventoryAddress;
		this.sourceCode = sourceCode;
		this.paidAmount = paidAmount;
		this.shipmentStatus = shipmentStatus;
	}

}
