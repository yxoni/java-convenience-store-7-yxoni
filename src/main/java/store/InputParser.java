package store;

import java.util.*;

public class InputParser {
    private Map<String, Long> purchaseData;

    public Map<String, Long> mapping(String input) {
        purchaseData = new LinkedHashMap<>();

        String[] items = input.replaceAll("[\\[\\]]", "").split(",");
        parseItem(items);

        return purchaseData;
    }

    public void parseItem(String[] items) {
        Arrays.stream(items)
                .map(item -> item.split("-"))
                .forEach(this::addPurchaseData);
    }

    public void addPurchaseData(String[] parts) {
        String name = parts[0];
        long amount = Long.parseLong(parts[1]);

        validate(name);
        purchaseData.put(name, amount);
    }

    public void validate(String name) {
        if (purchaseData.containsKey(name)) {
            throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
        }
    }
}
