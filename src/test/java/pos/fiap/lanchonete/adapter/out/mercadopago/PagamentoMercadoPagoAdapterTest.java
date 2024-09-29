package pos.fiap.lanchonete.adapter.out.mercadopago;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import pos.fiap.lanchonete.adapter.out.exception.RecursoNaoEncontradoException;
import pos.fiap.lanchonete.adapter.out.mercadopago.dto.merchantorder.MerchantOrderResponseDto;
import pos.fiap.lanchonete.adapter.out.mercadopago.dto.pagamentoqrcode.PagamentoMPRequestDto;
import pos.fiap.lanchonete.adapter.out.mercadopago.dto.pagamentoqrcode.PagamentoMPResponseDto;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;
import static pos.fiap.lanchonete.objectmother.dtos.out.MerchantOrderResponseDtoObjectMother.getMerchantOrderResponseDtoMock;
import static pos.fiap.lanchonete.objectmother.dtos.out.PagamentoMPResponseDtoObjectMother.getPagamentoMPResponseDtoMock;
import static pos.fiap.lanchonete.objectmother.model.DadosPagamentoObjectMother.getDadosPagamentoMock;

@ExtendWith(MockitoExtension.class)
class PagamentoMercadoPagoAdapterTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private MercadoPagoAdapter pagamentoMercadoPagoAdapter;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(pagamentoMercadoPagoAdapter, "urlPayment", "urlPayment");
        ReflectionTestUtils.setField(pagamentoMercadoPagoAdapter, "urlQrCode", "urlQrCode");
        ReflectionTestUtils.setField(pagamentoMercadoPagoAdapter, "urlMerchantOrders", "{merchant_order_id}");
        ReflectionTestUtils.setField(pagamentoMercadoPagoAdapter, "accessToken", "accessToken");
        ReflectionTestUtils.setField(pagamentoMercadoPagoAdapter, "notificationUrl", "notificationUrl");
    }

    @Test
    void testGerarPagamentoQRCode_Success() {
        var httpEntity = new HttpEntity<>(PagamentoMPRequestDto.builder().build());

        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(httpEntity.getClass()), eq(PagamentoMPResponseDto.class)))
                .thenReturn(new ResponseEntity<>(getPagamentoMPResponseDtoMock(), HttpStatus.OK));

        var pagamento = pagamentoMercadoPagoAdapter.gerarPagamentoQRCode(getDadosPagamentoMock());

        assertNotNull(pagamento);
    }

    @Test
    void testGerarPagamentoQRCode_Error() {
        var httpEntity = new HttpEntity<>(PagamentoMPRequestDto.builder().build());

        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(httpEntity.getClass()), eq(PagamentoMPResponseDto.class)))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

        assertThrows(RecursoNaoEncontradoException.class, () -> {
            pagamentoMercadoPagoAdapter.gerarPagamentoQRCode(getDadosPagamentoMock());
        });
    }

    @Test
    void testObterNumeroPedido_Success() {
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(MerchantOrderResponseDto.class)))
                .thenReturn(new ResponseEntity<>(getMerchantOrderResponseDtoMock(), HttpStatus.OK));

        var pagamento = pagamentoMercadoPagoAdapter.obterNumeroPedido("23");

        assertNotNull(pagamento);
    }

    @Test
    void testObterNumeroPedido_Error() {
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(MerchantOrderResponseDto.class)))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

        assertThrows(RecursoNaoEncontradoException.class, () -> pagamentoMercadoPagoAdapter.obterNumeroPedido("23"));
    }

    @Test
    void testObterNumeroPedido_SemExternalReferenceError() {
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(MerchantOrderResponseDto.class)))
                .thenReturn(new ResponseEntity<>(MerchantOrderResponseDto.builder().build(), HttpStatus.NOT_FOUND));

        assertThrows(RecursoNaoEncontradoException.class, () -> pagamentoMercadoPagoAdapter.obterNumeroPedido("23"));
    }

}
