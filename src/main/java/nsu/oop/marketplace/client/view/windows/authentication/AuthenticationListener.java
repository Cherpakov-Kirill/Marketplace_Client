package nsu.oop.marketplace.client.view.windows.authentication;

public interface AuthenticationListener {
    void logIn(String login, String password);
    void closeClientSession(String closeInfo);
}
