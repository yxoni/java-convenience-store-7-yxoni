package store.io;

import store.object.Receipt;
import store.product.Product;

import java.util.List;

public class OutputView {
    public void greeting() {
        System.out.println("안녕하세요. W편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.\n");
    }

    public void printProducts(List<Product> products) {
        products.forEach(System.out::println);
    }

    public void purchaseGuide() {
        System.out.println("\n구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
    }

    public void promotionAdditionalGuide(String productName) {
        System.out.printf("\n현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)\n", productName);
    }

    public void promotionImpossibleGuide(String productName, int amount) {
        System.out.printf("\n현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)\n", productName, amount);
    }

    public void membershipGuide() {
        System.out.println("\n멤버십 할인을 받으시겠습니다? (Y/N)");
    }

    public void printReceipt(Receipt receipt) {
        System.out.println("\n==============W 편의점================");
        String productReportFormat = "%-19s%-10s%-6s";
        System.out.printf((productReportFormat) + "%n", "상품명", "수량", "금액");
        System.out.printf(receipt.productReport());
        System.out.println("==============증    정================");
        System.out.printf(receipt.promotionReport());
        System.out.println("======================================");
        System.out.printf(receipt.paymentReport());
    }

    public void closingGuide() {
        System.out.println("\n감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
    }

    public void printError(String errorMessage) {
        System.out.println("\n" + errorMessage);
    }
}
