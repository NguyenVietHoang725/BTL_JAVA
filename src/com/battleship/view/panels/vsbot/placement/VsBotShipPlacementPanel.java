package com.battleship.view.panels.vsbot.placement;

import javax.swing.*;
import java.awt.*;

public class VsBotShipPlacementPanel extends JPanel {
    private ShipPlacementBoardPanel boardPanel;
    private VsBotPlacementInfoPanel infoPanel;

    public VsBotShipPlacementPanel(Font font, int cellSize) {
        setLayout(new BorderLayout(24, 0));
        setOpaque(false);

        boardPanel = new ShipPlacementBoardPanel(font, cellSize);
        infoPanel = new VsBotPlacementInfoPanel(font);

        add(boardPanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.EAST);

        // Kết nối các nút chức năng với logic của boardPanel
        infoPanel.getRotateButton().addActionListener(e -> boardPanel.toggleOrientation());
        infoPanel.getResetButton().addActionListener(e -> boardPanel.resetBoard());
        // Bạn có thể thêm callback cho Confirm ở đây nếu muốn
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image bg = new ImageIcon(getClass().getResource(com.battleship.view.utils.ViewConstants.VSBOT_PLACESHIP_BG_IMG)).getImage();
        g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
    }

    public ShipPlacementBoardPanel getBoardPanel() { return boardPanel; }
    public VsBotPlacementInfoPanel getInfoPanel() { return infoPanel; }
}