package wordDict;


import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        String[] wordArray = new String[]{"белеет",
                "парус",
                "одинокий",
                "в",
                "тумане",
                "моря",
                "голубом",
                "что",
                "ищет",
                "он",
                "в",
                "стране",
                "далекой",
                "что",
                "кинул",
                "он",
                "в",
                "краю",
                "родном"};

        Map<String, Integer> wordCount = new HashMap<>();
        for (int index = 0; index < wordArray.length; index++) {
            wordCount.put(wordArray[index], wordCount.getOrDefault(wordArray[index], 0) + 1);
        }
//        Вывести список уникальных слов, из которых состоит массив
        System.out.println(wordCount.keySet());

//        Количество уже подсчитано при создании HashMap wordCount
//        Ключ - уникальное слово, значение - количество слов в исходном массиве
        System.out.println(wordCount);
    }

}
