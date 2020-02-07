package Phonebook;

public class Main {
    public static void main(String[] args) {
        PhoneBook myBook = new PhoneBook();
        myBook.add("Ivanov", "1");
        myBook.add("Petrov", "2");
        myBook.add("Putin", "3");
        myBook.add("Putout", "4");
        myBook.add("Obama", "5");
        myBook.add("Trump", "6");
        myBook.add("Ivanov", "11");
        myBook.add("Petrov", "22");
        myBook.add("Putin", "33");


        System.out.println(myBook.get("Ivanov"));
        System.out.println(myBook.get("Petrov"));
        System.out.println(myBook.get("Putin"));
        System.out.println(myBook.get("Putout"));
        System.out.println(myBook.get("Obama"));
        System.out.println(myBook.get("Trump"));
        System.out.println(myBook.get("Gogol"));


    }
}
