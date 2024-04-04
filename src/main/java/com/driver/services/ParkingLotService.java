package com.driver.services;

import com.driver.model.ParkingLot;
import com.driver.model.Spot;

public interface ParkingLotService {

    ParkingLot addParkingLot(String name, String address);

    Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour);

    void deleteSpot(int spotId);

    Spot updateSpot(int parkingLotId, int spotId, int pricePerHour);

    void deleteParkingLot(int parkingLotId);
}
