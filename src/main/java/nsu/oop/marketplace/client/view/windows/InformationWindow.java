package nsu.oop.marketplace.client.view.windows;

import javax.swing.*;

public class InformationWindow extends JFrame {
    public InformationWindow(String message){
        JOptionPane.showMessageDialog(this,
                message,
                "Info", JOptionPane.INFORMATION_MESSAGE,
                null);
    }
}
