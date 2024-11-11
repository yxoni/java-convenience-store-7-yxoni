package store;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class InputParser {
    public Map<String, Integer> mapping(String input) {
        Map<String, Integer> purchaseData = new HashMap<>();

        String[] items = input.replaceAll("[\\[\\]]", "").split(",");

        Arrays.stream(items)
                .map(item -> item.split("-"))
                .forEach(parts -> purchaseData.put(parts[0], Integer.parseInt(parts[1])));
        return purchaseData;
    }
}
