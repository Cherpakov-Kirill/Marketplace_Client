import nsu.oop.marketplace.client.model.ClientCore;

public class Main {
    public static void main(String[] args) {
        int port = 1024 + (int) (Math.random() * 48126);
        ClientCore clientCore = new ClientCore(port);
    }
}
