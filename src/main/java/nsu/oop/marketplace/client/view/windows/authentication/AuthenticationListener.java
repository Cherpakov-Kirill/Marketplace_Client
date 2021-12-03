package nsu.oop.marketplace.client.view.windows.authentication;

public interface AuthenticationListener {
    void logIn(String name, String password);
    void endChatSession();
}
