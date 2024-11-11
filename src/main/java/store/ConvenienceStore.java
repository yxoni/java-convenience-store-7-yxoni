package store;

public class ConvenienceStore {
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final Receipt receipt = new Receipt();
    private final ProductManager productManager = new ProductManager();

    public void payment(String name, int amount) {
        Amount purchaseAmount = productManager.purchase(name, amount);
        receipt.addAmount(purchaseAmount);
    }

    public void ending() {
        outputView.membershipGuide();
        receipt.membershipApply(inputView.readAnswer().trim());
        outputView.printReceipt(receipt);
    }
}
