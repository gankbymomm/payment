package vn.vnpay.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO implements Serializable {

    private String tokenKey;

    @NotBlank(message = "Api id can not null")
    private String apiID;
    private String mobile;
    private String bankCode = "970445";
    private String accountNo;
    private String payDate;
    private String addtionalData;
    private double debitAmount;
    private String respCode;
    private String respDesc;
    private String traceTransfer;
    private String messageType;
    private String checkSum;

    @NotBlank(message = "Order code can not null")
    private String orderCode;
    private String userName;
    private String realAmount;
    private String promotionCode;
    private String addValue = "{\"payMethod\":\"01\",\"payMethodMMS\":1}";
}
