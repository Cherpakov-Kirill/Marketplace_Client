package nsu.oop.marketplace.client.view.windows.db;

import nsu.oop.marketplace.client.view.panels.db.main.globalChanges.GlobalChangeLineListener;
import nsu.oop.marketplace.client.view.panels.db.main.globalChanges.GlobalChangeTablePanel;
import nsu.oop.marketplace.client.view.panels.db.main.logs.LogTablePanel;
import nsu.oop.marketplace.client.view.panels.db.main.product.ProductTablePanel;
import nsu.oop.marketplace.client.view.panels.db.main.StartUpPanel;
import nsu.oop.marketplace.client.view.panels.db.main.sales.SalesTablePanel;
import nsu.oop.marketplace.client.view.panels.db.main.tasks.TaskLineListener;
import nsu.oop.marketplace.client.view.panels.db.main.tasks.TaskTablePanel;
import nsu.oop.marketplace.client.view.panels.db.menu.ClientMenuListener;
import nsu.oop.marketplace.client.view.panels.db.menu.ClientMenuPanel;
import nsu.oop.marketplace.client.view.panels.db.menu.DirectorMenuPanel;
import nsu.oop.marketplace.inet.MarketplaceProto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import static nsu.oop.marketplace.client.view.ViewUtils.getPart;

public class DBWindow extends JFrame implements ClientMenuListener, TaskLineListener, GlobalChangeLineListener {
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

    private final StartUpPanel startUpPanel;
    private ClientMenuPanel clientMenuPanel;
    private ProductTablePanel productTablePanel;
    private LogTablePanel logTablePanel;
    private TaskTablePanel taskTablePanel;
    private SalesTablePanel salesTablePanel;
    private GlobalChangeTablePanel globalChangeTablePanel;

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
            case DIRECTOR -> {
                this.clientMenuPanel = new DirectorMenuPanel(this, fullName, widthMenu, heightMenu);
            }
            /*case MANAGER -> {

            }
            case ADMINISTRATOR -> {

            }*/
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
    }

    private JPanel getContentPanel(ClientMenuPanel menu, JPanel main) {
        openedPanel = DBClientPanelType.valueOf(main.getClass().getSimpleName());
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

    /// Logs
    @Override
    public void showLogs() {
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
        globalChangeTablePanel = new GlobalChangeTablePanel(this, widthMainPanel, heightMainPanel, widthMenu, 0);
        listener.requestFullGlobalChangesTable();
    }

    public void updateGlobalChangesTable(List<MarketplaceProto.DBFullChanges> changes) {
        globalChangeTablePanel.updateGlobalChangesTable(changes);
        setContentOnFrame(getContentPanel(clientMenuPanel, globalChangeTablePanel));
    }

    public void updateAcceptChange(MarketplaceProto.Message.DBResponse.AcceptChange acceptChange) {
        if(acceptChange.getSuccess()){
            globalChangeTablePanel.updateAcceptChange(acceptChange.getId());
        }
    }

    @Override
    public void acceptTheChange(int id) {
        System.out.println("Accept " + id);
        listener.requestAcceptTheChange(id);
    }

    /// Sales table
    @Override
    public void showSales() {
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
        taskTablePanel = new TaskTablePanel(this, widthMainPanel, heightMainPanel, widthMenu, 0);
        listener.requestFullTaskTable();
    }

    public void updateTaskTable(List<MarketplaceProto.DBFullTask> tasks) {
        taskTablePanel.updateTaskTable(tasks);
        setContentOnFrame(getContentPanel(clientMenuPanel, taskTablePanel));
    }

    public void updateCompleteTask(MarketplaceProto.Message.DBResponse.CompleteTask completeTask) {
        if(completeTask.getSuccess()){
            taskTablePanel.updateCompleteTask(completeTask.getId());
        }
    }

    @Override
    public void completeTheTask(int id) {
        System.out.println("Complete " + id);
        listener.requestCompleteTheTask(id);
    }

    /// Product table
    @Override
    public void showProductTable() {
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
