package com.driver.services.impl;

import com.driver.model.ParkingLot;
import com.driver.model.Spot;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import com.driver.model.SpotType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private SpotRepository spotRepository;

    @Override
    public ParkingLot addParkingLot(String name, String address) {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName(name);
        parkingLot.setAddress(address);
        return parkingLotRepository.save(parkingLot);
    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {
        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId).orElseThrow(() -> new RuntimeException("Parking Lot not found"));
        Spot spot = new Spot();
        spot.setParkingLot(parkingLot);
        spot.setSpotType(getSpotType(numberOfWheels));
        spot.setPricePerHour(pricePerHour);
        return spotRepository.save(spot);
    }

    @Override
    public void deleteSpot(int spotId) {
        spotRepository.deleteById(spotId);
    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {
        Spot spot = spotRepository.findById(spotId).orElseThrow(() -> new RuntimeException("Spot not found"));
        spot.setPricePerHour(pricePerHour);
        return spotRepository.save(spot);
    }

    @Override
    public void deleteParkingLot(int parkingLotId) {
        parkingLotRepository.deleteById(parkingLotId);
    }

    private SpotType getSpotType(Integer numberOfWheels) {
        if (numberOfWheels == 2) {
            return SpotType.TWO_WHEELER;
        } else if (numberOfWheels == 4) {
            return SpotType.FOUR_WHEELER;
        } else {
            return SpotType.OTHERS;
        }
    }
}
