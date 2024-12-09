package com.codilien.hostelmanagementsystem.controller;

import com.codilien.hostelmanagementsystem.DTO.PaymentDto;
import com.codilien.hostelmanagementsystem.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create")
    public ResponseEntity<PaymentDto> addPayment(@RequestBody PaymentDto paymentDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.createPayment(paymentDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> getPayment(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.getPayemnt(id));
    }

    @GetMapping({"/", " "})
    public ResponseEntity<List<PaymentDto>> getAllPayment(){
        List<PaymentDto> payments = paymentService.getAllPayment();
        return ResponseEntity.ok(payments);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<PaymentDto> updatePayment(@PathVariable Long id, @RequestBody PaymentDto paymentDto){
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.updatePayment(id, paymentDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePayment(@PathVariable Long id){
        paymentService.deletePayment(id);
        return ResponseEntity.ok("Payment record deleted successfully.");
    }
}
