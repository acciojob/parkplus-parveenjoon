package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId)
                .orElseThrow(() -> new RuntimeException("Parking Lot not found"));

        List<Spot> availableSpots = spotRepository.findByParkingLotIdAndOccupiedFalse(parkingLotId);
        if (availableSpots.isEmpty()) {
            throw new Exception("No spot available for reservation");
        }

        Spot spotToReserve = null;
        int minPrice = Integer.MAX_VALUE;
        for (Spot spot : availableSpots) {
            if (spot.getSpotType().ordinal() >= SpotType.valueOf("FOUR_WHEELER").ordinal() && numberOfWheels > 4) {
                if (spot.getPricePerHour() < minPrice) {
                    spotToReserve = spot;
                    minPrice = spot.getPricePerHour();
                }
            } else if (spot.getSpotType().ordinal() >= SpotType.valueOf("TWO_WHEELER").ordinal() && numberOfWheels <= 4) {
                if (spot.getPricePerHour() < minPrice) {
                    spotToReserve = spot;
                    minPrice = spot.getPricePerHour();
                }
            }
        }

        if (spotToReserve == null) {
            throw new Exception("No suitable spot available for reservation");
        }

        spotToReserve.setOccupied(true);
        spotRepository.save(spotToReserve);

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setSpot(spotToReserve);
        reservation.setNumberOfHours(timeInHours);

        return reservationRepository.save(reservation);
    }
}
