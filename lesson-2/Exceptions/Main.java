package Exceptions;

public class Main {

    static int myMeth(String[][] myArray) throws MyArraySizeException, MyArrayDataException {
        if (myArray.length != 4) {
            throw new MyArraySizeException("Ошибка размера массива");
        }
        for (int i = 0; i < myArray.length; i++) {
            if (myArray[i].length != 4) {
                throw new MyArraySizeException("Ошибка размера массива");
            }
        }

        int sum = 0;

        for (int i = 0; i < myArray.length; i++) {
            for (int j = 0; j < myArray[i].length; j++) {
                try {
                    sum += Integer.parseInt(myArray[i][j]);
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException(i, j);
                }
            }
        }

        return sum;
    }

    public static void main(String[] args) {

        String[][] myArray1 = {{"1", "2", "3", "4"},
                {"5", "6", "7", "8"},
                {"9", "10", "11", "12"},
                {"13", "14", "15", "16"}
        };

        String[][] myArray2 = {{"1", "2.2", "3", "4"},
                {"5", "6", "7", "8"},
                {"9", "10", "11", "12"},
                {"13", "14", "15", "16"}
        };

        String[][] myArray3 = {{"1", "2", "3", "4", "вот это поворот"},
                {"5", "6", "7", "8"},
                {"9", "10", "11", "12"},
                {"13", "14", "15", "16"}
        };

        String[][][] totalArray = {myArray1, myArray2, myArray3};


        for (int i = 0; i < totalArray.length; i++) {
            try {
                System.out.println(myMeth(totalArray[i]));
            } catch (MyArraySizeException e) {
                System.out.println(e.getMessage());
            } catch (MyArrayDataException e) {
                System.out.println("Ошибка формата данных в ячейке " + "[" + e.getRow() + "]" + "[" + e.getColumn() + "]");
            }
        }

    }
}
