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
    private final static MyStem mystemAnalyzer =
            new Factory("-igd --eng-gr --format json --weight")
                    .newMyStem("3.0", Option.apply(new File("C:\\Users\\ebyte\\IdeaProjects\\bagOfWordsRestFulService\\mystem.exe"))).get();

    private static String deleteStopWorsd(String text, List<String> stopWords) {
        text = " " + text + " ";
        text = text.replaceAll("\\p{Punct}+", "");
        text = text.replaceAll("\\d+", "");
        for (String value :
                stopWords) {
            text = text.replaceAll("\\s" + value + "\\s", " ");
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
            lemmatizedText.append(info.lex().get()).append(" ");
        }
        return lemmatizedText.toString().strip();
    }

    private static List<String> getListWithUniqueWordsInText(String text){
        String[] wordsInText = text.split(" ");
        Set<String> uniqueWordsInText = new HashSet<>(List.of(wordsInText));
        return new ArrayList<>(uniqueWordsInText);
    }
    public static Integer compareTexts(String textFirst, String textSecond, List<String> stopWords) throws MyStemApplicationException{

        int totalCountWords = 0;
        textFirst = deleteStopWorsd(textFirst, stopWords);
        textFirst = getLemmatizationOfText(textFirst);

        textSecond = deleteStopWorsd(textSecond, stopWords);
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
