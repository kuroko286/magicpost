package com.magicpostapi.services;

import com.magicpostapi.enums.Role;
import com.magicpostapi.models.GatheringPoint;
import com.magicpostapi.models.User;
import com.magicpostapi.repositories.GatheringPointRepository;
import com.magicpostapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GatheringPointService implements OfficeService {
    @Autowired
    GatheringPointRepository gatheringPointRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @Override
    public boolean checkOfficeExist(String officeId) {
        return gatheringPointRepository.existsById(officeId);
    }

    public List<GatheringPoint> findAllGatheringPoint() {
        return gatheringPointRepository.findAll();
    }

    public GatheringPoint newGatheringPoint(String name, String address, String city) {
        GatheringPoint gatheringPoint = new GatheringPoint();
        gatheringPoint.setName(name);
        gatheringPoint.setCity(city);
        gatheringPoint.setAddress(address);
        return gatheringPointRepository.save(gatheringPoint);
    }

    public User newAccountWithGatheringId(Role role, String gatheringId) {
        return userService.generatedUser(gatheringId + "@magicpost.com", "123456", role, gatheringId);
    }

    public GatheringPoint findById(String id) {
        return gatheringPointRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("GatheringPoint not found"));
    }

}
