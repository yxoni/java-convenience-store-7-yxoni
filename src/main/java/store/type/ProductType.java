package store.type;

public enum ProductType {
    NAME(0),
    PRICE(1),
    QUANTITY(2),
    PROMOTION(3);

    private final int value;

    ProductType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
