package store;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FileReader {
    public List<Product> createProduct() {
        return read(road());
    }

    public InputStream road() {
        return getClass().getClassLoader().getResourceAsStream("products.md");
    }

    public Product parse(String product) {
        String[] productParts = product.split(",");
        String name = productParts[0];
        int price = Integer.parseInt(productParts[1]);
        int quantity = Integer.parseInt(productParts[2]);
        String promotion = productParts[3];

        return new Product(name, price, quantity, promotion);
    }

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
}
