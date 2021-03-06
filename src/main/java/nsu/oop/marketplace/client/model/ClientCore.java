package nsu.oop.marketplace.client.model;

import nsu.oop.marketplace.client.view.View;
import nsu.oop.marketplace.client.view.windows.ViewCore;
import nsu.oop.marketplace.client.view.windows.ViewCoreListener;
import nsu.oop.marketplace.inet.*;
import nsu.oop.marketplace.inet.messages.MessageBuilder;
import nsu.oop.marketplace.inet.users.InetForUsersController;
import nsu.oop.marketplace.inet.users.Users;
import nsu.oop.marketplace.inet.users.UsersController;
import nsu.oop.marketplace.inet.users.UsersControllerListener;


public class ClientCore implements InetControllerListener, UsersControllerListener, ViewCoreListener {
    private String username;

    private final Inet inet;
    private MarketplaceProto.SessionConfig config;
    private final Users users;
    private View view;

    public ClientCore(int port) {
        this.inet = new InetController(this, port, 1000, 2000);
        this.users = new UsersController(this, (InetForUsersController) inet);
        inet.attachUsers((UsersControllerForInet) users);
        inet.startMulticastReceiver();
    }

    @Override
    public void showErrorAuthMessage() {
        System.err.println("Bad authentication!");
        view.showErrorAuthMessage();
    }

    @Override
    public void receiveUserInfoMsg(int i, MarketplaceProto.UserType userType, String firstName, String secondName) {
        view.launchDBClient(userType, firstName, secondName);
    }

    @Override
    public void launchChat() {
        view.launchChat();
        users.sendChatMessage(
                MarketplaceProto.Message.ChatMessage.newBuilder()
                        .setJoin(MarketplaceProto.Message.ChatMessage.JoinMsg.newBuilder().setName(username)
                                .build())
                        .build(), 0);
    }

    @Override
    public void receiveErrorMsg(String error, int senderId) {
        System.err.println("Receiver error-message from node number " + senderId + " Message: " + error);
    }

    @Override
    public void receiveAnnouncementMsg(MarketplaceProto.Message.AnnouncementMsg announcementMsg, String ip, int port) {
        users.addUser(0, "ServerUser", ip, port, MarketplaceProto.UserType.UNAUTHENTICATED, "Server", "System");
        inet.startUnicast();
        this.view = new ViewCore(this); //start UI and get login password
    }

    @Override
    public void receiveChatMsg(MarketplaceProto.Message.ChatMessage chatMessage, int id) {
        switch (chatMessage.getTypeCase()) {
            case PUBLIC -> view.updateChatField("Group Chat", chatMessage.getPublic().getSenderName(), chatMessage.getPublic().getMessage());
            case PRIVATE -> view.updateChatField(chatMessage.getPrivate().getSenderName(), chatMessage.getPrivate().getSenderName(), chatMessage.getPrivate().getMessage());
            case LIST -> view.updateChatUserList(chatMessage.getList().getUserList().getNameList());
            default -> users.sendChatMessage(MessageBuilder.chatErrorMsgBuilder("Server can not send " + chatMessage.getTypeCase() + " message!"), id);
        }
    }

    @Override
    public void receiveDBResponseMsg(MarketplaceProto.Message.DBResponse dbResponse, int id) {
        switch (dbResponse.getTypeCase()) {
            case PRODUCT_TABLE -> view.updateProductTable(dbResponse.getProductTable().getFullProductList());
            case LOG_TABLE -> view.updateLogTable(dbResponse.getLogTable().getFullLogList());
            case TASK_TABLE -> view.updateTaskTable(dbResponse.getTaskTable().getFullTaskList());
            case SALE_TABLE -> view.updateSaleTable(dbResponse.getSaleTable().getFullSalesList());
            case CHANGE_TABLE -> view.updateGlobalChangesTable(dbResponse.getChangeTable().getFullChangeList());
            case COMPLETE_TASK -> view.updateCompleteTask(dbResponse.getCompleteTask());
            case ACCEPT_CHANGE -> view.updateAcceptChange(dbResponse.getAcceptChange());
            case USER_LIST -> view.updateDBUserList(dbResponse.getUserList().getUserList());
            case PRODUCT_LIST -> view.updateDBProductList(dbResponse.getProductList().getProductList());
            case SUCCESSFULLY -> view.showSuccessDBAction();
            case UNSUCCESSFULLY -> view.showFailedDBAction();
        }
    }

