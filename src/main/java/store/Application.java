package store;

import store.object.ConvenienceStore;

public class Application {
    public static void main(String[] args) {
        ConvenienceStore convenienceStore = new ConvenienceStore();
        convenienceStore.open();
        System.out.println();
    }
}