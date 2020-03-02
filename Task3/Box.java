package Task3;

import java.util.ArrayList;
import java.util.Arrays;

public class Box<T extends Fruit> {

    ArrayList<T> fruitArray;

    public Box(T[] fruitArray) {
        this.fruitArray = new ArrayList<T>(Arrays.asList(fruitArray));
    }

    public float getWeight() {
        if (this.fruitArray.size() != 0) {
            return fruitArray.size() * fruitArray.get(0).getWeight();
        } else {
            return 0;
        }

    }

    public boolean compare(Box<?> box) {
        return Math.abs(this.getWeight() - box.getWeight()) < 0.0001;
    }

    private void addFruit(T fruit) {
        this.fruitArray.add(fruit);
    }

    public void addFruitsFromBox(Box<T> box) {
        if(this.equals(box)){
            System.out.println("Нельзя пересыпать фрукты из коробки в саму себя");
            return;
        }
        for (T fruit : box.fruitArray) {
            this.addFruit(fruit);
        }
        box.fruitArray.clear();
    }
}
