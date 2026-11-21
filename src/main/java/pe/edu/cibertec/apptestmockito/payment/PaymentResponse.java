package pe.edu.cibertec.apptestmockito.payment;

public record PaymentResponse(
        String transactionId,
        boolean success) {}
