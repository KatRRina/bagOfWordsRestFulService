package ru.butenko.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.butenko.dto.ComparisonTextsDto;
import ru.butenko.dto.ResponseDataDto;
import ru.butenko.algorithms.BagOfWordsAlgorithms;
import ru.butenko.exceptions.IncorrectMeaningException;
import ru.butenko.exceptions.InvalidSizeException;
import ru.butenko.exceptions.LemmatizationException;
import ru.butenko.model.BagOfWordsInformation;
import ru.butenko.model.StopWord;
import ru.butenko.repositories.InformationRepository;
import ru.butenko.repositories.StopWordRepository;
import ru.stachek66.nlp.mystem.holding.MyStemApplicationException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcessingService {

    @Value("${max_size}")
    private final int MAX_SIZE = 100;

    private final InformationRepository informationRepository;
    private final StopWordRepository stopWordRepository;

    public ResponseDataDto processText(ComparisonTextsDto comparisonTextsDto, Authentication auth) {
        String textFirst = comparisonTextsDto.getTextFirst().strip();
        String textSecond = comparisonTextsDto.getTextSecond().strip();
        if (textFirst.length() > MAX_SIZE || textFirst.isEmpty())
            throw new InvalidSizeException("The first text exceeds the allowed length value");
        if (textSecond.length() > MAX_SIZE || textSecond.isEmpty())
            throw new InvalidSizeException("The second text exceeds the allowed length value");
        if (!textFirst.matches("^[а-яА-Я\\p{Punct}\\d\\s]+") || !textSecond.matches("^[а-яА-Я\\p{Punct}\\d\\s]+")) {
            throw new IncorrectMeaningException("The wrong meaning, the text must consist of Russian letters, punctuation marks and numbers.");
        }

        LocalDateTime time = LocalDateTime.now();
        Integer result;
        List<String> stopWords = stopWordRepository.findAll().stream().map(StopWord::getWord_value).toList();
        try {
            result = BagOfWordsAlgorithms.compareTexts(textFirst, textSecond, stopWords);
            BagOfWordsInformation information = BagOfWordsInformation.builder()
                    .user_login(auth.getName())
                    .text_first(textFirst)
                    .text_second(textSecond)
                    .outer_value(result)
                    .create_(time)
                    .build();
            informationRepository.save(information);

        } catch (MyStemApplicationException e) {
            throw new LemmatizationException("Error when lemmatizing text using mystem");
        }

        return new ResponseDataDto(result);

    }
}
