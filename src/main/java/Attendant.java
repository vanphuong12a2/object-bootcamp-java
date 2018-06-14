import java.util.*;

public class Attendant {
    private List<ParkingLot> parkingLots;

    public Attendant() {
        this.parkingLots = new ArrayList<>();
    }

    public void monitor(ParkingLot parkingLot) {
        this.parkingLots.add(parkingLot);
    }

    public Optional<ParkingLot> indicateFreeParking(Car car) {
        if(car.isHandicap()) {
            return parkingLots.stream().filter(parkingLotLot -> parkingLotLot.hasFreeSpace()).findFirst();
        }
        return parkingLots.stream().sorted().findFirst();
    }

}
