package store;

import java.util.*;

public class InputParser {
    private Map<String, Integer> purchaseData;

    public Map<String, Integer> mapping(String input) {
        purchaseData = new HashMap<>();

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
        int amount = Integer.parseInt(parts[1]);

        validate(name);
        purchaseData.put(name, amount);
    }

    public void validate(String name) {
        if (purchaseData.containsKey(name)) {
            throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
        }
    }
}
