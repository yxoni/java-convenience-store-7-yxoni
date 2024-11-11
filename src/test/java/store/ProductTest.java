package store;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import store.file.FileReader;
import store.product.Product;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductTest {
    @Test
    void 각_상품의_재고_수량을_고려하여_결제_가능_여부_확인() {
        FileReader fileReader = new FileReader();
        List<Product> products = fileReader.createProduct();

        Product product = products.get(1);

        product.buy(1);
        Assertions.assertThat(product.toString()).isEqualTo("- 콜라 1,000원 9개");
    }

    @Test
    void 상품의_재고보다_많으면_결제_불가() {
        FileReader fileReader = new FileReader();
        List<Product> products = fileReader.createProduct();

        Product product = products.get(1);

        assertThrows(IllegalArgumentException.class, () -> product.buy(11));
    }
}
