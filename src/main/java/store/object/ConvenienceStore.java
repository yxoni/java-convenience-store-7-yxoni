package store.object;

import store.io.InputParser;
import store.io.InputView;
import store.io.OutputView;
import store.product.ProductManager;

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
                inputHandle();
                return;
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    public void inputHandle() {
        String purchaseProducts = inputView.readPurchaseProduct();
        Map<String, Integer> purchaseData = inputParser.mapping(purchaseProducts);
        productManager.validate(purchaseData);
        createReceipt(purchaseData);

    }

    public void createReceipt(Map<String, Integer> purchaseData) {
        purchaseData.forEach((name, amount) -> {
            Amount purchaseAmount = productManager.purchase(name, amount);
            receipt.addAmount(purchaseAmount);
        });
        if (receipt.isExistence()) {
            membership();
            showReceipt();
        }
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
