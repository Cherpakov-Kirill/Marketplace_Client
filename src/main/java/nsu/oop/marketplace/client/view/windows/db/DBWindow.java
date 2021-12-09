package nsu.oop.marketplace.client.view.windows.db;

import nsu.oop.marketplace.client.view.panels.db.main.addProduct.AddNewProductListener;
import nsu.oop.marketplace.client.view.panels.db.main.addProduct.AddNewProductPanel;
import nsu.oop.marketplace.client.view.panels.db.main.addUser.AddNewUserListener;
import nsu.oop.marketplace.client.view.panels.db.main.addUser.AddNewUserPanel;
import nsu.oop.marketplace.client.view.panels.db.main.changeProduct.ChangeProductListener;
import nsu.oop.marketplace.client.view.panels.db.main.changeProduct.ChangeProductPanel;
import nsu.oop.marketplace.client.view.panels.db.main.globalChanges.GlobalChangeLineListener;
import nsu.oop.marketplace.client.view.panels.db.main.globalChanges.GlobalChangeTablePanel;
import nsu.oop.marketplace.client.view.panels.db.main.logs.LogTablePanel;
import nsu.oop.marketplace.client.view.panels.db.main.product.ProductTablePanel;
import nsu.oop.marketplace.client.view.panels.db.main.StartUpPanel;
import nsu.oop.marketplace.client.view.panels.db.main.response.ServerResponseListener;
import nsu.oop.marketplace.client.view.panels.db.main.response.SuccessfullyPanel;
import nsu.oop.marketplace.client.view.panels.db.main.response.UnsuccessfullyPanel;
import nsu.oop.marketplace.client.view.panels.db.main.sales.SalesTablePanel;
import nsu.oop.marketplace.client.view.panels.db.main.setTask.SetTaskListener;
import nsu.oop.marketplace.client.view.panels.db.main.setTask.SetTaskPanel;
import nsu.oop.marketplace.client.view.panels.db.main.tasks.TaskLineListener;
import nsu.oop.marketplace.client.view.panels.db.main.tasks.TaskTablePanel;
import nsu.oop.marketplace.client.view.panels.db.menu.*;
import nsu.oop.marketplace.inet.MarketplaceProto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import static nsu.oop.marketplace.client.view.ViewUtils.getPart;

public class DBWindow extends JFrame implements ClientMenuListener, ServerResponseListener, TaskLineListener, GlobalChangeLineListener, AddNewUserListener, AddNewProductListener, SetTaskListener, ChangeProductListener {
    private static final String NAME = "Marketplace Client";

    private static final String MENU = "Menu";
    private static final String CLOSE = "Close";
    private static final String LOGOUT = "LogOut";


    private final int widthClientWindow;
    private final int heightClientWindow;
    private final int widthMenu;
    private final int heightMenu;
    private final int widthMainPanel;
    private final int heightMainPanel;

    private final DBWindowListener listener;

    private DBClientPanelType openedPanel;

    private final SuccessfullyPanel successfullyPanel;
    private final UnsuccessfullyPanel unsuccessfullyPanel;
    private final StartUpPanel startUpPanel;
    private ClientMenuPanel clientMenuPanel;
    private ProductTablePanel productTablePanel;
    private LogTablePanel logTablePanel;
    private TaskTablePanel taskTablePanel;
    private SalesTablePanel salesTablePanel;
    private GlobalChangeTablePanel globalChangeTablePanel;
    private AddNewUserPanel addNewUserPanel;
    private AddNewProductPanel addNewProductPanel;
    private SetTaskPanel setTaskPanel;
    private ChangeProductPanel changeProductPanel;

    private JMenuItem createMenuItem(String name, ActionListener listener) {
        JMenuItem item = new JMenuItem(name);
        item.addActionListener(listener);
        return item;
    }

