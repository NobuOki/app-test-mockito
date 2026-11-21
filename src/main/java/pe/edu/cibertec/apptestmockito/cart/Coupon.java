package pe.edu.cibertec.apptestmockito.cart;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Coupon(String code,
                     BigDecimal discountPercentage,
                     LocalDate expirationDate) {}
