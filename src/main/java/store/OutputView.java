package store;

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
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (얘: [사이다-2],[감자칩-1]");
    }

    public void promotionImpossibleGuide() {
        // TODO: [제품명] n개 출력
        System.out.println("현재 [제품명] n개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)");
    }

    public void promotionAdditionalGuide() {
        // TODO: [제품명] n개 출력
        System.out.println("현재 [제품명]은(는) n개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)");
    }

    public void membershipGuide() {
        System.out.println("멤버십 할인을 받으시겠습니다? (Y/N)");
    }

    public void printReceipt() {
        // TODO: 영수증 출력 상세 내역
        String receiptLine = "=".repeat(14);

        System.out.println(receiptLine + "W 편의점" + receiptLine);

        System.out.println(receiptLine + "증\t정" + receiptLine);

        System.out.println("=".repeat(36));
    }

    public void closingGuide() {
        System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
    }
}