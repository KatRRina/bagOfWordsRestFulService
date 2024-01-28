package ru.butenko.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.butenko.dto.ComparisonTextsDto;
import ru.butenko.dto.FailedResponseDto;
import ru.butenko.dto.ResponseDataDto;
import ru.butenko.exceptions.IncorrectMeaningException;
import ru.butenko.exceptions.InvalidSizeException;
import ru.butenko.services.ProcessingService;

@RestController
@RequiredArgsConstructor
public class BagOfWordsController {
    private final ProcessingService processingService;

    @PostMapping("/bagOfWords")
    public ResponseDataDto handleBagOfWords(
            @RequestBody ComparisonTextsDto comparisonTextsDto,
            Authentication auth) {
        return processingService.processText(comparisonTextsDto, auth);
    }
    @ExceptionHandler
    public ResponseEntity<FailedResponseDto> handleInvalidSizeException(InvalidSizeException ex){
        return new ResponseEntity<>(new FailedResponseDto(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<FailedResponseDto> handleIncorrectMeaningException(IncorrectMeaningException ex){
        return new ResponseEntity<>(new FailedResponseDto(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
//    @ExceptionHandler
//    public ResponseEntity<FailedResponseDto> handleLemmatizationException(LemmatizationException ex){
//        return new ResponseEntity<>(new FailedResponseDto(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}

