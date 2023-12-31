package com.magicpostapi.repositories;

import com.magicpostapi.models.Delivery;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, String> {
        @NotNull
        Optional<Delivery> findById(@NotNull String id);

        @Query("SELECT d FROM Delivery d WHERE d.presentDes = :presentDes AND d.dateCreated BETWEEN :from AND :to")
        List<Delivery> findDeliveriesByPresentDesAndDate(
                        @Param("presentDes") String presentDes,
                        @Param("from") Date from,
                        @Param("to") Date to);

        @Query("SELECT d FROM Delivery d WHERE d.nextDes = :nextDes AND d.dateCreated BETWEEN :from AND :to")
        List<Delivery> findDeliveriesByNextDesAndDate(
                        @Param("nextDes") String nextDes,
                        @Param("from") Date from,
                        @Param("to") Date to);
}
