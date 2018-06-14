import java.util.ArrayList;

public class ParkingLot implements Comparable{
    private ArrayList<Car> cars;
    private int capacity;
    private Subscriber subscriber;

    public ParkingLot(int capacity) {
        this.cars = new ArrayList<Car>();
        this.capacity = capacity;
    }

    public boolean park(Car car) {
        this.cars.add(car);
        if (isFull() && this.subscriber != null) this.subscriber.notifyParkingLotAboveThreshold();
        return true;
    }

    private boolean isFull() {
        return freeSlots() == 0;
    }

    public void addSubscriber(Subscriber subscriber, int threshold) {
        this.subscriber = subscriber;
    }

    public boolean retrieve(Car car){
        boolean wasFull = isFull();
        boolean isRemoved = cars.remove(car);
        if(isRemoved && wasFull && subscriber != null) subscriber.notifyParkingNoLongerFull();
        return isRemoved;
    }

    private int freeSlots() {
        return this.capacity - this.cars.size();
    }

    @Override
    public int compareTo(Object o) {
        //can break break break
        return ((ParkingLot) o).freeSlots() - this.freeSlots();
    }

    public boolean hasFreeSpace() {
        return freeSlots() > 0;
    }
}
