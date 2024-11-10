package store;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Promotion {
    private String name;
    private int buy;
    private int get;
    private LocalDate startDate;
    private LocalDate endDate;

    public Promotion(String name, int buy, int get, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
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
