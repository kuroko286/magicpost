package com.magicpostapi.services;

import com.magicpostapi.enums.OrderStatus;
import com.magicpostapi.dto.OrderDto;
import com.magicpostapi.models.Order;
import com.magicpostapi.models.TransactionPoint;
import com.magicpostapi.repositories.OrderRepository;
import com.magicpostapi.repositories.TransactionPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private TransactionPointRepository transactionPointRepository;

    public Order getOrderById(String id) {
        return orderRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Order not found"));
    }

    public Order createOrder(Map<String, String> details) {
        Order newOrder = new Order();
        newOrder.setSenderName(details.get("senderName"));
        newOrder.setSenderAddress(details.get("senderAddress"));
        newOrder.setSenderPhone(details.get("senderPhone"));
        newOrder.setRecipientName(details.get("recipientName"));
        newOrder.setRecipientAddress(details.get("recipientAddress"));
        newOrder.setRecipientPhone(details.get("recipientPhone"));
        newOrder.setType(details.get("goodsType"));
        newOrder.setTransactionPointFrom(
                transactionPointRepository.findById(details.get("startLocation"))
                        .orElseThrow(() -> new IllegalArgumentException("Transaction not found")));
        newOrder.setTransactionPointTo(
                transactionPointRepository.findById(details.get("endLocation"))
                        .orElseThrow(() -> new IllegalArgumentException("Transaction not found")));
        newOrder.setTellersName(details.get("tellersName"));
        newOrder.setNote(details.get("note"));
        newOrder.setCod(details.get("recipientFeesCod"));
        newOrder.setWeight(Double.valueOf(details.get("weight")));

        newOrder.setMainCharge(Double.valueOf(details.get("costMain")));
        newOrder.setSubCharge(Double.valueOf(details.get("costAdditional")));

        OrderStatus orderStatus = OrderStatus.valueOf("CONFIRMED");
        newOrder.setOrderStatus(orderStatus);
        return orderRepository.save(newOrder);
    }

    public List<Order> statisticOrderByStatusAndDateInterval(OrderStatus status, Date from, Date to) {
        if (from.after(to))
            throw new InvalidParameterException("Interval time invalid");
        return orderRepository.findOrderByStatusAndDateInterval(status, from, to);
    }

    public List<Order> statisticOrderByStatus(OrderStatus status) {
        return orderRepository.findOrdersByOrderStatus(status);
    }

    public List<Order> statisticOrderByDateInterval(Date from, Date to) {
        if (from.after(to))
            throw new InvalidParameterException("Interval time invalid");
        return orderRepository.findOrdersByDateInterval(from, to);
    }

    public List<Order> getAllOrder() {

        return orderRepository.findAll(Sort.by(Sort.Direction.DESC, "dateCreated"));
    }

    public Map<String, Object> statisticOrder() {
        List<Order> orders = getAllOrder();
        int incoming = orders.size();
        int outgoing = orders.size();
        int success = (int) orders.stream().filter((order) -> order.getOrderStatus().equals(OrderStatus.RECEIVED))
                .count();
        int failed = (int) orders.stream().filter((order) -> order.getOrderStatus().equals(OrderStatus.RETURN)).count();
        List<OrderDto> orderDtos = orders.stream().limit(5).map((order) -> new OrderDto(order)).toList();
        return Map.of("incoming", incoming, "outgoing", outgoing, "success", success, "failed", failed, "latestOrders",
                orderDtos);
    }

    public List<Order> transactionStatisticSend(String transactionId) {
        TransactionPoint transactionPoint = transactionPointRepository
                .findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction point id not found"));

        return orderRepository.findOrdersByTransactionPointFrom(transactionPoint);

    }

    public List<Order> transactionStatisticReceive(String transactionId) {
        TransactionPoint transactionPoint = transactionPointRepository
                .findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction point id not found"));

        return orderRepository.findOrdersByTransactionPointTo(transactionPoint);

    }

    public Map<String, Object> transactionStatistic(String transactionId) {
        TransactionPoint transactionPoint = transactionPointRepository
                .findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction point id not found"));

        List<Order> orders = orderRepository
                .findOrdersByTransactionPointId(transactionPoint);
        int incoming = orders.size();
        int outgoing = orders.size();
        int success = (int) orders.stream().filter((order) -> order.getOrderStatus().equals(OrderStatus.RECEIVED))
                .count();
        int failed = (int) orders.stream().filter((order) -> order.getOrderStatus().equals(OrderStatus.RETURN)).count();
        List<OrderDto> orderDtos = orders.stream().limit(5).map((order) -> new OrderDto(order)).toList();
        return Map.of("incoming", incoming, "outgoing", outgoing, "success", success, "failed", failed, "latestOrders",
                orderDtos);

    }

    public List<Map<String, String>> countOrderByDateInterval(Date from, Date to) {
        if (from.after(to))
            throw new InvalidParameterException("Interval time invalid");
        return orderRepository.countAllOrdersByDateInterval(from, to);
    }

    public List<Map<String, String>> transactionCountOrderSend(TransactionPoint transactionPoint, Date from, Date to) {
        return orderRepository
                .countOrdersByDateIntervalAndTransactionPointFrom(from, to, transactionPoint);
    }

    public List<Map<String, String>> transactionCountOrderReceive(TransactionPoint transactionPoint, Date from,
            Date to) {
        return orderRepository
                .countOrdersByDateIntervalAndTransactionPointTo(from, to, transactionPoint);
    }

    public Map<String, Object> transactionCountOrder(String transactionId, Date from, Date to) {
        if (from.after(to))
            throw new InvalidParameterException("Interval time invalid");
        TransactionPoint transactionPoint = transactionPointRepository
                .findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction point id not found"));
        Map<String, Object> res = new HashMap<>();
        res.put("orderSent", transactionCountOrderSend(transactionPoint, from, to));
        res.put("orderReceived", transactionCountOrderReceive(transactionPoint, from, to));
        return res;
    }

    public Order updateOrder(String id, Map<String, String> reqBody) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Order not found"));
        String status = reqBody.get("status");
        System.out.println(status);
        if (status.equals("done")) {
            order.setOrderStatus(OrderStatus.RECEIVED);
        } else if (status.equals("failed")) {
            order.setOrderStatus(OrderStatus.RETURN);
        }
        Order savedOrder = orderRepository.save(order);
        return savedOrder;

    }
}
