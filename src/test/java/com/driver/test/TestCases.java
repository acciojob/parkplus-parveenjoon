package com.driver.test;

import com.driver.model.*;
import com.driver.repository.*;
import com.driver.services.ReservationService;
import com.driver.services.impl.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestCases {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SpotRepository spotRepository;

    @Mock
    private ParkingLotRepository parkingLotRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @InjectMocks
    private ParkingLotServiceImpl parkingLotService;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Before
    public void setUp() {
        when(userRepository.save(any(User.class))).thenReturn(new User());
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(new User()));

        when(parkingLotRepository.save(any(ParkingLot.class))).thenReturn(new ParkingLot());
        when(parkingLotRepository.findById(anyInt())).thenReturn(Optional.of(new ParkingLot()));

        when(spotRepository.save(any(Spot.class))).thenReturn(new Spot());
        when(spotRepository.findById(anyInt())).thenReturn(Optional.of(new Spot()));

        when(reservationRepository.save(any(Reservation.class))).thenReturn(new Reservation());
    }

    @Test
    public void testRegisterUser() {
        User user = userService.register("John", "1234567890", "password");
        assertEquals("John", user.getName());
        assertEquals("1234567890", user.getPhoneNumber());
        assertEquals("password", user.getPassword());
    }

    @Test
    public void testAddParkingLot() {
        ParkingLot parkingLot = parkingLotService.addParkingLot("Test Parking Lot", "Test Address");
        assertEquals("Test Parking Lot", parkingLot.getName());
        assertEquals("Test Address", parkingLot.getAddress());
    }

    @Test
    public void testAddSpot() {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setId(1);
        Spot spot = parkingLotService.addSpot(1, 4, 10);
        assertEquals(parkingLot, spot.getParkingLot());
        assertEquals(SpotType.FOUR_WHEELER, spot.getSpotType());
        assertEquals(Integer.valueOf(10), spot.getPricePerHour());
    }

    @Test
    public void testReserveSpot() throws Exception {
        User user = new User();
        user.setId(1);
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setId(1);
        Spot spot = new Spot();
        spot.setId(1);
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        when(parkingLotRepository.findById(anyInt())).thenReturn(Optional.of(parkingLot));
        when(spotRepository.findFirstByParkingLotAndSpotTypeOrderByPricePerHourAsc(any(ParkingLot.class), any(SpotType.class))).thenReturn(Optional.of(spot));

        Reservation reservation = reservationService.reserveSpot(1, 1, 2, 4);
        assertEquals(user, reservation.getUser());
        assertEquals(spot, reservation.getSpot());
        assertEquals(2, reservation.getNumberOfHours());
    }
}
