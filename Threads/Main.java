import java.util.Arrays;

public class Main {

    final int size = 100000000;
    final int h = size / 2;
    float[] arr = new float[size];

    //Возвращаю обработанные массивы, чтобы сравнить результаты работы двух методов
    float[] firstMethod() {
        Arrays.fill(arr, 1);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println(System.currentTimeMillis() - startTime);
        return arr;
    }

    float[] secondMethod() {
        Arrays.fill(arr, 1);

        long startTime = System.currentTimeMillis();

        float[] a1 = new float[h];
        float[] a2 = new float[h];
        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < h; i++) {
                a1[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = h; i < arr.length; i++) {
                a2[i - h] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);

        System.out.println(System.currentTimeMillis() - startTime);
        return arr;
    }


    public static void main(String[] args) {
        Main m = new Main();
        float[] res1 = m.firstMethod();
        float[] res2 = m.secondMethod();

        //Сравниваю результаты
        System.out.println(Arrays.equals(res1, res2));
    }
}
