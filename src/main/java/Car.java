
public class Car {
    private CarType type;

    private Car(CarType type) {
        this.type = type;
    }

    public static Car createLargeCar() {
        return new Car(CarType.LARGE);
    }

    public static Car createBasicCar() {
        return new Car(CarType.BASIC);
    }

    public static Car createHandicapCar() {
        return new Car(CarType.HANDICAP);
    }

    public boolean isHandicap() {
        return type==CarType.HANDICAP;
    }
}
