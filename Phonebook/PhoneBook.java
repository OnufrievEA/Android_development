package Phonebook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhoneBook {
    private Map<String, List<String>> phoneBook = new HashMap<>();

    public void add(String surname, String phoneNumber) {
        List<String> currentPhoneList = phoneBook.get(surname);
        if (currentPhoneList == null) {
            phoneBook.put(surname, new ArrayList<>());
            phoneBook.get(surname).add(phoneNumber);
        } else {
            currentPhoneList.add(phoneNumber);
        }
    }

    public List<String> get(String surname) {
        return phoneBook.get(surname);
    }


}
