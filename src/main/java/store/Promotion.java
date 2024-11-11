package store;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Promotion {
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final String name;
    private final long buy;
    private final long get;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Promotion(String name, long buy, long get, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Amount apply(String productName, long price, long quantity, long amount) {
        long total = buy + get;
        long maxAmount= quantity / total * total;
        if (amount <= maxAmount && amount % total == 0) {
            return new Amount(productName, price, amount, amount/total, 0, 0);
        }

        if (amount % total == buy && amount < maxAmount) {
            outputView.promotionAdditionalGuide(productName);
            if (inputView.readAnswer().equals("Y")) {
                return new Amount(productName, price, amount+1, (amount+1)/total, 0, 0);
            }
            return new Amount(productName, price, amount, amount/total, 0, 0);
        }

        long impossibleAmount = amount % total;
        if (amount > maxAmount) {
            impossibleAmount = amount - maxAmount;
        }
        outputView.promotionImpossibleGuide(productName, impossibleAmount);
        if (inputView.readAnswer().trim().equals("Y")) {
            if (quantity - (amount - impossibleAmount) < impossibleAmount) {
                return new Amount(productName, price, quantity, (amount-impossibleAmount)/total, amount-quantity, impossibleAmount);
            }
            return new Amount(productName, price, amount, (amount-impossibleAmount)/total, 0, impossibleAmount);
        }
        return new Amount(productName, price, amount-impossibleAmount, (amount-impossibleAmount)/total,0, 0);
    }

    public boolean isPossible(LocalDateTime now) {
        LocalDate nowDate = now.toLocalDate();
        return (startDate.isBefore(nowDate) || startDate.isEqual(nowDate)) &&
                (endDate.isAfter(nowDate) || endDate.isEqual(nowDate));
    }

    public boolean isMatch(String promotion) {
        return promotion.equals(name);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
