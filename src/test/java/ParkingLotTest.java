import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.testng.AssertJUnit.assertFalse;

public class ParkingLotTest {

    @Test
    void shouldParkACarATheParking() {
        ParkingLot parkingLot = new ParkingLot(1);
        Car car = Car.createBasicCar();

        assertTrue(parkingLot.park(car));
    }

    @Test
    void shouldRemoveCarFromParkingLotWhenCarIsRetrieved() {
        ParkingLot parkingLot = new ParkingLot(1);
        Car car = Car.createBasicCar();
        parkingLot.park(car);
        parkingLot.retrieve(car);

        assertTrue(parkingLot.hasFreeSpace());
        assertFalse(parkingLot.retrieve(car));
    }


    @Test
    void shouldShowZeroWhenTheParkingLotIsFull() {
        ParkingLot parkingLot = new ParkingLot(1);
        parkingLot.park(Car.createBasicCar());

        assertFalse(parkingLot.hasFreeSpace());
    }

    @Test
    void shouldNotifyASubscriberWhenItIsFull() {
        Subscriber subscriber = mock(Subscriber.class);
        ParkingLot parkingLot = new ParkingLot(1);
        parkingLot.addSubscriber(subscriber, 100);
        parkingLot.park(Car.createBasicCar());

        verify(subscriber).notifyParkingLotAboveThreshold();
    }

    @Test
    void shouldNotifySubscriberWhenParkingChangesToNotFull() {
        Subscriber subscriber = mock(Subscriber.class);
        ParkingLot parkingLot = new ParkingLot(1);
        parkingLot.addSubscriber(subscriber, 100);
        Car car = Car.createBasicCar();
        parkingLot.park(car);
        parkingLot.retrieve(car);

        verify(subscriber).notifyParkingNoLongerFull();
    }

    @Test
    void shouldNotifyFBIWhenParkingTurns80PercentFull() {
        ParkingLot parkingLot = new ParkingLot(10);
        for (int i = 0; i < 8; i++) {
            parkingLot.park(Car.createBasicCar());
        }
        Subscriber subscriber = mock(Subscriber.class);
        parkingLot.addSubscriber(subscriber, 80);

        verify(subscriber).notifyParkingLotAboveThreshold();


    }
}
