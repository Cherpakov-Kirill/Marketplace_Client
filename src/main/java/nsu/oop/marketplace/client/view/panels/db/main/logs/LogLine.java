package nsu.oop.marketplace.client.view.panels.db.main.logs;

import nsu.oop.marketplace.client.view.ViewUtils;
import nsu.oop.marketplace.client.view.panels.WindowPanel;
import nsu.oop.marketplace.inet.MarketplaceProto;

import static nsu.oop.marketplace.client.view.ViewUtils.getPart;

public class LogLine extends WindowPanel {

    public LogLine(int width, int height, int posX, int posY, MarketplaceProto.DBFullLog log) {
        super("/client/logsTable/LogsTable.png", width, height);
        add(ViewUtils.initLabel(log.getUserName(), getPart(width, 0.02), getPart(width, 0.26), getPart(height, 0.7), getPart(width, 0.01), getPart(height, 0.13)));
        add(ViewUtils.initLabel(log.getActionType(), getPart(width, 0.02), getPart(width, 0.13), getPart(height, 0.7), getPart(width, 0.273), getPart(height, 0.13)));
        add(ViewUtils.initLabel(log.getDescription(), getPart(width, 0.02), getPart(width, 0.60), getPart(height, 0.7), getPart(width, 0.405), getPart(height, 0.13)));
        setBounds(posX, posY, width, height);
    }
}
