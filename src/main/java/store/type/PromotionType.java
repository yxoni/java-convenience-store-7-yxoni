package store.type;

public enum PromotionType {
    NAME(0),
    GET(1),
    BUY(2),
    START_DATE(3),
    END_DATE(4);

    private final int value;

    PromotionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
