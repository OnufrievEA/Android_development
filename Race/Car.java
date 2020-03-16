package Race;

public class Car implements Runnable {
    private static boolean finished = false;
    private static int CARS_COUNT;
    private Race race;
    private int speed;
    private String name;
    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }
    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 10000));
            System.out.println(this.name + " готов");
            MainClass.cb.await();
            MainClass.onStartcdl.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        if(!finished){
            finished = true;
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Победитель!!! " + this.name + " первым пришёл к финишу!!!");
        }
        MainClass.onEndcdl.countDown();
    }
}