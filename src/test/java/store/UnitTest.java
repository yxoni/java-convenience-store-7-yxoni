package store;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UnitTest {

    @Test
    void 파일의_내용을_읽어서_객체에_저장() {
        FileReader fileReader = new FileReader();
        List<Product> products = fileReader.createProduct();

        Assertions.assertThat(products.size()).isEqualTo(16);
    }

    @Test
    void product_객체에_저장된_내용_확인() {
        FileReader fileReader = new FileReader();
        List<Product> products = fileReader.createProduct();

        Product product = products.get(0);

        Assertions.assertThat(product.toString()).isEqualTo("- 콜라 1,000원 10개 탄산2+1");
    }

    @Test
    void 각_상품의_재고_수량을_고려하여_결제_가능_여부_확인() {
        FileReader fileReader = new FileReader();
        List<Product> products = fileReader.createProduct();

        Product product = products.get(0);

        product.pay(1);
        Assertions.assertThat(product.toString()).isEqualTo("- 콜라 1,000원 9개 탄산2+1");
    }

    @Test
    void 상품의_재고보다_많으면_결제_불가() {
        FileReader fileReader = new FileReader();
        List<Product> products = fileReader.createProduct();

        Product product = products.get(0);

        assertThrows(IllegalArgumentException.class, () -> product.pay(11));
    }

}
