package vn.vnpay.consumer.entity;


import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Table;


@Data
@Entity
@Table(name = "consumer")
public class PaymentEntity extends BaseEntity {

    private String tokenKey;
    private String apiID;
    private String mobile;
    private String bankCode;
    private String accountNo;
    private String payDate;
    private String addtionalData;
    private double debitAmount;
    private String respCode;
    private String respDesc;
    private String traceTransfer;
    private String messageType;
    private String checkSum;
    private String orderCode;
    private String userName;
    private String realAmount;
    private String promotionCode;
    private String addValue;
}
