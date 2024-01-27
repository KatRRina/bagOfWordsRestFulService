package ru.butenko.controllers;

import com.typesafe.config.ConfigException;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.butenko.DTO.ComparisionTexts;
import ru.butenko.algorithms.BagOfWordsAlgorithms;
import ru.butenko.model.BagOfWordsInformation;

import java.time.LocalDateTime;

@RestController
public class BagOfWordsController {
    @SneakyThrows
    @PostMapping("/bagOfWords")
    public BagOfWordsInformation handleBagOfWords(
            @RequestBody ComparisionTexts comparisionTexts){
        String textFirst = comparisionTexts.getTextFirst();
        String textSecong = comparisionTexts.getTextSecond();
        LocalDateTime time = LocalDateTime.now();
        Integer result = BagOfWordsAlgorithms.compareTexts(textFirst, textSecong);
        return new BagOfWordsInformation(
                1,
                "lalala",
                comparisionTexts.getTextFirst(),
                comparisionTexts.getTextSecond(),
                result,
                time);
    }
}
