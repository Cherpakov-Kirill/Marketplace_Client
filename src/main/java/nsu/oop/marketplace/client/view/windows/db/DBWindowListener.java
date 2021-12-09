package nsu.oop.marketplace.client.view.windows.db;

public interface DBWindowListener {
    void closeClientSession(String closeInfo);
    void logOut();
    void openChat();
    void requestFullProductTable();
    void requestFullLogTable();
    void requestFullTaskTable();
    void requestFullSalesTable();
    void requestFullGlobalChangesTable();

    void requestAcceptTheChange(int id);

    void requestCompleteTheTask(int id);
}
