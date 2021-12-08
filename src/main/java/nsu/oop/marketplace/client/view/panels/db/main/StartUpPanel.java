package nsu.oop.marketplace.client.view.panels.db.main;

import nsu.oop.marketplace.client.view.panels.WindowPanel;

public class StartUpPanel extends WindowPanel {

    public StartUpPanel(int width, int height, int posX, int posY){
        super("/client/StartUp.png", width, height);
        setBounds(posX, posY, width, height);
    }

}
