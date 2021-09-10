package vn.vnpay.payment.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "payment")
public class PaymentEntity extends BaseEntity {

    private String tokenKey;
    @Column
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
    @Column
    private String userName;
    @Column
    private String realAmount;
    @Column
    private String promotionCode;
    private String addValue;
}
