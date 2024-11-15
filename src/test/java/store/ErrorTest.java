package store;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.io.InputParser;
import store.io.InputView;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ErrorTest extends NsTest {
    @ValueSource(strings = {"[콜라,1]", "asdf", "[콜라-1][사이다-2]"})
    @ParameterizedTest
    void 올바르지_않은_형식의_상품명과_수량(String input) {
        InputView inputView = new InputView();
        assertThatThrownBy(() -> inputView.purchaseProductValidate(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
    }

    @Test
    void 중복되는_상품명() {
        InputParser inputParser = new InputParser();
        assertThatThrownBy(() -> inputParser.mapping("[콜라-2],[콜라-3]"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
    }

    @Test
    void 존재하지_않는_상품명() {
        assertSimpleTest(() -> {
            runException("[사이-12]");
            assertThat(output()).contains("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
        });
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
