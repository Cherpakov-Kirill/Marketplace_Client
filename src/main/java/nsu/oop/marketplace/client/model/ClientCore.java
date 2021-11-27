package nsu.oop.marketplace.client.model;

import nsu.oop.marketplace.inet.*;
import nsu.oop.marketplace.inet.users.InetForUsersController;
import nsu.oop.marketplace.inet.users.Users;
import nsu.oop.marketplace.inet.users.UsersController;
import nsu.oop.marketplace.inet.users.UsersControllerListener;

public class ClientCore implements InetControllerListener, UsersControllerListener {
    private final Inet inet;
    private MarketplaceProto.SessionConfig config;
    private final Users users;

    public ClientCore(int port){
        this.inet = new InetController(this, port,1000,2000);
        this.users = new UsersController(this, (InetForUsersController) inet);
        inet.attachUsers((UsersControllerForInet) users);
        inet.startMulticastReceiver();
    }

    @Override
    public void showErrorAuthMessage() {
        System.err.println("Bad authentication!");
    }

    @Override
    public void launchClientCore(int userId, MarketplaceProto.UserType userType) {
        System.out.println("Authentication success!");
    }

    @Override
    public void receiveErrorMsg(String error, int senderId) {
        System.err.println("Receiver error-message from node number " + senderId + " Message: " + error);
    }

    @Override
    public void receiveAnnouncementMsg(MarketplaceProto.Message.AnnouncementMsg announcementMsg, String ip, int port) {
        users.addUser(0, "Server", ip, port, MarketplaceProto.UserType.UNAUTHENTICATED);
        inet.startUnicast();
        //todo start UI and get login password
        users.sendJoinMessage("Nikita", "1111");
    }

    //not used methods
    @Override
    public int receiveJoinMsg(String s, String s1, String s2, int i) {
        return 0;
    }
}
