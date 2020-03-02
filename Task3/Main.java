package Task3;

public class Main {
    public static void main(String[] args) {
        Apple[] apples = new Apple[10];
        for (int i = 0; i < apples.length; i++) {
            apples[i] = new Apple();
        }

        Orange[] oranges = new Orange[5];
        for (int i = 0; i < oranges.length; i++) {
            oranges[i] = new Orange();
        }

        Apple[] apples1 = {};

        Box<Apple> appleBox = new Box<>(apples);
        Box<Orange> orangeBox = new Box<>(oranges);
        Box<Apple> appleBox1 = new Box<>(apples1);

        System.out.println("Вес коробки с яблоками: " + appleBox.getWeight());
        System.out.println("Вес коробки с апельсинами: " + orangeBox.getWeight());
        System.out.println("Вес второй коробки с яблоками: " + appleBox1.getWeight());
        System.out.println();

        System.out.println("Вес коробки с апельсинами равен весу коробки с яблоками? " + appleBox.compare(orangeBox));
        System.out.println("Вес коробки с яблоками равен самому себе? " + appleBox.compare(appleBox));
        System.out.println("Вес коробки с апельсинами равен самому себе? " + orangeBox.compare(orangeBox));
        System.out.println();

        System.out.println("Пересыпаем яблоки из первой коробки во вторую коробку");
        System.out.println();

        appleBox1.addFruitsFromBox(appleBox);
        System.out.println("Вес второй коробки: " + appleBox1.getWeight() + " " + "Вес первой коробки: " + appleBox.getWeight());

        System.out.println("Пересыпаем яблоки из второй коробки в саму себя");
        appleBox1.addFruitsFromBox(appleBox1);
        System.out.println("Вес второй коробки с яблоками: " + appleBox1.getWeight());

    }
}
