package Exceptions;// неиспользуемые конструкторы создал для полноты картины

public class MyArrayDataException extends Exception {

    private int row;
    private int column;

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public MyArrayDataException(String message, int row, int column) {
        super(message);
        this.row = row;
        this.column = column;
    }

    public MyArrayDataException(int row, int column) {
        this.row = row;
        this.column = column;
    }


    public MyArrayDataException() {
    }

}
