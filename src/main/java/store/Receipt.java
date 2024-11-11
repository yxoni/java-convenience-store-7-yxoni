package store;

import java.util.ArrayList;
import java.util.List;

public class Receipt {
    private final List<Amount> amounts = new ArrayList<>();

    public void addAmount(Amount amount) {
        amounts.add(amount);
    }

    public String productReport() {
        StringBuilder productReport = new StringBuilder();
        amounts.forEach(amount -> productReport.append(amount.parseProductDetails()).append("\n"));
        return productReport.toString();
    }

    public String promotionReport() {
        StringBuilder promotionReport = new StringBuilder();
        amounts.forEach(amount -> promotionReport.append(amount.parsePromotionDetails()).append("\n"));
        return promotionReport.toString();
    }

    // Todo: 금액 정보
}
