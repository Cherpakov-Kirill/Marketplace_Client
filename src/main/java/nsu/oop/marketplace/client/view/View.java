package nsu.oop.marketplace.client.view;

import nsu.oop.marketplace.inet.MarketplaceProto;

import java.util.List;

public interface View {
    void showErrorAuthMessage();
    void launchChat();
    void launchDBClient(MarketplaceProto.UserType type, String firstName, String secondName);
    void closeView(String closeInfo);

    void updateChatField(String chatName, String senderName, String message);
    void updateUserList(List<String> list);

    void updateProductTable(List<MarketplaceProto.DBFullProduct> products);
    void updateLogTable(List<MarketplaceProto.DBFullLog> logs);
    void updateTaskTable(List<MarketplaceProto.DBFullTask> tasks);
    void updateSaleTable(List<MarketplaceProto.DBFullSales> sales);
    void updateGlobalChangesTable(List<MarketplaceProto.DBFullChanges> fullChangeList);

    void updateCompleteTask(MarketplaceProto.Message.DBResponse.CompleteTask completeTask);

    void updateAcceptChange(MarketplaceProto.Message.DBResponse.AcceptChange acceptChange);
}
