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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class UnitTest {
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
    void 파일의_내용을_읽어서_객체에_저장() {
        FileReader fileReader = new FileReader();
        List<Product> products = fileReader.createProduct();

        Assertions.assertThat(products.size()).isEqualTo(18);
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

    @ParameterizedTest
    @CsvSource(value = {"1,9,10", "4,6,10"})
    void 프로모션_수량_부족으로_일부_수량_정가로_결제(int amount, int promotionProductQuantity, int generalProductQuantity) {
        String yes = "Y\n";
        System.setIn(new ByteArrayInputStream(yes.getBytes()));

        ProductManager productManager = new ProductManager();
        productManager.purchase("콜라", amount);
        productManager.print();

        String promotionProductExpected = String.format("- 콜라 1,000원 %d개 탄산2+1", promotionProductQuantity);
        String generalProductExpected = String.format("- 콜라 1,000원 %d개", generalProductQuantity);

        assertTrue(outputStream.toString().contains(promotionProductExpected));
        assertTrue(outputStream.toString().contains(generalProductExpected));
    }

    @ParameterizedTest
    @CsvSource(value = {"10,0,10", "11,0,9", "12,0,8"})
    void 프로모션_수량_부족으로_일부_수량_정가로_결제_재고_없음으로_표시(int amount, int promotionProductQuantity, int generalProductQuantity) {
        String yes = "Y\n";
        System.setIn(new ByteArrayInputStream(yes.getBytes()));

        ProductManager productManager = new ProductManager();
        productManager.purchase("콜라", amount);
        productManager.print();

        String promotionProductExpected = String.format("- 콜라 1,000원 재고 없음 탄산2+1");
        String generalProductExpected = String.format("- 콜라 1,000원 %d개", generalProductQuantity);

        assertTrue(outputStream.toString().contains(promotionProductExpected));
        assertTrue(outputStream.toString().contains(generalProductExpected));
    }

    @ParameterizedTest
    @CsvSource(value = {"3,1", "4,1", "10,3", "12,3"})
    void 영수증으로_고객의_구매와_증정_내역_보기(int amount, int promotionAmount) {
        String yes = "Y\n";
        String input = yes + yes;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ConvenienceStore convenienceStore = new ConvenienceStore();
        convenienceStore.init();
        Map<String, Integer> purchaseData = Map.of(
                "콜라", amount
        );
        convenienceStore.createReceipt(purchaseData);
        convenienceStore.showReceipt();

        String expectedProduct = String.format("콜라%d%,d", amount, amount * 1000);
        String expectedPromotion = String.format("콜라%d", promotionAmount);

        assertTrue(outputStream.toString().replaceAll("\\s", "").contains(expectedProduct));
        assertTrue(outputStream.toString().replaceAll("\\s", "").contains(expectedPromotion));
    }

    @ParameterizedTest
    @CsvSource(value = {"3,1", "4,1", "10,3", "12,3"})
    void 영수증으로_금액_정보_보기(int amount, int promotionAmount) {
        String yes = "Y\n";
        String input = yes + yes;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ConvenienceStore convenienceStore = new ConvenienceStore();
        convenienceStore.init();
        Map<String, Integer> purchaseData = Map.of(
                "콜라", amount
        );
        convenienceStore.createReceipt(purchaseData);
        convenienceStore.showReceipt();

        int total = amount * 1000;
        int promotion = promotionAmount * 1000;
        int membership = (int) Math.floor((amount - promotionAmount) * 1000 * 0.3);
        int payment = total - promotion - membership;

        String expectedReceipt = String.format("총구매액%d%,d", amount, total);
        String expectedPromotion = String.format("행사할인%,d", -promotion);
        String expectedMembership = String.format("멤버십할인%,d", -membership);
        String expectedPayment = String.format("내실돈%,d", payment);

        assertTrue(outputStream.toString().replaceAll("\\s", "").contains(expectedReceipt));
        assertTrue(outputStream.toString().replaceAll("\\s", "").contains(expectedPromotion));
        assertTrue(outputStream.toString().replaceAll("\\s", "").contains(expectedMembership));
        assertTrue(outputStream.toString().replaceAll("\\s", "").contains(expectedPayment));
    }
}
