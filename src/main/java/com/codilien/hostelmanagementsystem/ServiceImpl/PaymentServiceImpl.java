package com.codilien.hostelmanagementsystem.ServiceImpl;

import com.codilien.hostelmanagementsystem.exception.ExcessPaymentAmountException;
import com.codilien.hostelmanagementsystem.exception.ResourceNotFoundException;
import com.codilien.hostelmanagementsystem.DTO.PaymentDto;
import com.codilien.hostelmanagementsystem.model.Fee;
import com.codilien.hostelmanagementsystem.model.Payment;
import com.codilien.hostelmanagementsystem.repository.FeeRepository;
import com.codilien.hostelmanagementsystem.repository.PaymentRepository;
import com.codilien.hostelmanagementsystem.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@PreAuthorize("hasAnyRole('ADMIN', 'ACCOUNTANT')")
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final FeeRepository feeRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, FeeRepository feeRepository) {
        this.paymentRepository = paymentRepository;
        this.feeRepository = feeRepository;
    }


    @Override
    @Transactional
    public PaymentDto createPayment(PaymentDto paymentDto) {
        Payment payment = new Payment();
        payment.setPaymentAmount(paymentDto.getPaymentAmount());
        payment.setPaymentDate(paymentDto.getPaymentDate());
        payment.setPaymentMethod(paymentDto.getPaymentMethod());
        payment.setRemarks(paymentDto.getRemarks());

        Optional<Fee> fee = feeRepository.findById(paymentDto.getFeeId());
        if (fee.isPresent()) {
            payment.setFee(fee.get());
        }else{
            throw new ResourceNotFoundException("Fee");
        }

        Fee actualFee = fee.get();

        if (paymentDto.getPaymentAmount() > actualFee.getRemainingAmount()){
            throw new ExcessPaymentAmountException("The payment amount exceeds the remaining fee balance. Please enter a valid amount.");
        }

        //update remainingFee on the Fee table once the payment amount is entered.
        actualFee.setRemainingAmount(actualFee.getRemainingAmount() - paymentDto.getPaymentAmount());

        Payment savedPayment = paymentRepository.save(payment);
        return mapToDto(savedPayment);
    }

    @Override
    public PaymentDto getPayemnt(Long id) {
        Optional<Payment> payment = paymentRepository.findById(id);
        if (payment.isPresent()) {
            return mapToDto(payment.get());
        }
        else{
            throw new ResourceNotFoundException("Payment");
        }
    }

    @Override
    public List<PaymentDto> getAllPayment() {
        List<Payment> payments = paymentRepository.findAll();
        return payments.stream()
                .map(payment -> mapToDto(payment))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PaymentDto updatePayment(Long id, PaymentDto paymentDto) {
        Optional<Payment> paymentOpt = paymentRepository.findById(id);

        if (paymentOpt.isPresent()) {
            Payment payment = paymentOpt.get();
            payment.setPaymentAmount(paymentDto.getPaymentAmount());
            payment.setPaymentDate(paymentDto.getPaymentDate());
            payment.setPaymentMethod(paymentDto.getPaymentMethod());
            payment.setRemarks(paymentDto.getRemarks());

            Fee fee = feeRepository.findById(paymentDto.getFeeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Fee"));
            payment.setFee(fee);

            if (paymentDto.getPaymentAmount() > fee.getNetAmount()){
                throw new ExcessPaymentAmountException("The payment amount exceeds the remaining fee balance. Please enter a valid amount.");
            }

            //update the Remaining amount on the fee table once the paymentAmount is updated.
            fee.setRemainingAmount(fee.getRemainingAmount() - paymentDto.getPaymentAmount());

            Payment updatedPayment = paymentRepository.save(payment);
            return mapToDto(updatedPayment);
        }
        else{
            throw new ResourceNotFoundException("Payment");
        }
    }

    @Override
    public void deletePayment(Long id) {
        Payment toBeDeleted = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment"));
        paymentRepository.delete(toBeDeleted);
    }

    private PaymentDto mapToDto(Payment payment) {
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setId(payment.getId());
        paymentDto.setPaymentAmount(payment.getPaymentAmount());
        paymentDto.setPaymentDate(payment.getPaymentDate());
        paymentDto.setPaymentMethod(payment.getPaymentMethod());
        paymentDto.setRemarks(payment.getRemarks());

        if (payment.getFee() != null) {
            paymentDto.setFeeId(payment.getFee().getId());
        }

        return paymentDto;
    }
}

/**
 * while alloting the room, system check if student is asctive or not . if not active throw exception otherwise proccedds forward and check if roomStatus is Available or not if not throw exception is available proceeds forward.
 * and check the AvailableStudentSlot (from roomdetails table) for that room is greater than 0 or not if not throw exception otherwise proceeds.
 * On the successful room allotation, increase the currentStudentSlot by 1 and decrease the AvailableStudentSlot by 1  on roomdetails table for that paticular room
 *
 * (same happens for update the room)
 *
 * while creating fee, system check if student is active or not . if not active throw exception otherwise proceeds forward (same for update)
 * and set the penalty fee for 0, NetAMount as the TotalAmount and the Remaining Amount same as the Net Amount.
 *
 * Apply the penalty fee once the due date is crossed and RemainingAmount is greater than 0 (system checks this condition on midnight at 00:00 at every night)
 *
 * while creating payment, checks whether the PaymentAmount is greater than the RemainingAMount present on that fee record if it is greater than the Remaining balance throw the exception otherwise proceeds forward.
 * and Remaining amount (on fee table) is decreased once the payment record is created . (same for the update payment)
 *
 *(Student can:
 * Filed(create) Complaints
 * register themselves
 * view the complaints owned by themself only (checks if the Owner is them or not)
 * (view fee record, roomdetails, own details and update own details ) only if they belongs to them (checks if the owner is them or not..)
 *
 * Admin can :
 * add employee and access to all the system
 *
 * (employee can have other 2 roles : WARDEN and ACCOUNTANT)
 * WARDEN can:
 *  view, update and delete complaint and student records
 *  create,view, update, and delete room detail
 *  allot,view, update, and delete room allotment
 *  view their employee details if the details belongs to them -- check isOwner
 *
 *
 *  ACCOUNTANT can:
 *  create, view, update, and delete the fee and payment records
 *  view their employee details if the details belongs to them -- check isOwner
 *
 *
 *  (Note: for all employee, admin and Student have same login)
 */
