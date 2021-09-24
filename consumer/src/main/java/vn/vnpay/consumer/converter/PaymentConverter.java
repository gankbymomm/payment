package vn.vnpay.consumer.converter;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import vn.vnpay.consumer.dto.PaymentDTO;
import vn.vnpay.consumer.entity.PaymentEntity;

@Component
public class PaymentConverter {

    public PaymentEntity toEntity(PaymentDTO paymentDTO){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(paymentDTO, PaymentEntity.class);
    }

}