    @Override
    public void notifyCoreAboutDisconnect(int id) {
        if (id == 0) {
            exit("The server is unavailable.");
        }
    }

    @Override
    public void exit(String message) {
        inet.interruptUnicast();
        view.closeView(message);
        System.exit(0);
    }

    //View methods
    @Override
    public void logIn(String login, String password) {
        username = login;
        users.sendJoinMessage(login, password);
    }

    @Override
    public void sendChatMessage(String newMessage, String receiverName, String senderName) {
        switch (receiverName) {
            case "Group Chat" -> users.sendChatMessage(
                    MessageBuilder.chatPublicMsgBuilder(senderName, newMessage), 0);
            case "End the session" -> users.sendChatMessage(
                    MessageBuilder.chatEndMsgBuilder(senderName), 0);
            default -> users.sendChatMessage(
                    MessageBuilder.chatPrivateMsgBuilder(senderName, receiverName, newMessage), 0);
        }
    }

    @Override
    public void requestFullProductTable() {
        users.sendDBRequestMessage(MessageBuilder.dbFullProductTableRequestMsgBuilder(), 0);
    }

    @Override
    public void requestFullLogTable() {
        users.sendDBRequestMessage(MessageBuilder.dbFullLogTableRequestMsgBuilder(), 0);
    }

    @Override
    public void requestFullTaskTable() {
        users.sendDBRequestMessage(MessageBuilder.dbFullTaskTableRequestMsgBuilder(), 0);
    }

    @Override
    public void requestFullSalesTable() {
        users.sendDBRequestMessage(MessageBuilder.dbFullSalesTableRequestMsgBuilder(), 0);
    }

    @Override
    public void requestFullGlobalChangesTable() {
        users.sendDBRequestMessage(MessageBuilder.dbFullGlobalChangesTableRequestMsgBuilder(), 0);
    }

    @Override
    public void requestAcceptTheChange(int id) {
        users.sendDBRequestMessage(MessageBuilder.dbAcceptChangeRequestMsgBuilder(id), 0);
    }

    @Override
    public void requestCompleteTheTask(int id) {
        users.sendDBRequestMessage(MessageBuilder.dbCompleteTaskRequestMsgBuilder(id), 0);
    }

    @Override
    public void requestAddNewUser(String firstName, String secondName, String role, String login, String password) {
        users.sendDBRequestMessage(MessageBuilder.dbAddNewUserRequestMsgBuilder(firstName, secondName, role, login, password), 0);
    }

    @Override
    public void requestAddNewProduct(String name, String price, String description) {
        users.sendDBRequestMessage(MessageBuilder.dbAddNewProductRequestMsgBuilder(name, price, description), 0);
    }

    @Override
    public void requestChangeProductInfo(int id, String name, String price, String description) {
        users.sendDBRequestMessage(MessageBuilder.dbChangeProductInfoRequestMsgBuilder(id, name, price, description), 0);
    }

    @Override
    public void requestProductList() {
        users.sendDBRequestMessage(MessageBuilder.dbProductListRequestMsgBuilder(), 0);
    }

    @Override
    public void requestUserList() {
        users.sendDBRequestMessage(MessageBuilder.dbUserListRequestMsgBuilder(), 0);
    }

    @Override
    public void requestSetTask(int id, String task) {
        users.sendDBRequestMessage(MessageBuilder.dbSetTaskRequestMsgBuilder(id, task), 0);
    }


    //not used methods
    @Override
    public void receiveDBRequestMsg(MarketplaceProto.Message.DBRequest dbRequest, int i) {

    }

    @Override
    public int receiveJoinMsg(String s, String s1, String s2, int i) {
        return 0;
    }
}
