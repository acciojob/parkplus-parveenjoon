package com.driver.controllers;

import com.driver.model.Payment;
import com.driver.services.impl.PaymentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/payment")
public class PaymentController {

    @Autowired
    private PaymentServiceImpl paymentService;

    @PostMapping("/pay")
    public Payment pay(@RequestParam Integer reservationId, @RequestParam Integer amountSent, @RequestParam String mode) throws Exception {
        return paymentService.pay(reservationId, amountSent, mode);
    }
}
