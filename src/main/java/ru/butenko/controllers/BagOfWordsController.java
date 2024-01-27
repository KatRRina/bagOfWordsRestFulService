package ru.butenko.controllers;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.butenko.dto.ComparisionTexts;
import ru.butenko.dto.FailedResponseDto;
import ru.butenko.dto.ResponseDataDto;
import ru.butenko.exceptions.IncorrectMeaningException;
import ru.butenko.exceptions.InvalidSizeException;
import ru.butenko.services.ProcessingService;

@RestController
@RequiredArgsConstructor
public class BagOfWordsController {
    private final ProcessingService processingService;
    @SneakyThrows
    @PostMapping("/bagOfWords")
    public ResponseDataDto handleBagOfWords(
            @RequestBody ComparisionTexts comparisionTexts) {
        return processingService.processText(comparisionTexts);
    }
    @ExceptionHandler
    public ResponseEntity<FailedResponseDto> handleInvalidSizeException(InvalidSizeException ex){
        return new ResponseEntity<>(new FailedResponseDto(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<IncorrectMeaningException> handleIncorrectMeaningException(InvalidSizeException ex){
        return new ResponseEntity<>(new IncorrectMeaningException(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
//    @ExceptionHandler
//    public ResponseEntity<FailedResponseDto> handleLemmatizationException(LemmatizationException ex){
//        return new ResponseEntity<>(new FailedResponseDto(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}

