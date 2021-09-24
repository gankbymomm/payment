package vn.vnpay.payment.controller;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpay.payment.common.FormatToken;
import vn.vnpay.payment.constant.SystemConstant;
import vn.vnpay.payment.dto.PaymentDTO;
import vn.vnpay.payment.service.PaymentService;

import javax.validation.Valid;

@RestController
@RequestMapping("/payment")
public class PaymentController extends BaseController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/request")
    public ResponseEntity<?> requestData(@Valid @RequestBody PaymentDTO paymentDTO) {
        String token = FormatToken.store(paymentDTO.getTokenKey());
        paymentDTO.setTokenKey(token);
        ThreadContext.put(SystemConstant.TOKEN,token);
        try {
            return response(paymentService.sendDataToRabbit(paymentDTO));
        } catch (Exception exception){
            ThreadContext.clearAll();
            return response(error(exception));
        }
    }

}
