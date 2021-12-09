package nsu.oop.marketplace.client.view.panels.db.main.addUser;

import nsu.oop.marketplace.client.view.ViewUtils;
import nsu.oop.marketplace.client.view.panels.WindowPanel;
import nsu.oop.marketplace.client.view.panels.db.main.setTask.SetTaskListener;

import javax.swing.*;

import java.awt.*;

import static nsu.oop.marketplace.client.view.ViewUtils.*;

public class AddNewUserPanel extends WindowPanel {
    private final AddNewUserListener listener;
    private final int width;
    private final int height;
    private final JButton addButton;

    private final JTextField firstName;
    private final JTextField secondName;
    private final JComboBox<String> role;
    private final JTextField login;
    private final JTextField password;
    private final JLabel emptyField;

    public AddNewUserPanel(AddNewUserListener listener, int width, int height, int posX, int posY) {
        super("/client/AddNewUserPanel.png", width, height);
        this.listener = listener;
        this.width = width;
        this.height = height;
        this.addButton = ViewUtils.initButton(getPart(width, 0.17), getPart(height, 0.075), getPart(width, 0.292), getPart(height, 0.71), e -> addUser());
        add(addButton);
        emptyField = new JLabel("Please, fill in all the fields !");
        emptyField.setForeground(Color.RED);
        emptyField.setBounds(getPart(width, 0.292), getPart(height, 0.665), getPart(width, 0.3), getPart(height, 0.05));

        firstName = initTextField(getPart(height, 0.025));
        secondName = initTextField(getPart(height, 0.025));
        String[] roles = {"Director", "Admin", "Manager"};
        role = new JComboBox<>(roles);
        login = initTextField(getPart(height, 0.025));
        password = initTextField(getPart(height, 0.025));
        add(initScrollPane(firstName, getPart(width, 0.35), getPart(height, 0.07), getPart(width, 0.292), getPart(height, 0.16)));
        add(initScrollPane(secondName, getPart(width, 0.35), getPart(height, 0.07), getPart(width, 0.292), getPart(height, 0.27)));
        role.setBounds(getPart(width, 0.292), getPart(height, 0.38), getPart(width, 0.35), getPart(height, 0.07));
        add(role);
        add(initScrollPane(login, getPart(width, 0.35), getPart(height, 0.07), getPart(width, 0.292), getPart(height, 0.49)));
        add(initScrollPane(password, getPart(width, 0.35), getPart(height, 0.07), getPart(width, 0.292), getPart(height, 0.60)));

        setBounds(posX, posY, width, height);

    }

    private void addUser() {
        String firstNameStr = firstName.getText();
        String secondNameStr = secondName.getText();
        String roleStr =  (String) role.getSelectedItem();
        if(roleStr == null) return;
        String loginStr = login.getText();
        String passwordStr = password.getText();
        if(firstNameStr.isEmpty() || secondNameStr.isEmpty() || roleStr.isEmpty() || loginStr.isEmpty() || passwordStr.isEmpty()){
            add(emptyField);
            repaint();
        } else {
            remove(emptyField);
            remove(addButton);
            repaint();
            listener.addNewUser(firstNameStr,secondNameStr,roleStr,loginStr,passwordStr);
        }
    }
}
