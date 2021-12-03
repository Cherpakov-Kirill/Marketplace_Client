package nsu.oop.marketplace.client.view.panels.chat.dialogue;

import nsu.oop.marketplace.client.view.ViewUtils;
import nsu.oop.marketplace.client.view.panels.WindowPanel;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static nsu.oop.marketplace.client.view.ViewUtils.getPart;

public class DialoguePanel extends WindowPanel {
    private final String username;
    private final String chatName;
    private final JTextArea textArea;
    private final JButton sendButton;
    private final JTextField messageField;
    private final DialogueListener listener;
    private final int width;
    private final int height;

    private void sendMessage() {
        if (!messageField.getText().trim().isEmpty()) {
            String message =messageField.getText();
            /*if (!chatName.equals("Group Chat")) {
                message = "/private_message " + chatName + " " + messageField.getText();

            } else {
                message = messageField.getText();
            }*/
            if (!chatName.equals("Group Chat")) addNewMessage(username, messageField.getText());
            listener.sendNewMessage(message, chatName, username);
            messageField.setText("");
            messageField.grabFocus();
        }
    }

    private void configureTextArea() {
        textArea.setFont(new Font("Roboto", Font.PLAIN, getPart(width, 0.0286)));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setForeground(Color.WHITE);
        textArea.setBorder(null);
        textArea.setOpaque(false);
    }

    private void configureMessageField() {
        messageField.setFont(new Font("Roboto", Font.PLAIN, getPart(width, 0.0266)));
        messageField.setCaretColor(Color.WHITE);
        messageField.setSelectionColor(Color.GRAY);
        messageField.setDisabledTextColor(Color.WHITE);
        messageField.setSelectedTextColor(Color.GRAY);
        messageField.setForeground(Color.WHITE);
        messageField.setBorder(null);
        messageField.setOpaque(false);
        messageField.setPreferredSize(new Dimension(getPart(width, 0.8401), getPart(height, 0.0666)));
        Dimension messageFieldSize = messageField.getPreferredSize();
        messageField.setBounds(getPart(width, 0.0204), getPart(height, 0.9426), messageFieldSize.width, messageFieldSize.height);
        messageField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                messageField.setText("");
            }
        });
        messageField.addKeyListener(
                new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                            sendMessage();
                        }
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                    }
                }
        );
    }

    public DialoguePanel(DialogueListener listener, String username, String destination, int width, int height) {
        super("/chat/TextField.png", width, height);
        this.width = width;
        this.height = height;
        this.username = username;
        chatName = destination;
        this.listener = listener;
        textArea = new JTextArea();
        sendButton = ViewUtils.initButton(getPart(width, 0.102), getPart(height, 0.0666), getPart(width, 0.8934), getPart(height, 0.9426), e -> sendMessage());
        messageField = new JTextField("Type your message");
        configureTextArea();
        configureMessageField();
        add(ViewUtils.initLabel(destination, getPart(width, 0.0368), getPart(width, 0.4098), getPart(height, 0.04), getPart(width, 0.0307), getPart(height, 0.0266)));
        DefaultCaret caret = (DefaultCaret) textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        JScrollPane scrollPane = ViewUtils.initScrollPane(getPart(width, 0.9631), getPart(height, 0.8266), getPart(width, 0.0204), getPart(height, 0.1066));
        scrollPane.setViewportView(textArea);

        add(scrollPane);
        add(sendButton);
        add(messageField);
    }

    public void addNewMessage(String senderName, String message) {
        textArea.append(senderName + " : " + message);
        textArea.append("\n");
    }
}
