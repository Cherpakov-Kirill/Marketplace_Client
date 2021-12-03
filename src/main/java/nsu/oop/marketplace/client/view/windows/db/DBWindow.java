package nsu.oop.marketplace.client.view.windows.db;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DBWindow extends JFrame {
    private static final String NAME = "Marketplace Client";
    private static final String MENU = "Menu";
    private final int widthWindow;
    private final int heightWindow;

    private int widthField;
    private int heightField;

    private DBWindowListener listener;

    //private LogInPanel logInPanel;

    public DBWindow(DBWindowListener listener) {
        super(NAME);
        this.widthWindow = 860;
        this.heightWindow = widthWindow / 16 * 9;
        this.setFocusable(true);
        this.setResizable(false);
        //logInPanel = new LogInPanel(this, widthWindow, heightWindow);
        //this.setContentPane(logInPanel);

        this.pack();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                closeTheClient();
            }
        });
    }

    private void setContentOnFrame(Container pane) {
        this.setContentPane(pane);
        this.repaint();
        this.setVisible(true);
    }


    public void closeTheClient() {
        this.setVisible(false);
        dispose();
        listener.endClientSession();
    }

    public void logOut() {
        listener.logOut();
    }
}
