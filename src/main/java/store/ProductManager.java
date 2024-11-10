package store;

import java.util.List;

public class ProductManager {
    private final List<Product> products;

    public ProductManager() {
        FileReader fileReader = new FileReader();
        products = fileReader.createProduct();
    }

    public void purchase(String name, int amount) {
        List<Product> purchaseProducts = products.stream()
                .filter(product -> product.isCorrect(name))
                .toList();

        int additionalAmount = purchaseProducts.getFirst().buy(amount);
        if (additionalAmount > 0) {
            purchaseProducts.getLast().buy(additionalAmount);
        }
    }

    public void print() {
        OutputView outputView = new OutputView();
        outputView.printProducts(products);
    }
}
