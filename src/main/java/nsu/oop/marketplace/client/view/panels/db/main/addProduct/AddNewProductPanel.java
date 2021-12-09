package nsu.oop.marketplace.client.view.panels.db.main.addProduct;

import nsu.oop.marketplace.client.view.ViewUtils;
import nsu.oop.marketplace.client.view.panels.WindowPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import static nsu.oop.marketplace.client.view.ViewUtils.*;

public class AddNewProductPanel extends WindowPanel {
    private final AddNewProductListener listener;
    private final int width;
    private final int height;
    private final JButton addButton;

    private final JTextField name;
    private final JTextField price;
    private final JTextArea productDescription;
    private final JLabel emptyField;

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
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });
        return jTextArea;
    }

    public AddNewProductPanel(AddNewProductListener listener, int width, int height, int posX, int posY) {
        super("/client/AddNewProductPanel.png", width, height);
        this.listener = listener;
        this.width = width;
        this.height = height;
        this.addButton = ViewUtils.initButton(getPart(width, 0.17), getPart(height, 0.075), getPart(width, 0.292), getPart(height, 0.70), e -> addUser());
        add(addButton);
        productDescription = initTextArea("Task description", getPart(height, 0.025));
        JScrollPane scroll = new JScrollPane(productDescription);
        scroll.setBounds(getPart(width, 0.292), getPart(height, 0.38), getPart(width, 0.5), getPart(height, 0.28));
        add(scroll);

        emptyField = new JLabel("Please, fill in all the fields !");
        emptyField.setForeground(Color.RED);
        emptyField.setBounds(getPart(width, 0.292), getPart(height, 0.662), getPart(width, 0.3), getPart(height, 0.05));

        name = initTextField(getPart(height, 0.025));
        price = initTextField(getPart(height, 0.025));
        add(initScrollPane(name, getPart(width, 0.35), getPart(height, 0.07), getPart(width, 0.292), getPart(height, 0.16)));
        add(initScrollPane(price, getPart(width, 0.35), getPart(height, 0.07), getPart(width, 0.292), getPart(height, 0.27)));
        setBounds(posX, posY, width, height);

    }

    private void addUser() {
        String nameStr = name.getText();
        String priceStr = price.getText();
        String description =  productDescription.getText();
        if(nameStr.isEmpty() || priceStr.isEmpty() || description.isEmpty()){
            add(emptyField);
            repaint();
        } else {
            remove(emptyField);
            remove(addButton);
            repaint();
            listener.addNewProduct(nameStr,priceStr,description);
        }
    }
}
