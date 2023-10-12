package com.ecommerce.shippingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.shippingservice.entity.Shipment;

public interface ShippingRepository extends JpaRepository<Shipment, Long> {

}
