package com.codilien.hostelmanagementsystem.controller;

import com.codilien.hostelmanagementsystem.DTO.RoomAllotmentDto;
import com.codilien.hostelmanagementsystem.service.RoomAllotmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roomAllotments/")
public class RoomAllotmentController {

    private RoomAllotmentService roomAllotmentService;

    @Autowired
    public RoomAllotmentController(RoomAllotmentService roomAllotmentService) {
        this.roomAllotmentService = roomAllotmentService;
    }

    @PostMapping("/create")
    public ResponseEntity<RoomAllotmentDto> createRoomAllotment(@RequestBody RoomAllotmentDto roomAllotmentDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(roomAllotmentService.allotRoom(roomAllotmentDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomAllotmentDto> getRoomAllotment(@PathVariable Long id){
        return ResponseEntity.ok(roomAllotmentService.getRoomAllotment(id));
    }

    @GetMapping({"", "/"})
    public ResponseEntity<List<RoomAllotmentDto>> getAllAllotments(){
        List<RoomAllotmentDto> allotments = roomAllotmentService.getAllRoomAllotment();
        return ResponseEntity.ok(allotments);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<RoomAllotmentDto> updateRoomAllotments(@PathVariable Long id, @RequestBody RoomAllotmentDto roomAllotmentDto){
        return ResponseEntity.status(HttpStatus.OK).body(roomAllotmentService.updateRoomAllotment(id, roomAllotmentDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoomAllotment(@PathVariable Long id){
        roomAllotmentService.deleteRoomAllotment(id);
        return ResponseEntity.ok("Room Allotment is deleted Successfully.");
    }
}
