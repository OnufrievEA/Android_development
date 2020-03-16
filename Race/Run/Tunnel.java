package Race.Run;

import Race.Car;
import Race.MainClass;

public class Tunnel extends Stage {
    public Tunnel() {
        this.length = 1200;
        this.description = "Тоннель " + length + " метров";
    }

    @Override
    public void go(Car c) {
        try {
            try {
                MainClass.smp.acquire();
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);
                MainClass.smp.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}