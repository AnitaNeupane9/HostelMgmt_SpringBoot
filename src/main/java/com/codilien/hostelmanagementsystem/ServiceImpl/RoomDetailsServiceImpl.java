package com.codilien.hostelmanagementsystem.ServiceImpl;

import com.codilien.hostelmanagementsystem.DTO.RoomDetailsDto;
import com.codilien.hostelmanagementsystem.exception.ResourceConflictException;
import com.codilien.hostelmanagementsystem.exception.ResourceNotFoundException;
import com.codilien.hostelmanagementsystem.mapper.RoomDetailsMapper;
import com.codilien.hostelmanagementsystem.model.RoomDetails;
import com.codilien.hostelmanagementsystem.repository.RoomDetailsRepository;
import com.codilien.hostelmanagementsystem.service.RoomDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomDetailsServiceImpl implements RoomDetailsService {

    private RoomDetailsRepository roomDetailsRepository;

    @Autowired
    public RoomDetailsServiceImpl(RoomDetailsRepository roomDetailsRepository) {
        this.roomDetailsRepository = roomDetailsRepository;
    }


    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    public RoomDetailsDto createRoom(RoomDetailsDto roomDetailsDto) {
        RoomDetails roomDetails = RoomDetailsMapper.mapToRoomDetails(roomDetailsDto);

        // Setting currentStudentCount to 0
        roomDetails.setCurrentStudentCount(0);
        // Calculate available slots as capacity - currentStudentCount
        roomDetails.setAvailableStudentSlots(roomDetails.getCapacity());

        // throw exception if the roomnumber is already present in db
        if (roomDetailsRepository.existsByRoomNumber(roomDetails.getRoomNumber())){
            throw new ResourceConflictException("The Room Number " + roomDetailsDto.getRoomNumber());
        }

        RoomDetails savedRoom = roomDetailsRepository.save(roomDetails);
        return RoomDetailsMapper.mapToRoomDetailsDto(savedRoom);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN') or (hasRole('STUDENT') and @securityService.isOwner(#id,'ROOM'))")
    public RoomDetailsDto getRoom(Long id) {
       RoomDetails roomDetails = roomDetailsRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("Room"));
       return RoomDetailsMapper.mapToRoomDetailsDto(roomDetails);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    public RoomDetailsDto updateRoom(Long id, RoomDetailsDto roomDetailsDto) {
        RoomDetails existingRoom = roomDetailsRepository.findById(id)
                .orElseThrow(() ->new ResourceNotFoundException("Room"));

        existingRoom.setFloor(roomDetailsDto.getFloor());
        existingRoom.setRoomType(roomDetailsDto.getRoomType());
        existingRoom.setCapacity(roomDetailsDto.getCapacity());
        existingRoom.setCurrentStudentCount(roomDetailsDto.getCurrentStudentCount());
        existingRoom.setAvailableStudentSlots(roomDetailsDto.getCapacity() - roomDetailsDto.getCurrentStudentCount());
        existingRoom.setStatus(roomDetailsDto.getStatus());

        RoomDetails updatedRoom = roomDetailsRepository.save(existingRoom);
        return RoomDetailsMapper.mapToRoomDetailsDto(updatedRoom);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    public List<RoomDetailsDto> getAllRooms() {
        List<RoomDetails> roomDetails = roomDetailsRepository.findAll();
        return roomDetails.stream()
                .map(rooms-> RoomDetailsMapper.mapToRoomDetailsDto(rooms))
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    public void deleteRoom(Long id) {
        RoomDetails roomDetails = roomDetailsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room"));
        roomDetailsRepository.delete(roomDetails);
    }
}
