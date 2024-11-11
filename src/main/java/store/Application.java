package store;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        ConvenienceStore convenienceStore = new ConvenienceStore();
        convenienceStore.payment("콜라", 1);
        convenienceStore.payment("오렌지주스", 2);
        convenienceStore.ending();
    }
}
