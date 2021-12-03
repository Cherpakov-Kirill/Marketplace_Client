package nsu.oop.marketplace.client.view.windows.authentication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Authentication extends JFrame {
    private JLabel windowName, user, pass, badAuth;
    private JTextField nameField;
    private JButton button;
    private JPasswordField passField;
    private AuthenticationListener listener;

    public Authentication(AuthenticationListener listener) {
        super("Authentication Form");
        this.listener = listener;
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 260);
        windowName = new JLabel("Authentication Form");
        windowName.setForeground(Color.blue);
        windowName.setFont(new Font("Roboto", Font.BOLD, 20));
        user = new JLabel("Username");
        pass = new JLabel("Password");
        badAuth = new JLabel("Wrong login or password");
        badAuth.setForeground(Color.RED);

        nameField = new JTextField("Kirill");
        passField = new JPasswordField("0000");
        button = new JButton("LogIn");
        button.addActionListener(e -> {
            remove(badAuth);
            repaint();
            listener.logIn(nameField.getText(), new String(passField.getPassword()));
        });

        windowName.setBounds(50, 30, 400, 30);
        user.setBounds(50, 70, 200, 30);
        pass.setBounds(50, 110, 200, 30);
        badAuth.setBounds(200, 160, 200, 30);
        nameField.setBounds(200, 70, 200, 30);
        passField.setBounds(200, 110, 200, 30);
        button.setBounds(50, 160, 140, 30);

        add(windowName);
        add(user);
        add(pass);
        add(nameField);
        add(passField);
        add(button);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                closeTheChat();
            }
        });
    }

    public void showErrorAuthMessage() {
        add(badAuth);
        repaint();
    }

    public void closeTheAuth() {
        this.setVisible(false);
        dispose();
    }

    public void closeTheChat() {
        closeTheAuth();
        listener.endChatSession();
    }
}
