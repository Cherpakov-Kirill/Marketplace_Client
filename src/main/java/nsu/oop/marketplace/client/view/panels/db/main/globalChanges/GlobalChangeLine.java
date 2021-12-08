package nsu.oop.marketplace.client.view.panels.db.main.globalChanges;

import nsu.oop.marketplace.client.view.ViewUtils;
import nsu.oop.marketplace.client.view.panels.WindowPanel;
import nsu.oop.marketplace.inet.MarketplaceProto;

import static nsu.oop.marketplace.client.view.ViewUtils.getPart;

public class GlobalChangeLine extends WindowPanel {

    public GlobalChangeLine(GlobalChangeLineListener listener, int width, int height, int posX, int posY, MarketplaceProto.DBFullChanges change) {
        super("/client/acceptGlobalChanges/AcceptGlobalChangesTable.png", width, height);
        add(ViewUtils.initButton(getPart(width,0.095), getPart(height,0.8), getPart(width,0.905), getPart(height,0.1), e -> listener.acceptTheChange(change.getId())));
        add(ViewUtils.initLabel(String.valueOf(change.getId()), getPart(width, 0.02), getPart(width, 0.10), getPart(height, 0.7), getPart(width, 0.01), getPart(height, 0.13)));
        add(ViewUtils.initLabel(change.getProductName(), getPart(width, 0.02), getPart(width, 0.24), getPart(height, 0.7), getPart(width, 0.09), getPart(height, 0.13)));
        add(ViewUtils.initLabel(change.getChangeType(), getPart(width, 0.02), getPart(width, 0.13), getPart(height, 0.7), getPart(width, 0.325), getPart(height, 0.13)));
        add(ViewUtils.initLabel(change.getNewValue(), getPart(width, 0.02), getPart(width, 0.23), getPart(height, 0.7), getPart(width, 0.465), getPart(height, 0.13)));
        add(ViewUtils.initLabel(change.getUserName(), getPart(width, 0.02), getPart(width, 0.22), getPart(height, 0.7), getPart(width, 0.69), getPart(height, 0.13)));
        setBounds(posX, posY, width, height);
    }
}
