package pe.edu.cibertec.apptestmockito.payment;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
