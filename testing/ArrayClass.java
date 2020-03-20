import java.util.Arrays;

public class ArrayClass {

    public int[] getSubArray(int[] arr) {
        if (arr.length == 0) {
            throw new RuntimeException("Пустой массив");
        }
        int index = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 4) {
                index = i;
            }
        }
        if (index == -1) {
            throw new RuntimeException("В массиве отсутствует число 4");
        }
        return Arrays.copyOfRange(arr, index + 1, arr.length);
    }

    public boolean containsFoursAndOnes(int[] arr) {
        boolean hasFours = false;
        boolean hasOnes = false;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 1) {
                hasOnes = true;
            } else if (arr[i] == 4) {
                hasFours = true;
            } else {
                return false;
            }
        }
        if (hasFours && hasOnes) {
            return true;
        }
        return false;
    }
}