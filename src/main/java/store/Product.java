package store;

import java.util.Optional;

public class Product {
    private final String name;
    private final int price;
    private int quantity;
    private final Promotion promotion;

    public Product(String name, int price, int quantity, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public void pay(int amount) {
        if (amount > quantity) {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }
        quantity -= amount;
    }

    @Override
    public String toString() {
        String promotionName = Optional.ofNullable(promotion)
                .map(Promotion::toString)
                .orElse("");
        if (quantity == 0) {
            return String.format("- %s %,d원 재고 없음 %s", name, price, promotionName);
        }
        return String.format("- %s %,d원 %d개 %s", name, price, quantity, promotionName);
    }
}
