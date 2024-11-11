package store;

public class Amount {
    private final String name;
    private final int price;
    private long buy;
    private final long get;
    private final long additional;
    private final long membership;

    public Amount(String name, long price, long buy, long get, long additional, long membership) {
        this.name = name;
        this.price = price;
        this.buy = buy;
        this.get = get;
        this.additional = additional;
        this.membership = membership;
    }

    public long getBuy() {
        return buy;
    }

    public long getAdditional() {
        return additional;
    }

    public long totalPrice() {
        return price * buy;
    }

    public long promotionPrice() {
        return price * get;
    }

    public long  membershipAvailablePrice() {
        return price * membership;
    }

    public void addBuyAmount(long amount) {
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
