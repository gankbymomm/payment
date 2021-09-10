package vn.vnpay.payment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import vn.vnpay.payment.dto.ResponsePaymentDTO;
import vn.vnpay.payment.utils.CustomException;

import java.util.HashMap;
import java.util.Map;

@Component
public class BaseController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected Map<String, String> handleValidate(MethodArgumentNotValidException exception){
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) ->{
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    protected ResponseEntity<?> response(ResponsePaymentDTO responsePaymentDTO) {
        return new ResponseEntity<>(responsePaymentDTO, HttpStatus.OK);
    }

    protected ResponsePaymentDTO error(Exception exception) {
        ResponsePaymentDTO responsePaymentDTO = new ResponsePaymentDTO();
        if (exception instanceof CustomException) {
            CustomException customException = (CustomException) exception;
            responsePaymentDTO.setCode(customException.getCode());
            responsePaymentDTO.setMessage(exception.getMessage());
        } else {
            responsePaymentDTO.setCode("500");
            responsePaymentDTO.setMessage("Internal Server Error " + exception.getMessage());
        }
        return responsePaymentDTO;
    }
}
