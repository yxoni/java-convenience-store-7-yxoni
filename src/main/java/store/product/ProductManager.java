package store.product;

import store.object.Amount;
import store.file.FileReader;
import store.io.OutputView;
import store.type.ErrorMessage;

import java.util.List;
import java.util.Map;

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

        isExceed(purchaseProducts, amount);
        return applyPromotion(purchaseProducts, amount);
    }

    public Amount applyPromotion(List<Product> purchaseProducts, int amount) {
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

    public void isExceed(List<Product> purchaseProducts, int amount) {
        int totalQuantity = 0;
        for (Product product : purchaseProducts) {
            totalQuantity += product.getQuantity();
        }
        if (totalQuantity < amount) {
            throw new IllegalArgumentException(ErrorMessage.EXCEED_QUANTITY.getMessage());
        }
    }

    public void validate(Map<String, Integer> purchaseData) {
        for (String productName : purchaseData.keySet()) {
            boolean exists = products.stream()
                    .anyMatch(product -> product.isExist(productName));
            if (!exists) {
                throw new IllegalArgumentException(ErrorMessage.NON_EXISTING_PRODUCT.getMessage());
            }
        }
    }
}
