package nsu.oop.marketplace.client.view.panels.db.main.changeProduct;

import nsu.oop.marketplace.client.view.ViewUtils;
import nsu.oop.marketplace.client.view.panels.WindowPanel;
import nsu.oop.marketplace.inet.MarketplaceProto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

import static nsu.oop.marketplace.client.view.ViewUtils.*;

public class ChangeProductPanel extends WindowPanel {
    private final ChangeProductListener listener;
    private final int width;
    private final int height;
    private final JButton addButton;
    private JComboBox<String> comboBox;

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
                if (jTextArea.getText().equals("Task description")) jTextArea.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });
        return jTextArea;
    }

    public ChangeProductPanel(ChangeProductListener listener, int width, int height, int posX, int posY) {
        super("/client/ChangeProductInfo.png", width, height);
        this.listener = listener;
        this.width = width;
        this.height = height;
        this.addButton = ViewUtils.initButton(getPart(width, 0.17), getPart(height, 0.075), getPart(width, 0.292), getPart(height, 0.8), e -> changeProductInfo());
        add(addButton);
        productDescription = initTextArea("Task description", getPart(height, 0.025));
        JScrollPane scroll = new JScrollPane(productDescription);
        scroll.setBounds(getPart(width, 0.292), getPart(height, 0.5), getPart(width, 0.5), getPart(height, 0.27));
        add(scroll);

        emptyField = new JLabel("Please, fill in all the fields !");
        emptyField.setForeground(Color.RED);
        emptyField.setBounds(getPart(width, 0.292), getPart(height, 0.76), getPart(width, 0.3), getPart(height, 0.05));

        name = initTextField(getPart(height, 0.025));
        price = initTextField(getPart(height, 0.025));
        add(initScrollPane(name, getPart(width, 0.35), getPart(height, 0.07), getPart(width, 0.292), getPart(height, 0.28)));
        add(initScrollPane(price, getPart(width, 0.35), getPart(height, 0.07), getPart(width, 0.292), getPart(height, 0.39)));
        setBounds(posX, posY, width, height);

    }

    public void updateProductList(List<MarketplaceProto.DBProduct> productListList) {
        String[] data = productListList.stream().map(dbProduct -> dbProduct.getId() + ". " + dbProduct.getName()).toList().toArray(String[]::new);
        this.comboBox = new JComboBox<>(data);
        comboBox.setBounds(getPart(width, 0.292), getPart(height, 0.16), getPart(width, 0.5), getPart(height, 0.05));
        add(comboBox);
    }

    private void changeProductInfo() {
        String nameStr = name.getText();
        String priceStr = price.getText();
        String description = productDescription.getText();
        if (nameStr.isEmpty() || priceStr.isEmpty() || description.isEmpty()) {
            add(emptyField);
            repaint();
        } else {
            String selectedUser = (String) comboBox.getSelectedItem();
            if (selectedUser == null) return;
            remove(emptyField);
            remove(addButton);
            repaint();
            listener.changeProductInfo(Integer.parseInt(selectedUser.split("\\.")[0]), nameStr, priceStr, description);
        }
    }
}
