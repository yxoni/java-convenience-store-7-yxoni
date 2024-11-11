package store;

import java.util.List;

public class ProductManager {
    private final List<Product> products;

    public ProductManager() {
        FileReader fileReader = new FileReader();
        products = fileReader.createProduct();
    }

    public Amount purchase(String name, int amount) {
        List<Product> purchaseProducts = products.stream()
                .filter(product -> product.isCorrect(name))
                .toList();

        Amount purchaseAmount = purchaseProducts.getFirst().buy(amount);
        if (purchaseAmount.isAdditional()) {
            Amount additionalAmount = purchaseProducts.getLast().buy(purchaseAmount.getAdditional());
            purchaseAmount.addBuyAmount(additionalAmount.getBuy());
        }
        return purchaseAmount;
    }

    public void print() {
        OutputView outputView = new OutputView();
        outputView.printProducts(products);
    }
}
