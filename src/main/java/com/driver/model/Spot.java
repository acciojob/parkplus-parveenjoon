
package com.driver.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Spot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(value = EnumType.STRING)
    private SpotType spotType;
    private int PricePerHour;
    private boolean occupied;


    @ManyToOne
    @JoinColumn
    private ParkingLot parkingLot;//it will automatically store id


    @OneToMany(mappedBy = "spot", cascade = CascadeType.ALL)
    List<Reservation> reservationList = new ArrayList<>();
    public Spot(SpotType spotType, int pricePerHour, boolean occupied){
        this.spotType = spotType;
        PricePerHour = pricePerHour;
        this.occupied = occupied;
    }
    public Spot(){

    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SpotType getSpotType() {
        return spotType;
    }

    public void setSpotType(SpotType spotType) {
        this.spotType = spotType;
    }

    public int getPricePerHour() {
        return PricePerHour;
    }

    public void setPricePerHour(int pricePerHour) {
        PricePerHour = pricePerHour;
    }

    public boolean getOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }
}
