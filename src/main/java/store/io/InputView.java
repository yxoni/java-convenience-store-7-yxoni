package store.io;

import camp.nextstep.edu.missionutils.Console;
import store.type.ErrorMessage;

public class InputView {
    private final OutputView outputView = new OutputView();

    public String readAnswer() {
        while (true) {
            try {
                String input = Console.readLine();
                answerValidate(input);
                return input;
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    public String readPurchaseProduct() {
        while (true) {
            try {
                String input = Console.readLine();
                purchaseProductValidate(input);
                return input;
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    public void answerValidate(String input) {
        if (!"Y".equals(input) && !"N".equals(input)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT.getMessage());
        }
    }

    public void purchaseProductValidate(String input) {
        String regex = "\\[([a-zA-Z가-힣]+-[1-9][0-9]*)\\](,\\[([a-zA-Z가-힣]+-[1-9][0-9]*)\\])*";
        if (!input.matches(regex)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_FORMAT.getMessage());
        }
    }
}
