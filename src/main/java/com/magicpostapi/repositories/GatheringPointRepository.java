package com.magicpostapi.repositories;

import com.magicpostapi.models.GatheringPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GatheringPointRepository extends JpaRepository<GatheringPoint, String> {
}
