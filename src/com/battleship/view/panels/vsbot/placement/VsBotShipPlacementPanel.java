package com.battleship.view.panels.vsbot.placement;

import javax.swing.*;
import java.awt.*;

public class VsBotShipPlacementPanel extends JPanel {
    private ShipPlacementBoardPanel boardPanel;
    private VsBotPlacementInfoPanel infoPanel;

    public VsBotShipPlacementPanel(Font font, int cellSize) {
        setLayout(new BorderLayout());
        setOpaque(false);

        boardPanel = new ShipPlacementBoardPanel(font, cellSize);
        infoPanel = new VsBotPlacementInfoPanel(font);

        boardPanel.setPreferredSize(new Dimension(780, 720));
        infoPanel.setPreferredSize(new Dimension(500, 720));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(boardPanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.EAST);

        // Kết nối nút chọn tàu
        JToggleButton[] shipButtons = infoPanel.getShipButtons();
        for (int i = 0; i < shipButtons.length; i++) {
            int idx = i;
            shipButtons[i].addActionListener(e -> {
                if (!shipButtons[idx].isEnabled()) return;
                for (int j = 0; j < shipButtons.length; j++) {
                    if (j != idx) shipButtons[j].setSelected(false);
                }
                boardPanel.setSelectedShipButtonIdx(idx);
            });
            // Đăng ký callback để enable/disable nút khi cần
            boardPanel.setShipButtonUpdater(idx, () -> {
                boolean enabled = !boardPanel.isShipButtonPlaced(idx);
                shipButtons[idx].setEnabled(enabled);
                shipButtons[idx].setSelected(false);
            });
        }

        // Kết nối Undo/Redo/Reset/Rotate
        infoPanel.getUndoButton().addActionListener(e -> boardPanel.undo());
        infoPanel.getRedoButton().addActionListener(e -> boardPanel.redo());
        infoPanel.getResetButton().addActionListener(e -> boardPanel.resetBoard());
        infoPanel.getRotateButton().addActionListener(e -> boardPanel.toggleOrientation());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image bg = new ImageIcon(getClass().getResource(com.battleship.utils.ViewConstants.VSBOT_PLACESHIP_BG_IMG)).getImage();
        g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
    }

    public ShipPlacementBoardPanel getBoardPanel() { return boardPanel; }
    public VsBotPlacementInfoPanel getInfoPanel() { return infoPanel; }
}