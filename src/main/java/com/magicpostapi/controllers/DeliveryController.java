package com.magicpostapi.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.magicpostapi.models.Delivery;
import com.magicpostapi.services.DeliveryService;

@RestController
@RequestMapping("/api/v1/deliveries")
@CrossOrigin("*")
public class DeliveryController {
    @Autowired
    private DeliveryService deliveryService;

    @GetMapping("")
    public ResponseEntity<List<Delivery>> getAllDeliveries() {
        return ResponseEntity.ok(deliveryService.getAllDeliveries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Delivery> getDelivery(@PathVariable String id) {
        return ResponseEntity.ok(deliveryService.getDelivery(id));
    }

    @PostMapping("")
    public ResponseEntity<Delivery> createDelivery(@RequestBody Map<String, String> body) {
        return ResponseEntity.ok(deliveryService.createDelivery(body));
    }

    // update delivery status
    @PutMapping("/{id}")
    public ResponseEntity<Delivery> updateDelivery(@PathVariable String id) {
        return ResponseEntity.ok(deliveryService.comfirmDelivery(id));
    }

}
