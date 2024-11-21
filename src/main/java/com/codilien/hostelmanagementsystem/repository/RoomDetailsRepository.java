package com.codilien.hostelmanagementsystem.repository;

import com.codilien.hostelmanagementsystem.model.RoomDetails;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoomDetailsRepository extends JpaRepository<RoomDetails, Long> {
    boolean existsByRoomNumber(String RoomNumber);
}
