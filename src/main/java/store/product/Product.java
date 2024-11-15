package store.product;

import camp.nextstep.edu.missionutils.DateTimes;
import store.object.Amount;
import store.promotion.Promotion;
import store.type.ErrorMessage;

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

    public int getQuantity() {
        return quantity;
    }

    public Amount buy(int amount) {
        if (promotion != null) {
            return promotionBuy(amount);
        }
        isExceed(amount);
        quantity -= amount;
        return new Amount(name, price, amount, 0, 0, amount);
    }

    public Amount promotionBuy(int amount) {
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

    public void isExceed(int amount) {
        if (amount > quantity) {
            throw new IllegalArgumentException(ErrorMessage.EXCEED_QUANTITY.getMessage());
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
