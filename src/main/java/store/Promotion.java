package store;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Promotion {
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final String name;
    private final int buy;
    private final int get;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Promotion(String name, int buy, int get, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Amount apply(String productName, int quantity, int amount) {
        int total = buy + get;
        int maxAmount= quantity / total * total;
        if (amount <= maxAmount && amount % total == 0) {
            return new Amount(amount, 0);
        }
        if (amount % total == buy && amount < maxAmount) {
            outputView.promotionAdditionalGuide(productName);
            if (inputView.readAnswer().equals("Y")) {
                return new Amount(amount+1, 0);
            }
            return new Amount(amount, 0);
        }

        int impossibleAmount = amount % total;
        if (amount > maxAmount) {
            impossibleAmount = amount - maxAmount;
        }
        outputView.promotionImpossibleGuide(productName, impossibleAmount);
        if (inputView.readAnswer().trim().equals("Y")) {
            return new Amount(amount-impossibleAmount, impossibleAmount);
        }
        return new Amount(amount / total * total, 0);
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
