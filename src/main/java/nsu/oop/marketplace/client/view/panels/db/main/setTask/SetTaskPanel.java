package nsu.oop.marketplace.client.view.panels.db.main.setTask;

import nsu.oop.marketplace.client.view.ViewUtils;
import nsu.oop.marketplace.client.view.panels.WindowPanel;
import nsu.oop.marketplace.inet.MarketplaceProto;

import javax.swing.*;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

import static nsu.oop.marketplace.client.view.ViewUtils.*;

public class SetTaskPanel extends WindowPanel {
    private final SetTaskListener listener;
    private JComboBox<String> comboBox;
    private final JLabel emptyDescription;
    private final JTextArea taskDescription;
    private final JButton setButton;

    private final int width;
    private final int height;

    public JTextArea initTextArea(String text, int fontSize) {
        JTextArea jTextArea = new JTextArea();
        jTextArea.setText(text);
        jTextArea.setBackground(new Color(112, 160, 190));
        jTextArea.setFont(new Font("Roboto", Font.BOLD, fontSize));
        jTextArea.setLineWrap(true);
        jTextArea.setWrapStyleWord(true);
        jTextArea.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(jTextArea.getText().equals("Task description")) jTextArea.setText("");
                remove(emptyDescription);
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });
        return jTextArea;
    }


    public SetTaskPanel(SetTaskListener listener, int width, int height, int posX, int posY) {
        super("/client/SetNewTask.png", width, height);
        this.listener = listener;
        this.width = width;
        this.height = height;
        emptyDescription = new JLabel("Please, type task description!");
        emptyDescription.setForeground(Color.RED);
        emptyDescription.setBounds(getPart(width, 0.42), getPart(height, 0.25), getPart(width, 0.3), getPart(height, 0.05));

        this.setButton = ViewUtils.initButton(getPart(width, 0.17), getPart(height, 0.075), getPart(width, 0.415), getPart(height, 0.675), e -> setTask());
        add(setButton);
        taskDescription = initTextArea("Task description", getPart(height, 0.025));
        JScrollPane scroll = new JScrollPane(taskDescription);
        scroll.setBounds(getPart(width, 0.42), getPart(height, 0.31), getPart(width, 0.5), getPart(height, 0.3));
        add(scroll);
        setBounds(posX, posY, width, height);
    }

    public void updateUserList(List<MarketplaceProto.DBUser> userList) {
        String[] data = userList.stream().map(dbUser -> dbUser.getId() + ". " + dbUser.getFullUserName()).toList().toArray(String[]::new);
        this.comboBox = new JComboBox<>(data);
        comboBox.setBounds(getPart(width, 0.42), getPart(height, 0.14), getPart(width, 0.5), getPart(height, 0.05));
        add(comboBox);
    }

    private void setTask() {
        String task = taskDescription.getText();
        if (task.isEmpty() || task.equals("Task description")) {
            add(emptyDescription);
            repaint();
        } else {
            String selectedUser = (String) comboBox.getSelectedItem();
            if(selectedUser == null) return;
            remove(setButton);
            listener.setTask(Integer.parseInt(selectedUser.split("\\.")[0]),task);
        }
    }

}
