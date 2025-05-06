package com.battleship.view.panels.vsbot.play;

import javax.swing.*;
import java.awt.*;

public class VsBotPlayPanel extends JPanel {
    private VsBotInfoAttackPanel infoAttackPanel;
    private VsBotPlayerBoardPanel playerBoardPanel;
    private VsBotBotBoardPanel botBoardPanel;

    public VsBotPlayPanel(Font font, int cellSize) {
        setLayout(new BorderLayout());
        setOpaque(false);

        // Panel chứa 2 bảng
        JPanel boardsPanel = new JPanel();
        boardsPanel.setLayout(new GridLayout(1, 2, 32, 0));
        boardsPanel.setOpaque(false);

        playerBoardPanel = new VsBotPlayerBoardPanel(font, cellSize);
        botBoardPanel = new VsBotBotBoardPanel(font, cellSize);

        boardsPanel.add(playerBoardPanel);
        boardsPanel.add(botBoardPanel);

        // Panel info + attack (bên trái)
        infoAttackPanel = new VsBotInfoAttackPanel(font);

        add(infoAttackPanel, BorderLayout.WEST);
        add(boardsPanel, BorderLayout.CENTER);
    }

    public VsBotInfoAttackPanel getInfoAttackPanel() { return infoAttackPanel; }
    public VsBotPlayerBoardPanel getPlayerBoardPanel() { return playerBoardPanel; }
    public VsBotBotBoardPanel getBotBoardPanel() { return botBoardPanel; }
}