package store;

public class Amount {
    private final String name;
    private final int price;
    private int buy;
    private final int get;
    private final int additional;

    public Amount(String name, int price, int buy, int get, int additional) {
        this.name = name;
        this.price = price;
        this.buy = buy;
        this.get = get;
        this.additional = additional;
    }

    public int getBuy() {
        return buy;
    }

    public int getAdditional() {
        return additional;
    }

    public int totalPrice() {
        return price * buy;
    }

    public int promotionPrice() {
        return price * get;
    }

    public void addBuyAmount(int amount) {
        this.buy += amount;
    }

    public String parseProductDetails() {
        if (buy == 0) {
            return "";
        }
        String productReportFormat = "%-19s%-10d%,-6d\n";
        return String.format(productReportFormat, name, buy, price*buy);
    }

    public String parsePromotionDetails() {
        if (get == 0) {
            return "";
        }
        String promotionReportFormat = "%-19s%,-10d\n";
        return String.format(promotionReportFormat, name, get);
    }

    public boolean isAdditional() {
        return additional > 0;
    }
}
