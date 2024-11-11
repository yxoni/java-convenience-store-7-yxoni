package store;

import camp.nextstep.edu.missionutils.DateTimes;

import java.util.Optional;

public class Product {
    private final String name;
    private final long price;
    private long quantity;
    private final Promotion promotion;

    public Product(String name, long price, long quantity, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public long getQuantity() {
        return quantity;
    }

    public Amount buy(long amount) {
        if (promotion != null) {
            return promotionBuy(amount);
        }
        isExceed(amount);
        quantity -= amount;
        return new Amount(name, price, amount, 0, 0, amount);
    }

    public Amount promotionBuy(long amount) {
        if (promotion.isPossible(DateTimes.now())) {
            Amount purchaseAmount = promotion.apply(name, price, quantity, amount);
            quantity -= purchaseAmount.getBuy();
            return purchaseAmount;
        }
        return new Amount(name, price, 0, 0, amount, 0);
    }

    public boolean isCorrect(String name) {
        return name.equals(this.name);
    }

    public void isExceed(long amount) {
        if (amount > quantity) {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }
    }

    public boolean isExist(String otherName) {
        return this.name.equals(otherName);
    }

    @Override
    public String toString() {
        String promotionName = Optional.ofNullable(promotion)
                .map(Promotion::toString)
                .orElse("");
        if (quantity == 0) {
            return String.format("- %s %,d원 재고 없음 %s", name, price, promotionName).trim();
        }
        return String.format("- %s %,d원 %d개 %s", name, price, quantity, promotionName).trim();
    }
}
