package vn.vnpay.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpay.payment.common.Common;
import vn.vnpay.payment.constant.SystemConstant;
import vn.vnpay.payment.dto.PaymentDTO;
import vn.vnpay.payment.service.PaymentService;
import vn.vnpay.payment.utils.CustomException;

import javax.validation.Valid;

@RestController
@RequestMapping("/payment")
public class PaymentController extends BaseController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/request")
    public ResponseEntity<?> requestData(@Valid @RequestBody PaymentDTO paymentDTO) {
        try {
            paymentService.sendDataToRabbit(paymentDTO);
            return response(Common.responsePartner(SystemConstant.SUCCESS_CODE, SystemConstant.SUCCESS_MESSAGE, ""));
        } catch (Exception exception){
            return response(error(exception));
        }
    }

}
