
package com.driver.services.impl;

import com.driver.model.ParkingLot;
import com.driver.model.Spot;
import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository1;
    @Autowired
    SpotRepository spotRepository1;
    @Override
    public ParkingLot addParkingLot(String name, String address) {
        ParkingLot parkingLot=new ParkingLot();
        parkingLot.setName(name);
        parkingLot.setAddress(address);
        //List<Spot> spotList=new ArrayList<>();
        //parkingLot.setSpotList(spotList);
        ParkingLot ParkingLotObjSave  = parkingLotRepository1.save(parkingLot);
        return ParkingLotObjSave;
    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {
        Optional<ParkingLot> parkingLotOpt = parkingLotRepository1.findById(parkingLotId);
        ParkingLot parkingLotObj = parkingLotOpt.get();

        Spot spotEntityObj = new Spot();
        if(numberOfWheels <= 2){
            spotEntityObj.setSpotType(SpotType.TWO_WHEELER);
        }else if(numberOfWheels <= 4){
            spotEntityObj.setSpotType(SpotType.FOUR_WHEELER);
        }else{
            spotEntityObj.setSpotType(SpotType.OTHERS);
        }
        spotEntityObj.setPricePerHour(pricePerHour);
        spotEntityObj.setParkingLot(parkingLotObj);
        spotEntityObj.setOccupied(Boolean.FALSE);
//        parkingLotObj.getSpotList().add(spotEntityObj);
        List<Spot> spotList = parkingLotObj.getSpotList();
        spotList.add(spotEntityObj);
        parkingLotObj.setSpotList(spotList);
        parkingLotRepository1.save(parkingLotObj);
        return spotEntityObj;
    }

    @Override
    public void deleteSpot(int spotId) {
        spotRepository1.deleteById(spotId);
    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {
        Optional<ParkingLot> parkingLotOpt = parkingLotRepository1.findById(parkingLotId);
        ParkingLot parkingLotObj = parkingLotOpt.get();
        List<Spot> spotList = parkingLotObj.getSpotList();
        Spot spotObj = null;
        for (Spot spot : spotList) {
            if (spot.getId() == spotId) {
                spot.setPricePerHour(pricePerHour);
                spotObj = spot;
            }
        }
        parkingLotObj.setSpotList(spotList);
        parkingLotRepository1.save(parkingLotObj);
        Spot toReturnSpot = spotRepository1.save(spotObj);
        return toReturnSpot;
    }

    @Override
    public void deleteParkingLot(int parkingLotId) {
        parkingLotRepository1.deleteById(parkingLotId);
    }
}
