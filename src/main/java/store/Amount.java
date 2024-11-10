package store;

public class Amount {
    private int buy;
    private int additional;

    public Amount(int buy, int additional) {
        this.buy = buy;
        this.additional = additional;
    }

    public int getBuy() {
        return buy;
    }

    public int getAdditional() {
        return additional;
    }
}
