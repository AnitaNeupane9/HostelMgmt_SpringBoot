package com.codilien.hostelmanagementsystem.ServiceImpl;

import com.codilien.hostelmanagementsystem.exception.*;
import com.codilien.hostelmanagementsystem.DTO.RoomAllotmentDto;
import com.codilien.hostelmanagementsystem.Enum.RoomStatus;
import com.codilien.hostelmanagementsystem.mapper.RoomAllotmentMapper;
import com.codilien.hostelmanagementsystem.model.RoomAllotment;
import com.codilien.hostelmanagementsystem.model.RoomDetails;
import com.codilien.hostelmanagementsystem.model.Student;
import com.codilien.hostelmanagementsystem.repository.RoomAllotmentRepository;
import com.codilien.hostelmanagementsystem.repository.RoomDetailsRepository;
import com.codilien.hostelmanagementsystem.repository.StudentRepository;
import com.codilien.hostelmanagementsystem.service.RoomAllotmentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
public class RoomAllotmentServiceImpl implements RoomAllotmentService {


    private StudentRepository studentRepository;
    private RoomDetailsRepository roomDetailsRepository;
    private RoomAllotmentRepository roomAllotmentRepository;

    @Autowired
    public RoomAllotmentServiceImpl(
            StudentRepository studentRepository,
            RoomDetailsRepository roomDetailsRepository,
            RoomAllotmentRepository roomAllotmentRepository) {

        this.studentRepository = studentRepository;
        this.roomDetailsRepository = roomDetailsRepository;
        this.roomAllotmentRepository = roomAllotmentRepository;
    }

