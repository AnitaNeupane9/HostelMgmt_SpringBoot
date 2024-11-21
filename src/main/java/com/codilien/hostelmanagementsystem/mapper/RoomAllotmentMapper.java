package com.codilien.hostelmanagementsystem.mapper;


import com.codilien.hostelmanagementsystem.DTO.RoomAllotmentDto;
import com.codilien.hostelmanagementsystem.model.RoomAllotment;
import com.codilien.hostelmanagementsystem.model.RoomDetails;
import com.codilien.hostelmanagementsystem.model.Student;

public class RoomAllotmentMapper {

    // Map from RoomAllotmentDto to RoomAllotment
    public static RoomAllotment mapToRoomAllotment(RoomAllotmentDto roomAllotmentDTO) {
        RoomAllotment roomAllotment = new RoomAllotment();
        roomAllotment.setId(roomAllotmentDTO.getId());
        roomAllotment.setAllotmentDate(roomAllotmentDTO.getAllotmentDate());
        roomAllotment.setDuration(roomAllotmentDTO.getDuration());
        roomAllotment.setCheckIn(roomAllotmentDTO.getCheckIn());
        roomAllotment.setCheckOut(roomAllotmentDTO.getCheckOut());

        // Map studentId to a Student entity
        if (roomAllotmentDTO.getStudentId() != null) {
            Student student = new Student();
            student.setId(roomAllotmentDTO.getStudentId());
            roomAllotment.setStudent(student);
        }

        // Map roomId to a RoomDetails entity
        if (roomAllotmentDTO.getRoomId() != null) {
            RoomDetails roomDetails = new RoomDetails();
            roomDetails.setId(roomAllotmentDTO.getRoomId());
            roomAllotment.setRoomDetails(roomDetails);
        }

        return roomAllotment;
    }

    // Map from RoomAllotment to RoomAllotmentDTO
    public static RoomAllotmentDto mapToRoomAllotmentDto(RoomAllotment roomAllotment) {
        RoomAllotmentDto roomAllotmentDTO = new RoomAllotmentDto();
        roomAllotmentDTO.setId(roomAllotment.getId());
        roomAllotmentDTO.setAllotmentDate(roomAllotment.getAllotmentDate());
        roomAllotmentDTO.setDuration(roomAllotment.getDuration());
        roomAllotmentDTO.setCheckIn(roomAllotment.getCheckIn());
        roomAllotmentDTO.setCheckOut(roomAllotment.getCheckOut());

        // Extract studentId and roomId from related entities
        if (roomAllotment.getStudent() != null) {
            roomAllotmentDTO.setStudentId(roomAllotment.getStudent().getId());
        }
        if (roomAllotment.getRoomDetails() != null) {
            roomAllotmentDTO.setRoomId(roomAllotment.getRoomDetails().getId());
        }

        return roomAllotmentDTO;
    }
}
