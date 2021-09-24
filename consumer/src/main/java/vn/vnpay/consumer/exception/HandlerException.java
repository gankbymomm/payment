package vn.vnpay.consumer.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class HandlerException {

    @JsonIgnore
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        return new ResponseEntity<>(processFieldErrors(fieldErrors), HttpStatus.BAD_REQUEST);
    }

    private Error processFieldErrors(List<org.springframework.validation.FieldError> fieldErrors) {
        Error error = new Error(400, "validation error");
        List<Result> listErr = new ArrayList<>();
        for (org.springframework.validation.FieldError fieldError: fieldErrors) {
            Result result = new Result(fieldError.getField(),fieldError.getDefaultMessage());
            result.setMessage(fieldError.getDefaultMessage());
            listErr.add(result);
        }
        error.setData(listErr);
        return error;
    }

    static class Error {
        private final int code;
        private final String message;

        private  List<Result> data = new ArrayList<>();

        Error(int code, String message) {
            this.code = code;
            this.message =message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        public List<Result> getData() {
            return data;
        }

        public void setData(List<Result> data) {
            this.data = data;
        }
    }
}
