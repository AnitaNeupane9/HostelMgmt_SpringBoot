package com.codilien.hostelmanagementsystem.mapper;

import com.codilien.hostelmanagementsystem.DTO.RoomDetailsDto;
import com.codilien.hostelmanagementsystem.model.RoomDetails;



public class RoomDetailsMapper {
    public static RoomDetails mapToRoomDetails(RoomDetailsDto roomDetailsDto) {
        RoomDetails roomDetails = new RoomDetails(
                roomDetailsDto.getId(),
                roomDetailsDto.getRoomNumber(),
                roomDetailsDto.getFloor(),
                roomDetailsDto.getRoomType(),
                roomDetailsDto.getCapacity(),
                roomDetailsDto.getCurrentStudentCount(),
                roomDetailsDto.getAvailableStudentSlots(),
                roomDetailsDto.getStatus(),
                null
        );
        return roomDetails;
    }

    public static RoomDetailsDto mapToRoomDetailsDto(RoomDetails roomDetails){
        RoomDetailsDto roomDetailsDto = new RoomDetailsDto(
                roomDetails.getId(),
                roomDetails.getRoomNumber(),
                roomDetails.getFloor(),
                roomDetails.getRoomType(),
                roomDetails.getCapacity(),
                roomDetails.getCurrentStudentCount(),
                roomDetails.getAvailableStudentSlots(),
                roomDetails.getStatus()
        );
        return roomDetailsDto;
    }
}
