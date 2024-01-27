package ru.butenko.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.butenko.dto.ComparisionTexts;
import ru.butenko.dto.ResponseDataDto;
import ru.butenko.algorithms.BagOfWordsAlgorithms;
import ru.butenko.exceptions.InvalidSizeException;
import ru.butenko.exceptions.LemmatizationException;
import ru.stachek66.nlp.mystem.holding.MyStemApplicationException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProcessingService {
    private final static int MAX_SIZE = 100;
    public ResponseDataDto processText(ComparisionTexts comparisionTexts){
        String textFirst = comparisionTexts.getTextFirst();
        String textSecond = comparisionTexts.getTextSecond();
        if(textFirst.length() > MAX_SIZE)
            throw new InvalidSizeException("The first text exceeds the allowed length value");
        if(textSecond.length() > MAX_SIZE)
            throw new InvalidSizeException("The second text exceeds the allowed length value");
        LocalDateTime time = LocalDateTime.now();
        Integer result = null;
        try {
            result = BagOfWordsAlgorithms.compareTexts(textFirst, textSecond);
        } catch (MyStemApplicationException e) {
            throw new LemmatizationException("Error when lemmatizing text using mystem");
        }

        return new ResponseDataDto(result);

    }
}
