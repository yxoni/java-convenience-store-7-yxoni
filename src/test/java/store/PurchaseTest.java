package store;

import camp.nextstep.edu.missionutils.Console;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import store.product.ProductManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PurchaseTest {
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
    @CsvSource(value = {"10,10", "11,9", "12,8"})
    void 프로모션_수량_부족으로_일부_수량_정가로_결제_재고_없음으로_표시(int amount, int generalProductQuantity) {
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
}
