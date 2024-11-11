package store;

import java.util.ArrayList;
import java.util.List;

public class Receipt {
    private final List<Amount> amounts = new ArrayList<>();
    private boolean membershipDiscount;
    private long totalPrice = 0;
    private long totalBuyAmount = 0;
    private long promotionPrice = 0;
    private long membershipAvailablePrice = 0;
    private long membershipPrice = 0;
    private long payment = 0;

    public void addAmount(Amount amount) {
        amounts.add(amount);
    }

    public void membershipApply(String input) {
        if (input.equals("Y")) {
            membershipDiscount = true;
        }
    }

    public String productReport() {
        StringBuilder productReport = new StringBuilder();
        amounts.forEach(amount -> productReport.append(amount.parseProductDetails()));
        return productReport.toString();
    }

    public String promotionReport() {
        StringBuilder promotionReport = new StringBuilder();
        amounts.forEach(amount -> promotionReport.append(amount.parsePromotionDetails()));
        return promotionReport.toString();
    }

    public String paymentReport() {
        calcPayment();
        return parsePaymentDetails();
    }

    public void calcPayment() {
        amounts.forEach(amount -> totalPrice += amount.totalPrice());
        amounts.forEach(amount -> totalBuyAmount += amount.getBuy());
        amounts.forEach(amount -> promotionPrice += amount.promotionPrice());
        amounts.forEach(amount -> membershipAvailablePrice += amount.membershipAvailablePrice());
        if (membershipDiscount) {
            membershipPrice = (long) Math.floor(membershipAvailablePrice * 0.3);
        }
        if (membershipPrice > 8000) {
            membershipPrice = 8000;
        }
        payment = totalPrice - promotionPrice - membershipPrice;
    }

    public String parsePaymentDetails() {
        StringBuilder paymentReport = new StringBuilder();
        String totalPriceFormat = "%-19s%-10d%,-6d";
        String otherPriceFormat = "%-29s%s%,-6d";
        return (paymentReport.append(String.format(totalPriceFormat, "총구매액", totalBuyAmount, totalPrice)).append("\n")
                .append(String.format(otherPriceFormat, "행사할인", "-", promotionPrice)).append("\n")
                .append(String.format(otherPriceFormat, "멤버십할인", "-", membershipPrice)).append("\n")
                .append(String.format(otherPriceFormat, "내실돈", "", payment)).append("\n")).toString();
    }
}
