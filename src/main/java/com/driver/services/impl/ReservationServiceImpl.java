
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
import java.util.Optional;

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
        //Reserve a spot in the given parkingLot such that the total price is minimum.
        // Note that the price per hour for each spot is different
        //Note that the vehicle can only be parked in a spot having a type equal to or larger than given vehicle
        //If parkingLot is not found, user is not found, or no spot is available, throw "Cannot make reservation" exception.

        User userObj;
        ParkingLot parkingLotObj;
        try{
            userObj = userRepository3.findById(userId).get();
            parkingLotObj = parkingLotRepository3.findById(parkingLotId).get();
        }catch(Exception e){
            throw new Exception("Cannot make reservation");
        }
        List<Spot> spotList = parkingLotObj.getSpotList();
        Spot spotObj = null;
        int minCost = Integer.MAX_VALUE;
        for(Spot spot : spotList){
            int wheels = 0;
            if(spot.getSpotType() == SpotType.FOUR_WHEELER){
                wheels = 4;
            }else if(spot.getSpotType() == SpotType.TWO_WHEELER){
                wheels = 2;
            }else{
                wheels = 24;
            }
            if(!spot.getOccupied() && numberOfWheels <= wheels && (timeInHours * spot.getPricePerHour() < minCost)){
                spotObj = spot;
                minCost = timeInHours * spot.getPricePerHour();
            }
        }

        if(spotObj == null){
            throw new Exception("Cannot make reservation");
        }

        Reservation reservation = new Reservation();
        reservation.setNumberOfHours(timeInHours);
        reservation.setSpot(spotObj);
        reservation.setUser(userObj);

        //saved User (Parent Entity)
        List<Reservation> reservationList = userObj.getReservationList();
        reservationList.add(reservation);
        userObj.setReservationList(reservationList);

        //saved Spot (Parent Entity)
        List<Reservation> reservationList2 = spotObj.getReservationList();
        reservationList2.add(reservation);
        spotObj.setReservationList(reservationList2);

        spotObj.setOccupied(true);
        userRepository3.save(userObj);
        spotRepository3.save(spotObj);
//unka yahi chhiye tha ke  hum ye  dono save kare pata nhi kyu  naki ye => reservation=reservationRepository3.save(reservation);
        return reservation;

    }
}
