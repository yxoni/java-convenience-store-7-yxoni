package store;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

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

        Assertions.assertThat(product.getName()).isEqualTo("콜라");
        Assertions.assertThat(product.getPrice()).isEqualTo(1000);
        Assertions.assertThat(product.getQuantity()).isEqualTo(10);
        Assertions.assertThat(product.getPromotion()).isEqualTo("탄산2+1");
    }

}
