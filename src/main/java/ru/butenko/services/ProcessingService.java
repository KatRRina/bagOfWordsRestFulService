package ru.butenko.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.butenko.dto.ComparisionTextsDto;
import ru.butenko.dto.ResponseDataDto;
import ru.butenko.algorithms.BagOfWordsAlgorithms;
import ru.butenko.exceptions.IncorrectMeaningException;
import ru.butenko.exceptions.InvalidSizeException;
import ru.butenko.exceptions.LemmatizationException;
import ru.butenko.model.BagOfWordsInformation;
import ru.butenko.repositories.InformationRepository;
import ru.stachek66.nlp.mystem.holding.MyStemApplicationException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProcessingService {

    @Value("${max_size}")
    private final int MAX_SIZE = 100;

    private final InformationRepository informationRepository;

    public ResponseDataDto processText(ComparisionTextsDto comparisionTextsDto) {
        String textFirst = comparisionTextsDto.getTextFirst();
        String textSecond = comparisionTextsDto.getTextSecond();
        if (textFirst.length() > MAX_SIZE)
            throw new InvalidSizeException("The first text exceeds the allowed length value");
        if (textSecond.length() > MAX_SIZE)
            throw new InvalidSizeException("The second text exceeds the allowed length value");
        if (!textFirst.matches("^[а-яА-Я\\p{Punct}\\d\\s]+") || !textSecond.matches("^[а-яА-Я\\p{Punct}\\d\\s]+")) {
            throw new IncorrectMeaningException("he wrong meaning, the text must consist of Russian letters, punctuation marks and numbers.");
        }

        LocalDateTime time = LocalDateTime.now();
        Integer result;
        try {
            result = BagOfWordsAlgorithms.compareTexts(textFirst, textSecond);
            BagOfWordsInformation information = BagOfWordsInformation.builder()
                    .user_login("lalalala")
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
