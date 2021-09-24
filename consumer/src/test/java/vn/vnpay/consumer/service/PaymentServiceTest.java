package vn.vnpay.consumer.service;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vn.vnpay.consumer.constant.RabbitConstant;
import vn.vnpay.consumer.constant.SystemConstant;
import vn.vnpay.consumer.converter.PaymentConverter;
import vn.vnpay.consumer.dto.PaymentDTO;
import vn.vnpay.consumer.entity.PaymentEntity;
import vn.vnpay.consumer.repository.PaymentRepository;
import vn.vnpay.consumer.utils.CustomException;
import vn.vnpay.consumer.utils.MessageTestUtils;
import vn.vnpay.consumer.utils.PostRequestUtils;

@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
public class PaymentServiceTest {

    @Captor
    private ArgumentCaptor<Message> amqpMessage;

    @Mock
    private PaymentConverter paymentConverter;

    @Autowired
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentService paymentService;

    private RabbitTemplate rabbitTemplate;

    @Mock
    private RabbitMessagingTemplate messagingTemplate;

    private static final PaymentDTO paymentDTO = new PaymentDTO();

    @BeforeAll
    public static void setUp() {
        paymentDTO.setTokenKey("1601353776839FT19310RH6P1");
        paymentDTO.setApiID("restPayment");
        paymentDTO.setPayDate("20200929112923");
        paymentDTO.setAccountNo("0001100014211002");
        paymentDTO.setDebitAmount(11200);
        paymentDTO.setAddtionalData("");
        paymentDTO.setMobile("0145225630");
        paymentDTO.setMessageType("1");
        paymentDTO.setOrderCode("FT19310RH6P1");
        paymentDTO.setUserName("cntest001");
        paymentDTO.setRealAmount("11200");
        paymentDTO.setRespCode("00");
        paymentDTO.setTraceTransfer("FT19310RH6P1");
        paymentDTO.setRespDesc("SUCCESS");
        paymentDTO.setBankCode("970445");
        paymentDTO.setCheckSum("40e670720b754324af3d3a0ff49b52fb");
        paymentDTO.setPromotionCode("");
        paymentDTO.setAddValue("{\"payMethod\":\"01\",\"payMethodMMS\":1}");
    }

    /*String input = "{\"tokenKey\":\"1601353776839FT19310RH6P3911\",\"apiID\":\"restPayment\"," +
            "\"mobile\":\"0145225630\",\"bankCode\":\"970445\",\"accountNo\":\"0001100014211002\"," +
            "\"payDate\":\"20200929112923\",\"addtionalData\":\"\",\"debitAmount\":11200,\"respCode\":\"00\"," +
            "\"respDesc\":\"SUCCESS\",\"traceTransfer\":\"FT19310RH6P1\",\"messageType\":\"1\"," +
            "\"checkSum\":\"40e670720b754324af3d3a0ff49b52fb\", \"orderCode\":\"FT19310RH6P1\"," +
            "\"userName\":\"cntest001\",\"realAmount\":\"11200\",\"promotionCode\":\"\"," +
            "\"addValue\":\\\"{\\\\\"payMethod\\\\\":\\\\\"01\\\\\",\\\\\"payMethodMMS\\\\\":1}\"}";*/

    /*@Test
    public void sendToQueueTest(){
        messagingTemplate.convertAndSend(RabbitConstant.TOPIC_EXCHANGE, RabbitConstant.ROUTING_KEY2, "message");
        verify(rabbitTemplate).send(eq(RabbitConstant.TOPIC_EXCHANGE), eq(RabbitConstant.ROUTING_KEY2), amqpMessage.capture());

        assertEquals("message", MessageTestUtils.extractText(amqpMessage.getValue()));
    }*/

   /* @Test
    public void saveToDataBaseTest(){
        PaymentEntity paymentEntity = paymentRepository.save(paymentConverter.toEntity(paymentDTO));
        Mockito.when(paymentRepository.save(Mockito.any(PaymentEntity.class))).thenReturn(paymentEntity);
        try {
            paymentService.receiveFromQueue(paymentDTO);
            Mockito.verify(paymentRepository, Mockito.times(1)).save(Mockito.any(PaymentEntity.class));
        } catch (Exception ignored) {
        }
    }*/

    /*@Test
    public void sendToURLTest() throws CustomException {
        ResponseEntity<Object> responseEntity = PostRequestUtils.postRequest(paymentDTO, SystemConstant.URL_VNPAY);
        if (responseEntity != null) {
            Assertions.assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCode().value());
        }
    }*/
}
