package com.swp391.koibe.exceptions;

import com.swp391.koibe.components.LocalizationUtils;
import com.swp391.koibe.exceptions.base.DataAlreadyExistException;
import com.swp391.koibe.exceptions.base.DataNotFoundException;
import com.swp391.koibe.responses.ExceptionResponse;
import com.swp391.koibe.responses.base.BaseResponse;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ApplicationExceptionHandler {

    private final LocalizationUtils localizationUtils;

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<Object> handleDataNotFoundException(DataNotFoundException e) {
        return ExceptionResponse.builder()
            .message(localizationUtils.getLocalizedMessage("exception.data_not_found"))
            .reason(e.getMessage())
            .build();
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<Object> handleNullPointerException(NullPointerException e) {
        return ExceptionResponse.builder()
            .message(localizationUtils.getLocalizedMessage("exception.null_pointer"))
            .reason(e.getMessage())
            .build();
    }

    @ExceptionHandler(BiddingRuleException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<Object> handleBiddingRuleException(BiddingRuleException e) {
        return ExceptionResponse.builder()
                .message(localizationUtils.getLocalizedMessage("bid.exception.bidding_rule_error"))
                .reason(e.getMessage())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse<Object> handleException(Exception e) {
        return ExceptionResponse.builder()
            .message(localizationUtils.getLocalizedMessage("exception.internal_server_error"))
            .reason(e.getMessage())
            .build();
    }

    @ExceptionHandler(GenerateDataException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse<Object> handleGenerateDataException(GenerateDataException e) {
        return ExceptionResponse.builder()
            .message(localizationUtils.getLocalizedMessage("exception.generate_data_error"))
            .reason(e.getMessage())
            .build();
    }

    //DataAlreadyExistsException
    @ExceptionHandler(DataAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<Object> handleDataAlreadyExistsException(DataAlreadyExistException e) {
        return ExceptionResponse.builder()
            .message(localizationUtils.getLocalizedMessage("exception.data_already_exist"))
            .reason(e.getMessage())
            .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
        MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}
