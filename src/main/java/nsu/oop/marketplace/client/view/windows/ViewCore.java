package nsu.oop.marketplace.client.view.windows;

import nsu.oop.marketplace.client.view.View;
import nsu.oop.marketplace.client.view.windows.authentication.Authentication;
import nsu.oop.marketplace.client.view.windows.authentication.AuthenticationListener;
import nsu.oop.marketplace.client.view.windows.chat.ChatWindow;
import nsu.oop.marketplace.client.view.windows.chat.ChatWindowListener;
import nsu.oop.marketplace.client.view.windows.db.DBWindow;
import nsu.oop.marketplace.client.view.windows.db.DBWindowListener;
import nsu.oop.marketplace.inet.MarketplaceProto;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;


public class ViewCore implements View, DBWindowListener, ChatWindowListener, AuthenticationListener {
    private final ViewCoreListener listener;
    private Authentication authentication;
    private String username;
    private ChatWindow chatWindow;
    private DBWindow dbWindow;
    private Properties appProps;


    public ViewCore(ViewCoreListener listener) {
        this.listener = listener;
        this.authentication = new Authentication(this);
        InputStream inputStream = getClass().getResourceAsStream("/view.properties");
        appProps = new Properties();
        try {
            appProps.load(inputStream);
        } catch (IOException e) {
            appProps = null;
            e.printStackTrace();
        }
    }

    @Override
    public void showErrorAuthMessage() {
        authentication.showErrorAuthMessage();
    }

    @Override
    public void closeView(String closeInfo) {
        if (chatWindow != null) chatWindow.closeTheChat();
        if (dbWindow != null) dbWindow.closeTheClient();
        if (authentication != null) authentication.closeTheAuth();
        InformationWindow infoWindow = new InformationWindow(closeInfo);
    }

    private int getWindowSizeForChat() {
        if (appProps != null) {
            return Integer.parseInt(appProps.getProperty("chatWindowSize"));
        } else {
            return 500;
        }
    }

    private int getWindowSizeForClient() {
        if (appProps != null) {
            return Integer.parseInt(appProps.getProperty("clientWindowSize"));
        } else {
            return 500;
        }
    }

    @Override
    public void launchDBClient(MarketplaceProto.UserType type, String firstName, String secondName) {
        this.authentication.closeTheAuth();
        this.authentication = null;
        this.dbWindow = new DBWindow(this, getWindowSizeForClient(), firstName + " " + secondName, type);
    }

    @Override
    public void endClientSession(String closeInfo) {
        listener.exit(closeInfo);
    }

    @Override
    public void logOut() {
        if (chatWindow != null) chatWindow.closeTheChat();
        if (dbWindow != null) dbWindow.closeTheClient();
        this.authentication = new Authentication(this);
    }

    @Override
    public void openChat() {
        listener.launchChat();
    }

    //Chat
    @Override
    public void launchChat() {
        this.chatWindow = new ChatWindow(this, getWindowSizeForChat(), username);
    }

    @Override
    public void updateChatField(String chatName, String senderName, String message) {
        chatWindow.updateChatField(chatName, senderName, message);
    }

    @Override
    public void updateUserList(List<String> list) {
        chatWindow.updateUserList(list);
    }

    @Override
    public void logIn(String login, String password) {
        this.username = login;
        listener.logIn(login, password);
    }

    @Override
    public void sendNewMessage(String newMessage, String receiverName, String senderName) {
        listener.sendChatMessage(newMessage, receiverName, senderName);
    }
}
