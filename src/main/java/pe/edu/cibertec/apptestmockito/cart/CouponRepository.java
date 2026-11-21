package pe.edu.cibertec.apptestmockito.cart;

import java.util.Optional;

interface CouponRepository {
    Optional<Coupon> findByCode(String code);
}

