package com.codilien.hostelmanagementsystem.service;

import com.codilien.hostelmanagementsystem.DTO.RoomAllotmentDto;

import java.util.List;

public interface RoomAllotmentService {

//    RoomAllotment allotRoom(RoomAllotmentDto roomAllotmentDto);
    RoomAllotmentDto allotRoom(RoomAllotmentDto roomAllotmentDto);
    RoomAllotmentDto getRoomAllotment(Long id);
    List<RoomAllotmentDto> getAllRoomAllotment();
    RoomAllotmentDto updateRoomAllotment(Long id, RoomAllotmentDto roomAllotmentDto);
    void deleteRoomAllotment(Long id);

}
