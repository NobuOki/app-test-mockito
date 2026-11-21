package pe.edu.cibertec.apptestmockito.payment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Test
    @DisplayName("Debe lanzar excepción cuando el saldo es insuficiente")
    void test1(){
        Long accountId = 1L;
        BigDecimal montoAPagar = new BigDecimal("100");
        BigDecimal saldo = new BigDecimal("50");
        PaymentRequest request = new PaymentRequest(accountId,
                montoAPagar);
        //Aplicando Mockito:
        when(accountRepository.getBalance(accountId)).thenReturn(saldo);

        assertThatThrownBy(() -> paymentService.processPayment(request))
                .isInstanceOf(InsufficientFundsException.class)
                .hasMessageContaining("Saldo insuficiente");
        //Que no se utilice la función saveTransaction
        verify(accountRepository, never()).saveTransaction(anyLong(),
                any(), any());
    }


    @Test
    @DisplayName("Validar si la cuenta tiene fondos y genera la transacción")
    void test2(){
        Long accountId = 1L;
        BigDecimal montoAPagar = new BigDecimal("100");
        BigDecimal saldo = new BigDecimal("250");
        PaymentRequest request = new PaymentRequest(accountId,
                montoAPagar);
        //Aplicando Mockito:
        when(accountRepository.getBalance(accountId)).thenReturn(saldo);

        PaymentResponse response = paymentService.processPayment(request);

        assertThat(request).isNotNull();
        assertThat(response.success()).isTrue();
        assertThat(response.transactionId()).isNotBlank();

        verify(accountRepository, times(1))
                .saveTransaction(eq(accountId),
                        eq(montoAPagar),
                        anyString());
    }


}
