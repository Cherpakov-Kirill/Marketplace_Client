import nsu.oop.marketplace.client.view.windows.db.DBWindow;
import nsu.oop.marketplace.client.view.windows.db.DBWindowListener;
import nsu.oop.marketplace.inet.MarketplaceProto;
import org.junit.jupiter.api.Test;

public class DBClientTest implements DBWindowListener {

    public DBClientTest(){
        DBWindow window = new DBWindow(this,896,"Cherpakov Kirill", MarketplaceProto.UserType.DIRECTOR);
    }

    @Override
    public void endClientSession() {

    }

    @Override
    public void logOut() {

    }


    public static void main(String[] args) {
        DBClientTest dbTest = new DBClientTest();

    }

}
