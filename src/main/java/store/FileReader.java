package store;

import java.io.InputStream;
import java.util.List;

public class FileReader {
    public List<Product> createProduct() {
        ProductParser productParser = new ProductParser(new PromotionManager(createPromotion()));
        return productParser.read(road("products"));
    }

    public List<Promotion> createPromotion() {
        PromotionParser promotionParser = new PromotionParser();
        return promotionParser.read(road("promotions"));
    }

    public InputStream road(String filename) {
        return getClass().getClassLoader().getResourceAsStream(filename + ".md");
    }
}
