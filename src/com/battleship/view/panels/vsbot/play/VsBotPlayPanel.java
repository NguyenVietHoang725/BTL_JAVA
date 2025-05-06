package com.battleship.view.panels.vsbot.play;

import javax.swing.*;
import java.awt.*;
import com.battleship.view.utils.ViewConstants;

public class VsBotPlayPanel extends JPanel {
    private VsBotInfoAttackPanel infoAttackPanel;
    private VsBotPlayerBoardPanel playerBoardPanel;
    private VsBotBotBoardPanel botBoardPanel;
    private String difficulty; // "Easy", "Medium", "Hard"

    public VsBotPlayPanel(Font font, int cellSize, String difficulty) {
        this.difficulty = difficulty;
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        String bgPath = ViewConstants.VSBOT_EASY_BG_IMG;
        if ("Medium".equalsIgnoreCase(difficulty)) {
            bgPath = ViewConstants.VSBOT_MEDIUM_BG_IMG;
        } else if ("Hard".equalsIgnoreCase(difficulty)) {
            bgPath = ViewConstants.VSBOT_HARD_BG_IMG;
        }
        Image bg = new ImageIcon(getClass().getResource(bgPath)).getImage();
        g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
    }

    public VsBotInfoAttackPanel getInfoAttackPanel() { return infoAttackPanel; }
    public VsBotPlayerBoardPanel getPlayerBoardPanel() { return playerBoardPanel; }
    public VsBotBotBoardPanel getBotBoardPanel() { return botBoardPanel; }
}