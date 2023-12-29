package com.magicpostapi.services;

import com.magicpostapi.enums.OrderStatus;
import com.magicpostapi.models.Delivery;
import com.magicpostapi.models.Order;
import com.magicpostapi.repositories.DeliveryRepository;
import com.magicpostapi.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DeliveryService {
    @Autowired
    DeliveryRepository deliveryRepository;
    @Autowired
    OrderRepository orderRepository;

    public Map<String, List<Delivery>> gatheringStatistic(String idGrp, Date from, Date to) {
        Map<String, List<Delivery>> statistic = new HashMap<>();
        statistic.put("orderSent", deliveryRepository.findDeliveriesByPresentDesAndDate(idGrp, from, to));
        statistic.put("orderReceived", deliveryRepository.findDeliveriesByNextDesAndDate(idGrp, from, to));

        return statistic;
    }

    public Delivery createDelivery(Map<String, String> body) {
        Order order = orderRepository.findById(body.get("orderId"))
                .orElseThrow(() -> new IllegalArgumentException("Order Id not found"));
        List<Delivery> deliveries = order.getDeliveries();
        if (deliveries.size() > 0) {
            if (!deliveries.get(deliveries.size() - 1).isDone()) {
                throw new IllegalArgumentException("Order is shipping");
            }
        }

        if (order.getOrderStatus().equals(OrderStatus.RECEIVED) || order.getOrderStatus().equals(OrderStatus.RETURN)) {
            throw new IllegalArgumentException("Order is already received or returned");
        }
        if (deliveries.size() > 0
                && !deliveries.get(deliveries.size() - 1).getNextDes().equals(body.get("fromLocation"))) {
            throw new IllegalArgumentException("Wrong location");
        }

        Delivery newDelivery = new Delivery();
        newDelivery.setOrder(order);
        newDelivery.setPresentDes(body.get("fromLocation"));
        newDelivery.setNextDes(body.get("toLocation"));
        newDelivery.setDone(false);

        Delivery savedDelivery = deliveryRepository.save(newDelivery);

        return savedDelivery;
    }

    public Delivery comfirmDelivery(String id) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Delivery not found"));
        delivery.setDone(true);
        return deliveryRepository.save(delivery);

    }

    public Delivery getDelivery(String id) {
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Delivery not found"));
    }

    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

}
