package nsu.oop.marketplace.client.view.windows.db;

import nsu.oop.marketplace.client.view.panels.chat.dialogue.DialoguePanel;
import nsu.oop.marketplace.client.view.panels.db.main.StartUpPanel;
import nsu.oop.marketplace.client.view.panels.db.menu.ClientMenuListener;
import nsu.oop.marketplace.client.view.panels.db.menu.ClientMenuPanel;
import nsu.oop.marketplace.client.view.panels.db.menu.DirectorMenuPanel;
import nsu.oop.marketplace.inet.MarketplaceProto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static nsu.oop.marketplace.client.view.ViewUtils.getPart;

public class DBWindow extends JFrame implements ClientMenuListener {
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
    private final StartUpPanel startUpPanel;
    private ClientMenuPanel clientMenuPanel;

    private JMenuItem createMenuItem(String name, ActionListener listener) {
        JMenuItem item = new JMenuItem(name);
        item.addActionListener(listener);
        return item;
    }

    private void setupMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu(MENU);
        menu.add(createMenuItem(LOGOUT, e -> listener.logOut()));
        menu.add(createMenuItem(CLOSE, e -> listener.endClientSession("Client was closed, bye!")));
        menuBar.add(menu);
        this.setJMenuBar(menuBar);
    }


    public DBWindow(DBWindowListener listener, int widthWindow, String fullName, MarketplaceProto.UserType type) {
        super(NAME);
        this.listener = listener;
        this.widthClientWindow = widthWindow;
        int height = widthWindow / 16 * 9;
        this.heightClientWindow = height+58;
        this.widthMenu = getPart(widthClientWindow, 0.2);
        this.heightMenu = height;
        this.widthMainPanel = getPart(widthClientWindow, 0.8);
        this.heightMainPanel = height;
        this.setSize(widthClientWindow, heightClientWindow);
        setupMenu();
        switch (type){
            case DIRECTOR -> {
                this.clientMenuPanel = new DirectorMenuPanel(this, fullName, widthMenu, heightMenu);
            }
            /*case MANAGER -> {

            }
            case ADMINISTRATOR -> {

            }*/
        }
        this.startUpPanel = new StartUpPanel(widthMainPanel, heightMainPanel);
        startUpPanel.setPreferredSize(new Dimension(widthMainPanel, heightMainPanel));
        Dimension chatPanelSize = startUpPanel.getPreferredSize();
        startUpPanel.setBounds(widthMenu, 0, chatPanelSize.width, chatPanelSize.height);
        setContentOnFrame(getContentPanel(clientMenuPanel, startUpPanel));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                listener.endClientSession("Client was closed, bye!");
            }
        });
        this.setResizable(false);
        this.setVisible(true);
    }

    private JPanel getContentPanel(ClientMenuPanel menu, JPanel main) {
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
    public void showLogs() {
        System.out.println("show logs");
    }

    @Override
    public void openChat() {
        listener.openChat();
    }

    @Override
    public void showProductTable() {

    }
}
