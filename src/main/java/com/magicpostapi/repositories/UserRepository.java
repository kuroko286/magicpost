package com.magicpostapi.repositories;

import com.magicpostapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String userName);

    Optional<User> findUserByEmail(String email);

    List<User> findByGatheringPoint_Id(String gatheringPointId);

    List<User> findByTransactionPoint_Id(String gatheringPointId);

}
