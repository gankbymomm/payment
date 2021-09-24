package vn.vnpay.consumer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import vn.vnpay.consumer.dto.ResponseFromPartnerDTO;

@Component
public class ConsumerController {
    private ResponseEntity<?> response(ResponseFromPartnerDTO responseFromPartnerDTO) {
        return new ResponseEntity(responseFromPartnerDTO, HttpStatus.OK);
    }
}
