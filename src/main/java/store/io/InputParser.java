package store.io;

import store.type.ErrorMessage;

import java.util.*;

public class InputParser {
    private Map<String, Integer> purchaseData;

    public Map<String, Integer> mapping(String input) {
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
        int amount = Integer.parseInt(parts[1]);

        validate(name);
        purchaseData.put(name, amount);
    }

    public void validate(String name) {
        if (purchaseData.containsKey(name)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT.getMessage());
        }
    }
}
