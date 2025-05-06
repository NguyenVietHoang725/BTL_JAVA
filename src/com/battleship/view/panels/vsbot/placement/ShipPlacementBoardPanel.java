package com.battleship.view.panels.vsbot.placement;

import com.battleship.enums.CellState;
import com.battleship.view.components.board.GameBoardPanel;
import com.battleship.view.utils.ResourceLoader;
import com.battleship.view.utils.ViewConstants;
import com.battleship.model.board.Board;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

public class ShipPlacementBoardPanel extends JPanel {
    private static final int BOARD_SIZE = 10;
    private int cellSize;
    private Font font;

    private GameBoardPanel boardPanel;
    private int[][] board = new int[BOARD_SIZE][BOARD_SIZE]; // 0: empty, >0: ship id

    // Ship placement
    private final int[] shipLengths = {5, 4, 3, 3, 2};
    private final boolean[] shipsPlaced = new boolean[5];
    private int currentShipIdx = 0;
    private boolean horizontal = true;

    public ShipPlacementBoardPanel(Font font, int cellSize) {
        this.font = font;
        this.cellSize = cellSize;

        setLayout(new BorderLayout());
        setOpaque(false);

        // Border giống Challenge
        Border outerBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE, 3, true),
                "YOUR BOARD",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                font.deriveFont(Font.BOLD, 18f),
                Color.WHITE
            ),
            BorderFactory.createEmptyBorder(12, 12, 12, 12)
        );

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
            "", // Không cần title ở đây, đã có outerBorder
            BOARD_SIZE,
            normalIcon, missIcon, hoverIcon, hitIcon,
            cellSize,
            headerFont,
            headerColor,
            headerBorder,
            cellBorder,
            outerBorder
        );
        boardPanel.setOpaque(false);

        // Gắn sự kiện đặt tàu cho từng ô
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                JButton btn = boardPanel.getButton(y, x);
                int fx = x, fy = y;
                btn.addActionListener(e -> placeShip(fx, fy));
                btn.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        if (currentShipIdx < shipLengths.length && board[fy][fx] == 0 && canPlaceShip(fx, fy, shipLengths[currentShipIdx], horizontal)) {
                            previewShip(fx, fy, true);
                        }
                    }
                    @Override
                    public void mouseExited(MouseEvent e) {
                        if (currentShipIdx < shipLengths.length && board[fy][fx] == 0) {
                            previewShip(fx, fy, false);
                        }
                    }
                });
            }
        }

        add(boardPanel, BorderLayout.CENTER);
    }

    private void placeShip(int x, int y) {
        if (currentShipIdx >= shipLengths.length) return;
        int len = shipLengths[currentShipIdx];
        if (canPlaceShip(x, y, len, horizontal)) {
            for (int i = 0; i < len; i++) {
                int nx = x + (horizontal ? i : 0);
                int ny = y + (horizontal ? 0 : i);
                board[ny][nx] = currentShipIdx + 1;
                boardPanel.setCellState(ny, nx, CellState.HIT);
            }
            shipsPlaced[currentShipIdx] = true;
            currentShipIdx++;
        } else {
            JOptionPane.showMessageDialog(this, "Cannot place ship here!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean canPlaceShip(int x, int y, int len, boolean horizontal) {
        for (int i = 0; i < len; i++) {
            int nx = x + (horizontal ? i : 0);
            int ny = y + (horizontal ? 0 : i);
            if (nx < 0 || nx >= BOARD_SIZE || ny < 0 || ny >= BOARD_SIZE) return false;
            if (board[ny][nx] != 0) return false;
        }
        return true;
    }

    private void previewShip(int x, int y, boolean hover) {
    	if (currentShipIdx >= shipLengths.length) return;
        int len = shipLengths[currentShipIdx];
        for (int i = 0; i < len; i++) {
            int nx = x + (horizontal ? i : 0);
            int ny = y + (horizontal ? 0 : i);
            if (nx < 0 || nx >= BOARD_SIZE || ny < 0 || ny >= BOARD_SIZE) continue;
            if (board[ny][nx] == 0) {
                boardPanel.setCellState(ny, nx, hover ? CellState.HOVER : CellState.NORMAL);
            }
        }
    }

    public void toggleOrientation() {
        horizontal = !horizontal;
    }

    public void resetBoard() {
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                board[y][x] = 0;
                boardPanel.setCellState(y, x, CellState.NORMAL);
            }
        }
        for (int i = 0; i < shipsPlaced.length; i++) shipsPlaced[i] = false;
        currentShipIdx = 0;
    }

    public boolean isAllShipsPlaced() {
        for (boolean placed : shipsPlaced) if (!placed) return false;
        return true;
    }

    public Board getBoard() {
        Board result = new Board();
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                if (board[y][x] > 0) {
                    result.getNode(x, y).setHasShip(true);
                }
            }
        }
        return result;
    }
}