package com.magicpostapi.controllers;

import com.magicpostapi.dto.ResponseObject;
import com.magicpostapi.models.Order;
import com.magicpostapi.services.DeliveryService;
import com.magicpostapi.services.OrderService;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/orders")
@CrossOrigin("*")
public class OrderController {
        @Autowired
        private OrderService orderService;
        @Autowired
        private DeliveryService deliveryService;

        // @PreAuthorize("hasAnyAuthority('TRANSACTION_POINT_MANAGER', 'ADMIN',
        // 'TELLERS')")
        @GetMapping("/{id}")
        ResponseEntity<Order> getOrderById(@PathVariable String id) {
                return ResponseEntity.ok(orderService.getOrderById(id));
        }

        // @PreAuthorize("hasAnyAuthority('TRANSACTION_POINT_MANAGER', 'ADMIN',
        // 'TELLERS')")
        @PostMapping("")
        ResponseEntity<Map<String, String>> createOrder(@RequestBody Map<String, String> reqBody) {
                Order newOrder = orderService.createOrder(reqBody);
                return ResponseEntity.ok(Collections.singletonMap("orderId", newOrder.getId()));
        }

        @PutMapping("/{id}")
        ResponseEntity<Order> updateOrder(@PathVariable String id,
                        @RequestBody Map<String, String> reqBody) {
                Order order = orderService.updateOrder(id, reqBody);
                return ResponseEntity.ok(order);
        }

        // @PreAuthorize("hasAnyAuthority('TRANSACTION_POINT_MANAGER', 'ADMIN',
        // 'TELLERS')")
        // @GetMapping("/statistic")
        // ResponseEntity<List<Order>> statisticOrderByStatusAndMonth(
        // @RequestParam @Nullable String status,
        // @RequestParam @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
        // @RequestParam @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd") Date to) {
        // List<Order> orders = orderService.statisticOrder(status, from, to);
        // return ResponseEntity.ok(orders);
        // // return new ResponseEntity<>(new ResponseObject(
        // // "200",
        // // "Find successfully",
        // // orderService.statisticOrder(status, from, to)), HttpStatus.OK);
        // }

        @PreAuthorize("hasAnyAuthority('TRANSACTION_POINT_MANAGER', 'ADMIN', 'TELLERS')")
        @GetMapping("/statistic")
        ResponseEntity<Map<String, Object>> statisticOrders() {
                Map<String, Object> stat = orderService.statisticOrder();
                return ResponseEntity.ok(stat);
        }

        // @PreAuthorize("hasAnyAuthority('TRANSACTION_POINT_MANAGER', 'ADMIN',
        // 'TELLERS')")
        // @GetMapping("/statistic/transaction/{id}")
        // ResponseEntity<ResponseObject> transactionStatistic(
        // @PathVariable @Nonnull String id,
        // @RequestParam @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
        // @RequestParam @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd") Date to) {
        // class Statistic {
        // @JsonProperty
        // private List<Order> ordersSend;
        // @JsonProperty
        // private List<Order> ordersReceive;

        // public Statistic(List<Order> ordersSend, List<Order> ordersReceive) {
        // this.ordersSend = ordersSend;
        // this.ordersReceive = ordersReceive;
        // }
        // }

        // return new ResponseEntity<>(new ResponseObject(
        // "200",
        // "Find Successfully",
        // new Statistic(orderService.transactionStatisticSend(from, to, id),
        // orderService.transactionStatisticReceive(from, to, id))),
        // HttpStatus.OK);
        // }

        // @PreAuthorize("hasAnyAuthority('GATHERING_POINT_MANAGER', 'ADMIN',
        // 'TELLERS')")
        // @GetMapping("/statistic/gathering/{id}")
        // ResponseEntity<ResponseObject> gatheringStatistic(
        // @RequestParam @Nonnull @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
        // @RequestParam @Nonnull @DateTimeFormat(pattern = "yyyy-MM-dd") Date to,
        // @PathVariable String id) {
        // return new ResponseEntity<>(new ResponseObject(
        // "200",
        // "Find successfully",
        // deliveryService.gatheringStatistic(id, from, to)), HttpStatus.OK);
        // }

        @PreAuthorize("hasAnyAuthority('TRANSACTION_POINT_MANAGER', 'ADMIN', 'TELLERS')")
        @GetMapping("/statistic/transaction/{id}")
        ResponseEntity<Map<String, Object>> transactionStatistic(
                        @PathVariable @Nonnull String id) {
                return ResponseEntity.ok(orderService.transactionStatistic(id));
        }

        // @PreAuthorize("hasAnyAuthority('GATHERING_POINT_MANAGER', 'ADMIN',
        // 'TELLERS')")
        // @GetMapping("/statistic/gathering/{id}")
        // ResponseEntity<ResponseObject> gatheringStatistic(

        // @PathVariable String id) {
        // // return new ResponseEntity<>(new ResponseObject(
        // // "200",
        // // "Find successfully",
        // // deliveryService.gatheringStatistic(id, from, to)), HttpStatus.OK);
        // return ResponseEntity.ok(orderService)
        // }

        @PreAuthorize("hasAnyAuthority('ADMIN')")
        @GetMapping("/count")
        ResponseEntity<ResponseObject> countOrder(
                        @RequestParam @Nonnull @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
                        @RequestParam @Nonnull @DateTimeFormat(pattern = "yyyy-MM-dd") Date to) {
                return new ResponseEntity<>(new ResponseObject(
                                "200",
                                "Count successfully",
                                orderService.countOrderByDateInterval(from, to)), HttpStatus.OK);
        }

        @PreAuthorize("hasAnyAuthority('ADMIN', 'TELLERS', 'TRANSACTION_POINT_MANAGER')")
        @GetMapping("/count/transaction/{id}")
        ResponseEntity<ResponseObject> transactionCountOrder(
                        @RequestParam @Nonnull @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
                        @RequestParam @Nonnull @DateTimeFormat(pattern = "yyyy-MM-dd") Date to,
                        @PathVariable @Nonnull String id) {
                return new ResponseEntity<>(new ResponseObject(
                                "200",
                                "Count successfully",
                                orderService.transactionCountOrder(id, from, to)), HttpStatus.OK);
        }

        @PreAuthorize("hasAnyAuthority('ADMIN', 'COORDINATOR', 'GATHERING_POINT_MANAGER')")
        @GetMapping("/count/gathering/{id}")
        ResponseEntity<ResponseObject> gatheringCountOrder(
                        @RequestParam @Nonnull @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
                        @RequestParam @Nonnull @DateTimeFormat(pattern = "yyyy-MM-dd") Date to,
                        @PathVariable @Nonnull String id) {
                return new ResponseEntity<>(new ResponseObject(
                                "200",
                                "Count successfully!"), HttpStatus.OK);
        }
}