    private void setupMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu(MENU);
        menu.add(createMenuItem(LOGOUT, e -> listener.logOut()));
        menu.add(createMenuItem(CLOSE, e -> listener.closeClientSession("Client was closed, bye!")));
        menuBar.add(menu);
        this.setJMenuBar(menuBar);
    }


    public DBWindow(DBWindowListener listener, int widthWindow, String fullName, MarketplaceProto.UserType type) {
        super(NAME);
        this.listener = listener;
        this.widthClientWindow = widthWindow;
        int height = widthWindow / 16 * 9;
        this.heightClientWindow = height + 58;
        this.widthMenu = getPart(widthClientWindow, 0.2);
        this.heightMenu = height;
        this.widthMainPanel = getPart(widthClientWindow, 0.8);
        this.heightMainPanel = height;
        this.setSize(widthClientWindow, heightClientWindow);
        setupMenu();
        switch (type) {
            case DIRECTOR -> this.clientMenuPanel = new DirectorMenuPanel(this, fullName, widthMenu, heightMenu);
            case MANAGER -> this.clientMenuPanel = new ManagerMenuPanel(this, fullName, widthMenu, heightMenu);
            case ADMINISTRATOR -> this.clientMenuPanel = new AdminMenuPanel(this, fullName, widthMenu, heightMenu);
        }
        this.startUpPanel = new StartUpPanel(widthMainPanel, heightMainPanel, widthMenu, 0);

        setContentOnFrame(getContentPanel(clientMenuPanel, startUpPanel));
        openedPanel = DBClientPanelType.StartUpPanel;
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                listener.closeClientSession("Client was closed, bye!");
            }
        });
        this.setResizable(false);
        this.setVisible(true);
        this.successfullyPanel = new SuccessfullyPanel(this, widthMainPanel, heightMainPanel, widthMenu, 0);
        this.unsuccessfullyPanel = new UnsuccessfullyPanel(this, widthMainPanel, heightMainPanel, widthMenu, 0);
    }

    private JPanel getContentPanel(ClientMenuPanel menu, JPanel main) {
        //openedPanel = DBClientPanelType.valueOf(main.getClass().getSimpleName());
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.add(menu);
        panel.add(main);
        return panel;
    }

    private void setContentOnFrame(Container pane) {
        this.setContentPane(pane);
        this.repaint();
        this.setVisible(true);
    }


    public void closeTheClient() {
        this.setVisible(false);
        dispose();
    }

    public void logOut() {
        listener.logOut();
    }

    @Override
    public void backToStart() {
        openedPanel = DBClientPanelType.valueOf(StartUpPanel.class.getSimpleName());
        setContentOnFrame(getContentPanel(clientMenuPanel, startUpPanel));
    }

    public void showSuccessDBAction() {
        openedPanel = DBClientPanelType.valueOf(SuccessfullyPanel.class.getSimpleName());
        setContentOnFrame(getContentPanel(clientMenuPanel, successfullyPanel));
    }

    public void showFailedDBAction() {
        openedPanel = DBClientPanelType.valueOf(UnsuccessfullyPanel.class.getSimpleName());
        setContentOnFrame(getContentPanel(clientMenuPanel, unsuccessfullyPanel));
    }

    public void updateUserList(List<MarketplaceProto.DBUser> userList) {
        switch (openedPanel) {
            case SetTaskPanel -> {
                setTaskPanel.updateUserList(userList);
                setContentOnFrame(getContentPanel(clientMenuPanel, setTaskPanel));
            }
            default -> System.err.println("Now opened other panel, not for userList");
        }
    }

    public void updateProductList(List<MarketplaceProto.DBProduct> productList) {
        switch (openedPanel) {
            case ChangeProductPanel -> {
                changeProductPanel.updateProductList(productList);
                setContentOnFrame(getContentPanel(clientMenuPanel, changeProductPanel));
            }
            default -> System.err.println("Now opened other panel, not for productList");
        }
    }


    /// Add new user
    @Override
    public void showAddNewUser() {
        openedPanel = DBClientPanelType.valueOf(AddNewUserPanel.class.getSimpleName());
        addNewUserPanel = new AddNewUserPanel(this, widthMainPanel, heightMainPanel, widthMenu, 0);
        setContentOnFrame(getContentPanel(clientMenuPanel, addNewUserPanel));
    }

    @Override
    public void addNewUser(String firstName, String secondName, String role, String login, String password) {
        listener.requestAddNewUser(firstName, secondName, role, login, password);
    }

    /// Add new product
    @Override
    public void showAddNewProduct() {
        openedPanel = DBClientPanelType.valueOf(AddNewProductPanel.class.getSimpleName());
        addNewProductPanel = new AddNewProductPanel(this, widthMainPanel, heightMainPanel, widthMenu, 0);
        setContentOnFrame(getContentPanel(clientMenuPanel, addNewProductPanel));
    }

    @Override
    public void addNewProduct(String name, String price, String description) {
        listener.requestAddNewProduct(name, price, description);
    }


    /// Change product info
    @Override
    public void showChangeProductInfo() {
        openedPanel = DBClientPanelType.valueOf(ChangeProductPanel.class.getSimpleName());
        changeProductPanel = new ChangeProductPanel(this, widthMainPanel, heightMainPanel, widthMenu, 0);
        listener.requestProductList();
    }


    @Override
    public void changeProductInfo(int id, String name, String price, String description) {
        listener.requestChangeProductInfo(id, name, price, description);
    }

    /// Set new task
    @Override
    public void showSetTask() {
        openedPanel = DBClientPanelType.valueOf(SetTaskPanel.class.getSimpleName());
        setTaskPanel = new SetTaskPanel(this, widthMainPanel, heightMainPanel, widthMenu, 0);
        listener.requestUserList();
    }

    @Override
    public void setTask(int id, String task) {
        listener.requestSetTask(id, task);
    }

    /// Logs
    @Override
    public void showLogs() {
        openedPanel = DBClientPanelType.valueOf(LogTablePanel.class.getSimpleName());
        logTablePanel = new LogTablePanel(widthMainPanel, heightMainPanel, widthMenu, 0);
        listener.requestFullLogTable();
    }

    public void updateLogTable(List<MarketplaceProto.DBFullLog> logs) {
        logTablePanel.updateLogTable(logs);
        setContentOnFrame(getContentPanel(clientMenuPanel, logTablePanel));
    }

    /// Global changes
    @Override
    public void showGlobalChanges() {
        openedPanel = DBClientPanelType.valueOf(GlobalChangeTablePanel.class.getSimpleName());
        globalChangeTablePanel = new GlobalChangeTablePanel(this, widthMainPanel, heightMainPanel, widthMenu, 0);
        listener.requestFullGlobalChangesTable();
    }

    public void updateGlobalChangesTable(List<MarketplaceProto.DBFullChanges> changes) {
        globalChangeTablePanel.updateGlobalChangesTable(changes);
        setContentOnFrame(getContentPanel(clientMenuPanel, globalChangeTablePanel));
    }

    public void updateAcceptChange(MarketplaceProto.Message.DBResponse.AcceptChange acceptChange) {
        if (acceptChange.getSuccess()) {
            globalChangeTablePanel.updateAcceptChange(acceptChange.getId());
        }
    }

    @Override
    public void acceptTheChange(int id) {
        listener.requestAcceptTheChange(id);
    }

    /// Sales table
    @Override
    public void showSales() {
        openedPanel = DBClientPanelType.valueOf(SalesTablePanel.class.getSimpleName());
        salesTablePanel = new SalesTablePanel(widthMainPanel, heightMainPanel, widthMenu, 0);
        listener.requestFullSalesTable();
    }

    public void updateSaleTable(List<MarketplaceProto.DBFullSales> sales) {
        salesTablePanel.updateSaleTable(sales);
        setContentOnFrame(getContentPanel(clientMenuPanel, salesTablePanel));
    }

    /// Task table
    @Override
    public void showTasks() {
        openedPanel = DBClientPanelType.valueOf(TaskTablePanel.class.getSimpleName());
        taskTablePanel = new TaskTablePanel(this, widthMainPanel, heightMainPanel, widthMenu, 0);
        listener.requestFullTaskTable();
    }

    public void updateTaskTable(List<MarketplaceProto.DBFullTask> tasks) {
        taskTablePanel.updateTaskTable(tasks);
        setContentOnFrame(getContentPanel(clientMenuPanel, taskTablePanel));
    }

    public void updateCompleteTask(MarketplaceProto.Message.DBResponse.CompleteTask completeTask) {
        if (completeTask.getSuccess()) {
            taskTablePanel.updateCompleteTask(completeTask.getId());
        }
    }

    @Override
    public void completeTheTask(int id) {
        listener.requestCompleteTheTask(id);
    }

    /// Product table
    @Override
    public void showProductTable() {
        openedPanel = DBClientPanelType.valueOf(ProductTablePanel.class.getSimpleName());
        productTablePanel = new ProductTablePanel(widthMainPanel, heightMainPanel, widthMenu, 0);
        listener.requestFullProductTable();
    }

    public void updateProductTable(List<MarketplaceProto.DBFullProduct> products) {
        productTablePanel.updateProductTable(products);
        setContentOnFrame(getContentPanel(clientMenuPanel, productTablePanel));
    }

    /// Chat
    @Override
    public void openChat() {
        listener.openChat();
    }
}
