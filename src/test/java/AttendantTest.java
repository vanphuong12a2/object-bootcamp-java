import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AttendantTest {

    @Test
    void shouldReturnAssignedParking() {
        ParkingLot parkingLot = new ParkingLot(1);
        Attendant attendant = new Attendant();
        attendant.monitor(parkingLot);

        assertEquals(attendant.indicateFreeParking(Car.createBasicCar()).get(), parkingLot);
    }

    @Test
    void shouldReturnOneParkingLotGivenAListOfParkingLots() {
        ParkingLot parkingLot1 = new ParkingLot(1);
        ParkingLot parkingLot2 = new ParkingLot(1);
        Attendant attendant = new Attendant();
        attendant.monitor(parkingLot1);
        attendant.monitor(parkingLot2);

        assertTrue(attendant.indicateFreeParking(Car.createBasicCar()).get() == parkingLot1 || attendant.indicateFreeParking(Car.createBasicCar()).get() == parkingLot2);
    }

    @Test
    void shouldReturnAllParkingLotsFromTheAssignedOnes() {
        Attendant attendant = new Attendant();
        ParkingLot parkingLot1 = new ParkingLot(500);
        attendant.monitor(parkingLot1);
        ParkingLot parkingLot2 = new ParkingLot(500);
        attendant.monitor(parkingLot2);

        ParkingLot firstReturnedLot = attendant.indicateFreeParking(Car.createBasicCar()).get();
        parkingLot1.park(Car.createBasicCar());
        ParkingLot secondReturnedLot  = attendant.indicateFreeParking(Car.createBasicCar()).get();
        secondReturnedLot.park(Car.createBasicCar());

        assertTrue(firstReturnedLot == parkingLot1 || firstReturnedLot == parkingLot2);
        assertTrue(secondReturnedLot == parkingLot1 || secondReturnedLot == parkingLot2);
        assertNotEquals(firstReturnedLot, secondReturnedLot);
    }

    @Test
    void shouldReturnParkingLotWithLessParkedCars() {
        Attendant attendant = new Attendant();
        ParkingLot busyParkingLot = new ParkingLot(2);
        busyParkingLot.park(Car.createBasicCar());
        busyParkingLot.park(Car.createBasicCar());
        ParkingLot lessBusyParkingLot = new ParkingLot(2);
        lessBusyParkingLot.park(Car.createBasicCar());
        attendant.monitor(busyParkingLot);
        attendant.monitor(lessBusyParkingLot);

        assertEquals(lessBusyParkingLot, attendant.indicateFreeParking(Car.createBasicCar()).get());
    }

    @Test
    void shouldReturnParkingLotWithMostFreeSpacesForALargeCar() {
        ParkingLot parkingLot = new ParkingLot(1);
        ParkingLot parkingLotWithMostFreeSpace = new ParkingLot(2);
        Attendant attendant = new Attendant();

        attendant.monitor(parkingLot);
        attendant.monitor(parkingLotWithMostFreeSpace);

        assertEquals(attendant.indicateFreeParking(Car.createLargeCar()).get(), parkingLotWithMostFreeSpace);
    }

    @Test
    void shouldReturnClosestParkingLotWithFreeSpaceForAHandicappedCar() {
        ParkingLot parkingLot1 = new ParkingLot(1);
        ParkingLot parkingLot2 = new ParkingLot(2);
        Attendant attendant = new Attendant();

        attendant.monitor(parkingLot1);
        attendant.monitor(parkingLot2);

        assertEquals(attendant.indicateFreeParking(Car.createHandicapCar()).get(), parkingLot1);
    }

    @Test
    void shouldReturnClosestNonEmptyParkingLotWithFreeSpaceForAHandicappedCar() {
        ParkingLot parkingLot = new ParkingLot(2);
        ParkingLot parkingLotWithoutFreeSpace = new ParkingLot(0);
        Attendant attendant = new Attendant();
        attendant.monitor(parkingLot);
        attendant.monitor(parkingLotWithoutFreeSpace);

        assertEquals(attendant.indicateFreeParking(Car.createBasicCar()), Optional.of(parkingLot));
    }
}
