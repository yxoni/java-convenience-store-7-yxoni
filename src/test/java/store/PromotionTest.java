package store;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.DateTimes;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import store.file.FileReader;
import store.product.Product;
import store.promotion.Promotion;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PromotionTest {
    private static ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUpStream() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void restoreStream() {
        Console.close();
        System.setOut(System.out);
    }

    @Test
    void 오늘_날짜가_프로모션_기간_내에_포홤되어_있는지_확인() {
        FileReader fileReader = new FileReader();
        List<Promotion> promotions = fileReader.createPromotion();

        Promotion promotionPossible = promotions.get(0);
        Promotion promotionImpossible = promotions.get(3);

        Assertions.assertThat(promotionPossible.isPossible(DateTimes.now())).isEqualTo(true);
        Assertions.assertThat(promotionImpossible.isPossible(DateTimes.now())).isEqualTo(false);
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 6, 9})
    void 프로모션_할인_알맞는_개수(int amount) {
        FileReader fileReader = new FileReader();
        List<Product> products = fileReader.createProduct();

        Product product = products.get(0);

        product.buy(amount);
        assertEquals("", outputStream.toString());
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 5, 8})
    void 프로모션_할인_무료_추가(int amount) {
        String yes = "Y\n";
        System.setIn(new ByteArrayInputStream(yes.getBytes()));

        FileReader fileReader = new FileReader();
        List<Product> products = fileReader.createProduct();

        Product product = products.get(0);

        product.buy(amount);
        assertEquals("현재 콜라은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)".trim(), outputStream.toString().trim());
    }

    @ParameterizedTest
    @CsvSource(value = {"1,1", "4,1", "10,1", "11,2", "12,3"})
    void 프로모션_할인_기준_미달로_인한_그냥_구매(int amount, int exceed) {
        String yes = "Y\n";
        System.setIn(new ByteArrayInputStream(yes.getBytes()));

        FileReader fileReader = new FileReader();
        List<Product> products = fileReader.createProduct();

        Product product = products.get(0);

        product.buy(amount);
        String expected = String.format("현재 콜라 %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)", exceed);
        assertEquals(expected.trim(), outputStream.toString().trim());
    }
}
