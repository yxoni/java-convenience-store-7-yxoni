package store.file;

import store.product.Product;
import store.promotion.PromotionManager;
import store.promotion.Promotion;
import store.type.ProductType;
import store.type.PromotionType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ProductParser {
    private final PromotionManager promotionManager;

    public ProductParser(PromotionManager promotionManager) {
        this.promotionManager = promotionManager;
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

    public Product parse(String product) {
        String[] productParts = product.split(",");
        String name = productParts[ProductType.NAME.getValue()];
        int price = Integer.parseInt(productParts[ProductType.PRICE.getValue()]);
        int quantity = Integer.parseInt(productParts[ProductType.QUANTITY.getValue()]);
        Promotion promotion = promotionManager.match(productParts[ProductType.PROMOTION.getValue()]);

        return new Product(name, price, quantity, promotion);
    }
}
