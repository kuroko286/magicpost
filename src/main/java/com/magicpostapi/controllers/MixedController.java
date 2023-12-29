package com.magicpostapi.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.magicpostapi.dto.OrderDto;
import com.magicpostapi.models.Order;
import com.magicpostapi.services.GatheringPointService;
import com.magicpostapi.services.OrderService;
import com.magicpostapi.services.TransactionPointService;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class MixedController {

    @Autowired
    private GatheringPointService gatheringPointService;
    @Autowired
    private TransactionPointService transactionPointService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/locations")
    public ResponseEntity<List<String>> getAllOfficeLocation() {
        List<String> grpLocations = gatheringPointService.findAllGatheringPoint().stream().map(p -> p.getId()).toList();
        List<String> tspLocations = transactionPointService.findAllTransactionPoint().stream().map(p -> p.getId())
                .toList();
        List<String> res = new ArrayList<>();
        res.addAll(grpLocations);
        res.addAll(tspLocations);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/statistics")
    public ResponseEntity<List<OrderDto>> getStatistics() {

        List<Order> orders = orderService.getAllOrder();
        List<OrderDto> orderDtos = orders.stream().map(o -> new OrderDto(o)).toList();
        return ResponseEntity.ok(orderDtos);

    }

}
