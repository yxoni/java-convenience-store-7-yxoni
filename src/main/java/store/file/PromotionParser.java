package store.file;

import store.promotion.Promotion;
import store.type.PromotionType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PromotionParser {
    public List<Promotion> read(InputStream productsInputStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(productsInputStream))) {
            return reader.lines()
                    .skip(1)
                    .map(this::parse)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    public Promotion parse(String promotion) {
        String[] promotionParts = promotion.split(",");
        String name = promotionParts[PromotionType.NAME.getValue()];
        int get = Integer.parseInt(promotionParts[PromotionType.GET.getValue()]);
        int buy = Integer.parseInt(promotionParts[PromotionType.BUY.getValue()]);
        LocalDate startDate = formatDate(promotionParts[PromotionType.START_DATE.getValue()]);
        LocalDate endDate = formatDate(promotionParts[PromotionType.END_DATE.getValue()]);

        return new Promotion(name, get, buy, startDate, endDate);
    }

    public LocalDate formatDate(String date) {
        try {
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(date, dateFormat);
        } catch (Exception e) {
            return null;
        }
    }
}
