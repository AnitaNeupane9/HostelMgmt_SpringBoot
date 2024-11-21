package com.codilien.hostelmanagementsystem.repository;

import com.codilien.hostelmanagementsystem.model.RoomAllotment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomAllotmentRepository extends JpaRepository<RoomAllotment, Long> {
}
