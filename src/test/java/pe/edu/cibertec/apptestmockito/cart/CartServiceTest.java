package pe.edu.cibertec.apptestmockito.cart;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CouponRepository  couponRepository;
    @InjectMocks
    private CartServiceImpl cartService;

    @Test
    @DisplayName("Debe lanzar excepcion cuando el cupon NO EXISTE")
    void test1(){
        String cupon = "PROM2026";
        //Aplicando Mockito para que cuando invoque al repository
        //me devuelva el valor que yo le indique
        when(couponRepository.findByCode(cupon)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> cartService.applyCoupon(
                new BigDecimal("100"), cupon))
                .isInstanceOf(CouponNotFoundException.class)
                .hasMessageContaining("no existe");
    }
    @Test
    @DisplayName("Debe lanzar excepcion cuando el cupón está EXPIRADO")
    void test2(){
        Coupon cuponExpirado = new Coupon(
                "PROM2026",
                new BigDecimal("20"),
                LocalDate.now().minusDays(1));
        when(couponRepository.findByCode("PROM2026"))
                .thenReturn(Optional.of(cuponExpirado));

        assertThatThrownBy(() -> cartService.applyCoupon(
                new BigDecimal("100"), "PROM2026"))
                .isInstanceOf(CouponExpiredException.class)
                .hasMessageContaining("expirado");
    }

    @Test
    @DisplayName("Debe retornar el total a pagar aplicando el descuento")
    void test3(){
        String cupon = "PROM2026";
        Coupon cuponCorrecto = new Coupon(
                cupon,
                new BigDecimal("20"),
                LocalDate.now().plusDays(5));
        when(couponRepository.findByCode(cupon))
                .thenReturn(Optional.of(cuponCorrecto));
        BigDecimal total = cartService.applyCoupon(
                new  BigDecimal("200"), cupon);
        assertThat(total).isEqualByComparingTo(
                new BigDecimal("160"));
    }


}
