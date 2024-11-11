package store;

import camp.nextstep.edu.missionutils.Console;

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
        if(!"Y".equals(input) && !"N".equals(input)) {
            throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
        }
    }

    public void purchaseProductValidate(String input) {
        String regex = "\\[([a-zA-Z가-힣]+-[1-9][0-9]*)\\](,\\[([a-zA-Z가-힣]+-[1-9][0-9]*)\\])*";
        if (!input.matches(regex)) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }
}
