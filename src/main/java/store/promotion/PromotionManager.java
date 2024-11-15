package store.promotion;

import java.util.List;

public class PromotionManager {
    private final List<Promotion> promotions;

    public PromotionManager(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public Promotion match(String promotion) {
        return promotions.stream()
                .filter(p -> p.isMatch(promotion))
                .findFirst()
                .orElse(null);
    }
}
