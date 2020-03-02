package Task1_2;

import java.util.ArrayList;
import java.util.Arrays;

class Main {

    static <T> T[] swapElements(T[] array, int pos1, int pos2){
        T temp = array[pos1];
        array[pos1] = array[pos2];
        array[pos2] = temp;
        return array;
    }

    static <T> ArrayList<T> arrayToArrayList(T[] array){
        ArrayList<T> arrayList = new ArrayList<T>(Arrays.asList(array));
        return arrayList;
    }

    public static void main(String[] args) {

        //Проверка метода, меняющего два элемента массива местами
        Integer[] myTestIntegerArray = {1, 2, 3, 4, 5};
        System.out.println(Arrays.toString(swapElements(myTestIntegerArray, 1, 3)));

        String[] myTestStringArray = {"one", "two", "three", "four", "five"};
        System.out.println(Arrays.toString(swapElements(myTestStringArray, 2, 4)));


        //Проверка метода, преобразующего массив в ArrayList
        System.out.println(arrayToArrayList(myTestIntegerArray).getClass().getSimpleName());
        System.out.println(arrayToArrayList(myTestStringArray).getClass().getSimpleName());
    }
}
