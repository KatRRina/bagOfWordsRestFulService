package ru.butenko.algorithms;

import ru.stachek66.nlp.mystem.holding.Factory;
import ru.stachek66.nlp.mystem.holding.MyStem;
import ru.stachek66.nlp.mystem.holding.MyStemApplicationException;
import ru.stachek66.nlp.mystem.holding.Request;
import ru.stachek66.nlp.mystem.model.Info;
import scala.Option;
import scala.collection.JavaConversions;

import java.io.File;
import java.util.*;

public class BagOfWordsAlgorithms {
    private final static List<String> list = List.of("у", "а", "и", "под", "над", "c", ".", ",", ";", ":", "!", "?");
    private final static List<String> STOP_WORDS = new ArrayList<>(list);
    private final static MyStem mystemAnalyzer =
            new Factory("-igd --eng-gr --format json --weight")
                    .newMyStem("3.0", Option.apply(new File("C:\\Users\\ebyte\\IdeaProjects\\bagOfWordsRestFulService\\mystem.exe"))).get();

    private static String deleteStopWorsd(String text) {
        text = " " + text + " ";
        for (String value :
                STOP_WORDS) {
            if (value == ".")
                value = "\\.";
            text = text.replaceAll("\\b" + value + "\\b", "");
        }
        return text;
    }
    private static String getLemmatizationOfText(String text) throws MyStemApplicationException {
        final Iterable<Info> result =
                JavaConversions.asJavaIterable(
                        mystemAnalyzer
                                .analyze(Request.apply(text))
                                .info()
                                .toIterable());


        StringBuilder lemmatizedText = new StringBuilder();

        for (final Info info : result) {
            lemmatizedText.append(info.lex().get() + " ");
        }
        return lemmatizedText.toString().strip();
    }

    private static List<String> getListWithUniqueWordsInText(String text){
        String[] wordsInText = text.split(" ");
        Set<String> uniqueWordsInText = new HashSet<>(List.of(wordsInText));
        return new ArrayList<>(uniqueWordsInText);
    }
    public static Integer compareTexts(String textFirst, String textSecond) throws MyStemApplicationException{

        int totalCountWords = 0;
        textFirst = deleteStopWorsd(textFirst);
        textFirst = getLemmatizationOfText(textFirst);

        textSecond = deleteStopWorsd(textSecond);
        textSecond = getLemmatizationOfText(textSecond);

        List<String> listWithUniqueWordInFirstText = getListWithUniqueWordsInText(textFirst);
        List<String> listWithUniqueWordInSecondText = getListWithUniqueWordsInText(textSecond);

        for (String word: listWithUniqueWordInSecondText)
            if(listWithUniqueWordInFirstText.contains(word))
                totalCountWords += 1;
        double result = (float) totalCountWords / listWithUniqueWordInFirstText.size() * 100;

        return (int)Math.round(result);
    }
}
