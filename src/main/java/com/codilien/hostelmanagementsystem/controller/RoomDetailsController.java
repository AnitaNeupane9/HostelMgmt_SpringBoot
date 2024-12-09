package com.codilien.hostelmanagementsystem.controller;

import com.codilien.hostelmanagementsystem.DTO.RoomDetailsDto;
import com.codilien.hostelmanagementsystem.repository.RoomDetailsRepository;
import com.codilien.hostelmanagementsystem.service.RoomDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomDetailsController {

    private RoomDetailsService roomDetailsService ;
    private RoomDetailsRepository roomDetailsRepository;

    public RoomDetailsController(
            RoomDetailsService roomDetailsService,
            RoomDetailsRepository roomDetailsRepository) {

        this.roomDetailsService = roomDetailsService;
        this.roomDetailsRepository = roomDetailsRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> addRoom (@RequestBody RoomDetailsDto roomDetailsDto) {
        RoomDetailsDto createdRoom = roomDetailsService.createRoom(roomDetailsDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRoom);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDetailsDto> getRoomDetail (@PathVariable Long id){
        return ResponseEntity.ok(roomDetailsService.getRoom(id));
    }

    @GetMapping({"", "/"})
    public ResponseEntity<List<RoomDetailsDto>> getAllRooms(){
        List<RoomDetailsDto> roomDetails = roomDetailsService.getAllRooms();
        return ResponseEntity.ok(roomDetails);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<RoomDetailsDto> editRoomDetail(
            @PathVariable Long id,
            @RequestBody RoomDetailsDto roomDetailsDto){

        return ResponseEntity.status(HttpStatus.OK).body(roomDetailsService.updateRoom(id,roomDetailsDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long id){
        roomDetailsService.deleteRoom(id);
        return ResponseEntity.ok("Room deleted Successfully!");
    }
}
