package com.codilien.hostelmanagementsystem.ServiceImpl;

import com.codilien.hostelmanagementsystem.exception.InactiveStudentException;
import com.codilien.hostelmanagementsystem.exception.ResourceNotFoundException;
import com.codilien.hostelmanagementsystem.DTO.FeeDto;
import com.codilien.hostelmanagementsystem.model.Fee;
import com.codilien.hostelmanagementsystem.model.Student;
import com.codilien.hostelmanagementsystem.repository.FeeRepository;
import com.codilien.hostelmanagementsystem.repository.StudentRepository;
import com.codilien.hostelmanagementsystem.service.FeeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeeServiceImpl implements FeeService {

    private FeeRepository feeRepository;
    private StudentRepository studentRepository;

    @Autowired
    public FeeServiceImpl(FeeRepository feeRepository,
                          StudentRepository studentRepository) {

        this.feeRepository = feeRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'ACCOUNTANT')")
    public FeeDto createFee(FeeDto feeDto) {
        try {
            Optional<Student> studentOpt = studentRepository.findById(feeDto.getStudentId());
            if (studentOpt.isEmpty()) {
                throw new ResourceNotFoundException("Student");
            }

            Student student = studentOpt.get();
            // Check if the Student status is Active
            if (!student.isActive()) {
                throw new InactiveStudentException("Fee cannot be created. Student status is inactive.");
            }

            Fee fee = new Fee();
            fee.setTotalAmount(feeDto.getTotalAmount());
            fee.setPenaltyFee(0.0);
            fee.setNetAmount(fee.getTotalAmount() + fee.getPenaltyFee());
            fee.setRemainingAmount(fee.getNetAmount());
            fee.setDueDate(feeDto.getDueDate());
            fee.setStudent(studentOpt.get());

            Fee savedFee = feeRepository.save(fee);
            return mapToDto(savedFee);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Fee for the student is already Created.");
        }
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'ACCOUNTANT') or ((hasRole('STUDENT') and @securityService.isOwner(#id, 'FEE')))")
    public FeeDto getFee(Long id) {
        Fee fee = feeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fee"));

        return mapToDto(fee);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'ACCOUNTANT')")
    public List<FeeDto> getAllFee() {
        List<Fee> fees = feeRepository.findAll();
        return fees.stream()
                .map(fee -> mapToDto(fee))
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'ACCOUNTANT')")
    @Transactional
    public FeeDto updateFee(Long id, FeeDto feeDto) {
        try {
            Optional<Fee> existingFeeOpt = feeRepository.findById(id);
            if (existingFeeOpt.isEmpty()) {
                throw new ResourceNotFoundException("Fee");
            }

            Fee toBeUpdated = existingFeeOpt.get();
            toBeUpdated.setTotalAmount(feeDto.getTotalAmount());
            toBeUpdated.setDueDate(feeDto.getDueDate());

            // Set penalty fee to 0 if it's null
            double penaltyFee = 0.0;
            if (toBeUpdated.getPenaltyFee() != null) {
                penaltyFee = toBeUpdated.getPenaltyFee();
            }

            // Calculate net amount (use provided value or calculate with actual + penalty)
            double netAmount = 0.0;
            if (feeDto.getNetAmount() != null) {
                netAmount = feeDto.getNetAmount();
            } else {
                netAmount = toBeUpdated.getTotalAmount() + penaltyFee;
            }
            toBeUpdated.setNetAmount(netAmount);

            // Set remaining amount
            double remainingAmount = netAmount;
            if (feeDto.getRemainingAmount() != null) {
                remainingAmount = feeDto.getRemainingAmount();
            }
            toBeUpdated.setRemainingAmount(remainingAmount);

            // Update student if studentId is provided
            if (feeDto.getStudentId() != null) {
                Optional<Student> studentOpt = studentRepository.findById(feeDto.getStudentId());
                if (studentOpt.isEmpty()) {
                    throw new ResourceNotFoundException("Student");
                }

                Student student = studentOpt.get();
                if (!student.isActive()) {
                    throw new InactiveStudentException("Fee cannot be updated. Student status is inactive.");
                }

                toBeUpdated.setStudent(student);
            }

            // Save the updated fee and return DTO
            Fee updatedFee = feeRepository.save(toBeUpdated);
            return mapToDto(updatedFee);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Fee for the student is already Created.");
        }
    }


    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'ACCOUNTANT')")
    public void deleteFee(Long id) {
        Fee toBeDeleted = feeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fee"));
        feeRepository.delete(toBeDeleted);
    }


    @Scheduled(cron = "0 0 0 * * ?")   // Checks if the fee is overdue or not and update the penalty on daily basis
//    @Scheduled(cron = "0 48 17 * * ?")
public void applyPenaltyIfDueDatePassed() {
        LocalDate currentDate = LocalDate.now();

        // Find all fees that have crossed the due date and fee is not cleared
        List<Fee> overdueFees = feeRepository.findOverdue(currentDate, 0);

        for (Fee fee : overdueFees) {
            if (fee.getPenaltyFee() == 0) {
                double penaltyFee = fee.getTotalAmount() * 0.10;   // 10% penalty based on the actual amount
                fee.setPenaltyFee(penaltyFee);
                fee.setNetAmount(fee.getTotalAmount() + penaltyFee);
                fee.setRemainingAmount(fee.getNetAmount());
                feeRepository.save(fee);
            }
        }
    }


    //Helper Method to convert entity to Dto
    private FeeDto mapToDto(Fee fee){
        FeeDto feeDto = new FeeDto();
        feeDto.setId(fee.getId());
        feeDto.setTotalAmount(fee.getTotalAmount());
        feeDto.setDueDate(fee.getDueDate());
        feeDto.setPenaltyFee(fee.getPenaltyFee());
        feeDto.setNetAmount(fee.getNetAmount());
        feeDto.setRemainingAmount(fee.getRemainingAmount());
        feeDto.setStudentId(fee.getStudent().getId());
        return feeDto;
    }
}
