package Enums;

public class Main {

    static String getWorkingHours(DayOfWeek day){
        if(day.ordinal() < 5){
            return String.valueOf(8 * (5 - day.ordinal()));
        } else {
            return "Сегодня выходной";
        }
    }

    public static void main(String[] args) {
        for (DayOfWeek day: DayOfWeek.values()) {
            System.out.println(getWorkingHours(day));
        }
    }
}
