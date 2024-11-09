package store;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class UnitTest {

    @Test
    void 파일의_내용을_읽어서_객체에_저장() {
        List<Product> product = fileReader.parseFile();

        Assertions.assertThat(product.size()).isEqualTo(16);
    }

}
