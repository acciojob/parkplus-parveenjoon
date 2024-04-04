package com.driver.services.impl;

import com.driver.model.PaymentMode;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        int billAmount = reservation.getNumberOfHours() * reservation.getSpot().getPricePerHour();
        if (amountSent < billAmount) {
            throw new Exception("Insufficient Amount");
        }

        PaymentMode paymentMode = PaymentMode.valueOf(mode.toUpperCase());
        if (paymentMode == null) {
            throw new Exception("Payment mode not detected");
        }

        Payment payment = new Payment();
        payment.setReservation(reservation);
        payment.setPaymentCompleted(true);
        payment.setPaymentMode(paymentMode);

        return paymentRepository.save(payment);
    }
}
