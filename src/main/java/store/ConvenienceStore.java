package store;

import java.util.Map;

public class ConvenienceStore {
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final InputParser inputParser = new InputParser();
    private final Receipt receipt = new Receipt();
    private final ProductManager productManager = new ProductManager();
    private boolean isOpen = true;

    public void open() {
        while (isOpen) {
            announcement();
            purchase();
            membership();
            showReceipt();
            close();
        }
    }

    public void announcement() {
        outputView.greeting();
        productManager.print();
    }

    public void purchase() {
        outputView.purchaseGuide();
        String purchaseProducts = inputView.readPurchaseProduct();
        Map<String, Integer> purchaseData = inputParser.mapping(purchaseProducts);
        createReceipt(purchaseData);
    }

    public void createReceipt(Map<String, Integer> purchaseData) {
        purchaseData.forEach((name, amount) -> {
            Amount purchaseAmount = productManager.purchase(name, amount);
            receipt.addAmount(purchaseAmount);
        });
    }

    public void membership() {
        outputView.membershipGuide();
        receipt.membershipApply(inputView.readAnswer().trim());
    }

    public void showReceipt() {
        outputView.printReceipt(receipt);
    }

    public void close() {
        outputView.closingGuide();
        if (inputView.readAnswer().equals("N")) {
            isOpen = false;
        }
    }
}
