package store;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ProductParser {
    public List<Product> read(InputStream productsInputStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(productsInputStream))) {
            return reader.lines()
                    .skip(1)
                    .map(this::parse)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    public Product parse(String product) {
        String[] productParts = product.split(",");
        String name = productParts[0];
        int price = Integer.parseInt(productParts[1]);
        int quantity = Integer.parseInt(productParts[2]);
        String promotion = productParts[3];

        return new Product(name, price, quantity, promotion);
    }
}