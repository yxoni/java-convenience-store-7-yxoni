package store;

public class Product {
    private String name;
    private int price;
    private int quantity;
    private String promotion;

    public Product(String name, int price, int quantity, String promotion) {
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
        if (quantity == 0) {
            return String.format("- %s %,d원 재고 없음 %s", name, price, promotion);
        }
        return String.format("- %s %,d원 %d개 %s", name, price, quantity, promotion);
    }
}
