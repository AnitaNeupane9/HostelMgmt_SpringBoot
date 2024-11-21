package com.codilien.hostelmanagementsystem.service;

import com.codilien.hostelmanagementsystem.DTO.RoomDetailsDto;

import java.util.List;

public interface RoomDetailsService {

    RoomDetailsDto createRoom(RoomDetailsDto roomDetailsDto);
    RoomDetailsDto getRoom(Long id);
    RoomDetailsDto updateRoom(Long id, RoomDetailsDto roomDetailsDto);
    List<RoomDetailsDto> getAllRooms();
    void deleteRoom(Long id);
}
