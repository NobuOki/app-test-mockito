package pe.edu.cibertec.apptestmockito.payment;

import java.math.BigDecimal;

public record PaymentRequest(
        Long accountId,
        BigDecimal amount) {}
