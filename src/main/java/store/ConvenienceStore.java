package store;

import java.util.Map;

public class ConvenienceStore {
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final InputParser inputParser = new InputParser();
    private final ProductManager productManager = new ProductManager();
    private Receipt receipt;
    private boolean isOpen = true;

    public void open() {
        while (isOpen) {
            init();
            announcement();
            purchase();
            showReceipt();
            close();
            System.out.println();
        }
    }

    public void init() {
        receipt = new Receipt();
    }

    public void announcement() {
        outputView.greeting();
        productManager.print();
    }

    public void purchase() {
        outputView.purchaseGuide();
        while (true) {
            try {
                String purchaseProducts = inputView.readPurchaseProduct();
                if (purchaseProducts.equals("N")) {
                    break;
                }
                Map<String, Long> purchaseData = inputParser.mapping(purchaseProducts);
                productManager.validate(purchaseData);

                createReceipt(purchaseData);
                return;
            } catch (Exception e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    public void createReceipt(Map<String, Long> purchaseData) {
        purchaseData.forEach((name, amount) -> {
            Amount purchaseAmount = productManager.purchase(name, amount);
            receipt.addAmount(purchaseAmount);
        });
        membership();
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
