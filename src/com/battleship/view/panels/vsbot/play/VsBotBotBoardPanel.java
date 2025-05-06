package com.battleship.view.panels.vsbot.play;

import com.battleship.view.components.board.GameBoardPanel;
import com.battleship.view.utils.ResourceLoader;
import com.battleship.view.utils.ViewConstants;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class VsBotBotBoardPanel extends JPanel {
    private GameBoardPanel boardPanel;
    private int cellSize;

    public VsBotBotBoardPanel(Font font, int cellSize) {
        this.cellSize = cellSize;
        setLayout(new BorderLayout());
        setOpaque(false);

        Border outerBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE, 3, true),
                "BOT'S SHIPS",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                font.deriveFont(Font.BOLD, 18f),
                Color.WHITE
            ),
            BorderFactory.createEmptyBorder(12, 12, 12, 12)
        );

        add(Box.createVerticalStrut(10), BorderLayout.NORTH);

        Color headerColor = new Color(255, 215, 0); // Gold
        Font headerFont = font.deriveFont(Font.BOLD, 22f);
        Border headerBorder = BorderFactory.createMatteBorder(2, 2, 1, 1, Color.WHITE);
        Border cellBorder = BorderFactory.createMatteBorder(1, 1, 2, 2, Color.WHITE);

        ImageIcon normalIcon = new ImageIcon(ResourceLoader.loadImage(ViewConstants.CELL_NORMAL_IMG)
                .getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH));
        ImageIcon hitIcon = new ImageIcon(ResourceLoader.loadImage(ViewConstants.CELL_HIT_IMG)
                .getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH));
        ImageIcon missIcon = new ImageIcon(ResourceLoader.loadImage(ViewConstants.CELL_MISS_IMG)
                .getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH));
        ImageIcon hoverIcon = new ImageIcon(ResourceLoader.loadImage(ViewConstants.CELL_HOVER_IMG)
                .getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH));

        boardPanel = new GameBoardPanel(
            "", // No title here, already in outerBorder
            10,
            normalIcon, missIcon, hoverIcon, hitIcon,
            cellSize,
            headerFont,
            headerColor,
            headerBorder,
            cellBorder,
            outerBorder
        );

        add(boardPanel, BorderLayout.CENTER);
    }

    public JButton getButton(int row, int col) {
        return boardPanel.getButton(row, col);
    }

    public int getCellSize() {
        return cellSize;
    }

    public void setCellState(int row, int col, com.battleship.enums.CellState state) {
        boardPanel.setCellState(row, col, state);
    }
}