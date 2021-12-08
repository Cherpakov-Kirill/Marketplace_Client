package nsu.oop.marketplace.client.view.windows.db;

public interface DBWindowListener {
    void endClientSession(String closeInfo);
    void logOut();
    void openChat();
}
