package com.driver.services.impl;

import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository1;
    @Autowired
    SpotRepository spotRepository1;
    @Override
    public ParkingLot addParkingLot(String name, String address) {
        ParkingLot newParkingLot = new ParkingLot();
        newParkingLot.setName(name);
        newParkingLot.setAddress(address);
        return parkingLotRepository.save(newParkingLot);

    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {
        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId)
                .orElseThrow(() -> new RuntimeException("Parking Lot not found"));
        
        SpotType spotType;
        if (numberOfWheels == 2) {
            spotType = SpotType.TWO_WHEELER;
        } else if (numberOfWheels == 4) {
            spotType = SpotType.FOUR_WHEELER;
        } else {
            spotType = SpotType.OTHERS;
        }

        Spot newSpot = new Spot();
        newSpot.setParkingLot(parkingLot);
        newSpot.setSpotType(spotType);
        newSpot.setPricePerHour(pricePerHour);
        newSpot.setOccupied(false);

        return spotRepository.save(newSpot);

    }

    @Override
    public void deleteSpot(int spotId) {
         spotRepository.deleteById(spotId);

    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {
        Spot spot = spotRepository.findById(spotId)
                .orElseThrow(() -> new RuntimeException("Spot not found"));

        if (spot.getParkingLot().getId() != parkingLotId) {
            throw new RuntimeException("Spot does not belong to specified Parking Lot");
        }

        spot.setPricePerHour(pricePerHour);
        return spotRepository.save(spot);

    }

    @Override
    public void deleteParkingLot(int parkingLotId) {
        parkingLotRepository.deleteById(parkingLotId);

    }
}