    @Override
    @Transactional
    public RoomAllotmentDto allotRoom(RoomAllotmentDto roomAllotmentDto) {

        try {
            RoomAllotment roomAllotment = RoomAllotmentMapper.mapToRoomAllotment(roomAllotmentDto);

            // Fetch the student, throw exception if not found
            Student student = studentRepository.findById(roomAllotmentDto.getStudentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Student"));

            // Check if the student's status is ACTIVE
            if (!student.isActive()) {
                throw new InactiveStudentException("Student status must be Active to be assigned to a room.");
            }

            // Fetch the room details, throw  exception if not found
            RoomDetails roomDetails = roomDetailsRepository.findById(roomAllotmentDto.getRoomId())
                    .orElseThrow(() -> new ResourceNotFoundException("Room"));

            // Check if the room has available slots, throw exception if room is full
            if (roomDetails.getAvailableStudentSlots() <= 0) {
                throw new RoomFullException(roomDetails.getRoomNumber());
            }

            // Check if the room status is AVAILABLE
            if (roomDetails.getStatus() != RoomStatus.AVAILABLE) {
                throw new RoomUnavailableException("Room status should be 'AVAILABLE' to allot room." );
            }
            // Set the actual Student and RoomDetails objects in the RoomAllotment entity
            roomAllotment.setStudent(student);
            roomAllotment.setRoomDetails(roomDetails);

            roomDetails.setCurrentStudentCount(roomDetails.getCurrentStudentCount() + 1);
            roomDetails.setAvailableStudentSlots(roomDetails.getAvailableStudentSlots() - 1);

            // Save RoomDetails first
            roomDetailsRepository.save(roomDetails);

            RoomAllotment savedRoomAllotment = roomAllotmentRepository.save(roomAllotment);
            return RoomAllotmentMapper.mapToRoomAllotmentDto(savedRoomAllotment);

        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Room for the student is already allotted.");
        }
    }

    @Override
    public RoomAllotmentDto getRoomAllotment(Long id) {
        RoomAllotment roomAllotment = roomAllotmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room Allotment"));
        return RoomAllotmentMapper.mapToRoomAllotmentDto(roomAllotment);
    }

    @Override
    public List<RoomAllotmentDto> getAllRoomAllotment() {
        List<RoomAllotment> roomAllotments = roomAllotmentRepository.findAll();
        return roomAllotments.stream()
                .map(allotments -> RoomAllotmentMapper.mapToRoomAllotmentDto(allotments))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RoomAllotmentDto updateRoomAllotment(Long id, RoomAllotmentDto roomAllotmentDto) {
        try {
            RoomAllotment toBeUpdatedAllotment = roomAllotmentRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Room Allotment not found"));

            // Store the old RoomDetails
            RoomDetails oldRoom = toBeUpdatedAllotment.getRoomDetails();

            // Update the RoomAllotment fields
            toBeUpdatedAllotment.setAllotmentDate(roomAllotmentDto.getAllotmentDate());
            toBeUpdatedAllotment.setDuration(roomAllotmentDto.getDuration());
            toBeUpdatedAllotment.setCheckIn(roomAllotmentDto.getCheckIn());
            toBeUpdatedAllotment.setCheckOut(roomAllotmentDto.getCheckOut());

            // Check if the student is being updated is valid or not
            if (roomAllotmentDto.getStudentId() != null && !roomAllotmentDto.getStudentId().equals(toBeUpdatedAllotment.getStudent().getId())) {
                Student student = studentRepository.findById(roomAllotmentDto.getStudentId())
                        .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

                // Check if the student's status is ACTIVE
                if (!student.isActive()) {
                    throw new InactiveStudentException("Student status must be Active to be assigned to a room.");
                }

                // Update the student in the RoomAllotment
                toBeUpdatedAllotment.setStudent(student);
            }

            // Check if the room is being updated is valid or not
            if (roomAllotmentDto.getRoomId() != null && !roomAllotmentDto.getRoomId().equals(toBeUpdatedAllotment.getRoomDetails().getId())) {
                // Fetch the new room from the database
                RoomDetails newRoom = roomDetailsRepository.findById(roomAllotmentDto.getRoomId())
                        .orElseThrow(() -> new ResourceNotFoundException("Room not found"));

                // Check if the room's status is AVAILABLE
                if (newRoom.getStatus() != RoomStatus.AVAILABLE) {
                    throw new RoomUnavailableException("Room status should be 'AVAILABLE' to allot room.");
                }

                // Set the new room in the RoomAllotment
                toBeUpdatedAllotment.setRoomDetails(newRoom);

                // Update the old room's current student count and available student slots
                if (oldRoom != null) {
                    oldRoom.setCurrentStudentCount(oldRoom.getCurrentStudentCount() - 1);
                    oldRoom.setAvailableStudentSlots(oldRoom.getAvailableStudentSlots() + 1);
                    roomDetailsRepository.save(oldRoom);
                }

                // Update the new room's current student count and available student slots
                newRoom.setCurrentStudentCount(newRoom.getCurrentStudentCount() + 1);
                newRoom.setAvailableStudentSlots(newRoom.getAvailableStudentSlots() - 1);
                roomDetailsRepository.save(newRoom);
            }

            RoomAllotment updatedAllotment = roomAllotmentRepository.save(toBeUpdatedAllotment);
            return RoomAllotmentMapper.mapToRoomAllotmentDto(updatedAllotment);

        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Room for the student is already allotted.");
        }
    }


    @Override
    @Transactional
    public void deleteRoomAllotment(Long id) {

        RoomAllotment toBeDeletedAllotment = roomAllotmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room Allotment"));

        RoomDetails roomDetails = toBeDeletedAllotment.getRoomDetails();

        if (roomDetails != null) {
            // decrease currentStudentCount and increase availableStudentSlots
            roomDetails.setCurrentStudentCount(roomDetails.getCurrentStudentCount() - 1);
            roomDetails.setAvailableStudentSlots(roomDetails.getAvailableStudentSlots() + 1);
            roomDetailsRepository.save(roomDetails);
        }
        roomAllotmentRepository.delete(toBeDeletedAllotment);
    }
}
