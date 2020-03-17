package Race;

import Race.Run.Road;
import Race.Run.Tunnel;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class MainClass {
    public static final int CARS_COUNT = 6;

    public static CyclicBarrier cb = new CyclicBarrier(CARS_COUNT);
    public static Semaphore smp = new Semaphore((int) Math.floor(CARS_COUNT / 2));
    public static CountDownLatch onStartcdl = new CountDownLatch(CARS_COUNT);
    public static CountDownLatch onEndcdl = new CountDownLatch(CARS_COUNT);

    public static void main(String[] args) {
        Race race = new Race(new Road(1000), new Tunnel(), new Road(600));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 50 + i * 10);
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }
        try {
            onStartcdl.await();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
            onEndcdl.await();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
