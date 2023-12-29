package com.magicpostapi.repositories;

import com.magicpostapi.models.TransactionPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionPointRepository extends JpaRepository<TransactionPoint, String> {

}
