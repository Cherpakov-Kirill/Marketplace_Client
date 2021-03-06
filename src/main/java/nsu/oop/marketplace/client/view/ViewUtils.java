package nsu.oop.marketplace.client.view;

import nsu.oop.marketplace.client.view.windows.db.DBWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;

public class ViewUtils {
    public static int getPart(int param, double part) {
        return (int) (((double) param) * part);
    }

    public static JButton initButton(int width, int height, int posX, int posY, ActionListener listener) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(width, height));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);///
        button.setOpaque(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addActionListener(listener);
        Dimension startSize = button.getPreferredSize();
        button.setBounds(posX, posY, startSize.width, startSize.height);
        return button;
    }

    public static ImageIcon getImageButtonIcon(String fileDirectory, Color colorForButton, int width, int height) {
        URL file = DBWindow.class.getResource(fileDirectory);
        if (file == null) {
            BufferedImage defaultBackground = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = defaultBackground.createGraphics();
            graphics.setPaint(colorForButton);
            graphics.fillRect(0, 0, defaultBackground.getWidth(), defaultBackground.getHeight());
            return new ImageIcon(defaultBackground);
        } else {
            ImageIcon background = new ImageIcon(file);
            Image img = background.getImage();
            Image temp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            background = new ImageIcon(temp);
            return background;
        }
    }

    public static JLabel initLabel(String text, int fontSize, int width, int height, int posX, int posY) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Roboto", Font.BOLD, fontSize));
        label.setForeground(Color.WHITE);
        label.setPreferredSize(new Dimension(width, height));
        Dimension textSize = label.getPreferredSize();
        label.setBounds(posX, posY, textSize.width, textSize.height);
        return label;
    }

    public static JTextField initTextField(int fontSize) {
        JTextField field = new JTextField();
        field.setFont(new Font("Roboto", Font.BOLD, fontSize));
        field.setBackground(new Color(112, 160, 190));
        return field;
    }

    public static JScrollPane initScrollPane(JComponent component, int width, int height, int posX, int posY) {
        JScrollPane sp = initScrollPane(width, height, posX, posY);
        sp.setViewportView(component);
        return sp;
    }

    public static JScrollPane initScrollPane(int width, int height, int posX, int posY) {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().setOpaque(false);
        //scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(width, height));
        Dimension scrollPaneSize = scrollPane.getPreferredSize();
        scrollPane.setBounds(posX, posY, scrollPaneSize.width, scrollPaneSize.height);
        return scrollPane;
    }

    public static JTextField initTextField(String defaultText, Color color, int fontSize, int columns, int width, int height, int posX, int posY) {
        JTextField textField = new JTextField(defaultText, columns);
        textField.setFont(new Font("Roboto", Font.BOLD, fontSize));
        textField.setForeground(color);
        textField.setOpaque(false);
        textField.setBorder(BorderFactory.createEmptyBorder());
        textField.setPreferredSize(new Dimension(width, height));
        Dimension textSize = textField.getPreferredSize();
        textField.setBounds(posX, posY, textSize.width, textSize.height);
        return textField;
    }
}
