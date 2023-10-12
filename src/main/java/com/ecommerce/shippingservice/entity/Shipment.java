package com.ecommerce.shippingservice.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shipment")
public class Shipment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String billNo;

	@NotBlank
	private String customerName;

	@NotBlank
	private String customerAddress;

	@NotBlank
	private String destinationCode;

	@NotBlank
	private String shipmentMode;

	@NotNull
	private Long orderId;

	@NotNull
	private Long inventoryId;

	@NotBlank
	private String inventoryAddress;

	@NotBlank
	private String sourceCode;

	@NotNull
	private BigDecimal paidAmount;

	@NotBlank
	private String shipmentStatus;

	public Shipment updateWith(Shipment shipment) {
		return new Shipment(this.id, shipment.billNo, shipment.customerName, shipment.customerAddress,
				shipment.destinationCode, shipment.shipmentMode, shipment.orderId, shipment.inventoryId,
				shipment.inventoryAddress, shipment.sourceCode, shipment.paidAmount, shipment.shipmentStatus);
	}

}
