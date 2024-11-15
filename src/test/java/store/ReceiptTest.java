package store;

import camp.nextstep.edu.missionutils.Console;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import store.object.ConvenienceStore;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReceiptTest {
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

    @Test
    void 영수증으로_금액_정보_보기() {
        String yes = "Y\n";
        String input = yes + yes;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ConvenienceStore convenienceStore = new ConvenienceStore();
        convenienceStore.init();
        Map<String, Integer> purchaseData = Map.of(
                "콜라", 3,
                "에너지바", 5
        );
        convenienceStore.createReceipt(purchaseData);
        convenienceStore.showReceipt();

        String expectedReceipt = "총구매액813,000";
        String expectedPromotion = "행사할인-1,000";
        String expectedMembership = "멤버십할인-3,000";
        String expectedPayment = "내실돈9,000";

        assertTrue(outputStream.toString().replaceAll("\\s", "").contains(expectedReceipt));
        assertTrue(outputStream.toString().replaceAll("\\s", "").contains(expectedPromotion));
        assertTrue(outputStream.toString().replaceAll("\\s", "").contains(expectedMembership));
        assertTrue(outputStream.toString().replaceAll("\\s", "").contains(expectedPayment));
    }
}
