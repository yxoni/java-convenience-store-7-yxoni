package store;

public class ConvenienceStore {
    private final OutputView outputView = new OutputView();
    private final Receipt receipt = new Receipt();
    private final ProductManager productManager = new ProductManager();

    public void payment(String name, int amount) {
        Amount purchaseAmount = productManager.purchase(name, amount);
        receipt.addAmount(purchaseAmount);
    }

    public void ending() {
        outputView.printReceipt(receipt);
    }
}
