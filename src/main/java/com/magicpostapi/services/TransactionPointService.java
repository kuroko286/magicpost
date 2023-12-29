package com.magicpostapi.services;

import com.magicpostapi.enums.Role;
import com.magicpostapi.models.GatheringPoint;
import com.magicpostapi.models.TransactionPoint;
import com.magicpostapi.models.User;
import com.magicpostapi.repositories.TransactionPointRepository;
import com.magicpostapi.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionPointService implements OfficeService {
    @Autowired
    TransactionPointRepository transactionPointRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    GatheringPointService gatheringPointService;
    @Autowired
    EntityManager entityManager;

    @Override
    public boolean checkOfficeExist(String officeId) {
        return transactionPointRepository.existsById(officeId);
    }

    public List<TransactionPoint> findAllTransactionPoint() {
        return transactionPointRepository.findAll();
    }

    public boolean isValidGatheringId(String gatheringId) {
        return gatheringPointService.checkOfficeExist(gatheringId);
    }

    @Transactional
    public TransactionPoint newTransactionPoint(String name, String address, String city, String gatheringPointId) {

        TransactionPoint transactionPoint = new TransactionPoint();
        transactionPoint.setName(name);
        transactionPoint.setAddress(address);
        transactionPoint.setCity(city);

        GatheringPoint gatheringPoint = gatheringPointService.findById(gatheringPointId);
        transactionPoint.setGatheringPoint(gatheringPoint);
        entityManager.persist(transactionPoint);
        return transactionPoint;
    }

    public User newAccountWithTransactionId(Role role, String transactionId) {
        return userService.generatedUser(transactionId + "@magicpost.com", "123456", role, transactionId);
    }

    public TransactionPoint findById(String id) {
        return transactionPointRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transaction Point not found"));
    }

}
