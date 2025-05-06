package com.battleship.view.panels.vsbot.placement;

import javax.swing.*;

import com.battleship.model.board.Board;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ShipPlacementBoardPanel extends JPanel {
    private static final int BOARD_SIZE = 10;
    private int cellSize;
    private Font font;

    // Danh sách các tàu cần đặt: {length, số lượng}
    private final int[] shipLengths = {5, 4, 3, 3, 2};
    private final boolean[] shipsPlaced = new boolean[5];
    private int currentShipIdx = 0; // Đang đặt tàu nào
    private boolean horizontal = true; // Hướng đặt

    private int[][] board = new int[BOARD_SIZE][BOARD_SIZE]; // 0: trống, >0: id tàu

    public ShipPlacementBoardPanel(Font font, int cellSize) {
        this.font = font;
        this.cellSize = cellSize;
        setPreferredSize(new Dimension(cellSize * BOARD_SIZE, cellSize * BOARD_SIZE));
        setOpaque(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() / cellSize;
                int y = e.getY() / cellSize;
                placeShip(x, y);
            }
        });
    }

    private void placeShip(int x, int y) {
        if (currentShipIdx >= shipLengths.length) return;
        int len = shipLengths[currentShipIdx];
        if (canPlaceShip(x, y, len, horizontal)) {
            for (int i = 0; i < len; i++) {
                int nx = x + (horizontal ? i : 0);
                int ny = y + (horizontal ? 0 : i);
                board[ny][nx] = currentShipIdx + 1;
            }
            shipsPlaced[currentShipIdx] = true;
            currentShipIdx++;
            repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Không thể đặt tàu ở vị trí này!", "Lỗi", JOptionPane.ERROR_MESSAGE);
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

    public void toggleOrientation() {
        horizontal = !horizontal;
    }

    public void resetBoard() {
        for (int[] row : board) Arrays.fill(row, 0);
        Arrays.fill(shipsPlaced, false);
        currentShipIdx = 0;
        repaint();
    }

    public boolean isAllShipsPlaced() {
        for (boolean placed : shipsPlaced) if (!placed) return false;
        return true;
    }

    // TODO: Hàm trả về Board (model) để truyền vào Player
    public Board getBoard() {
        Board result = new Board();
        // Giả sử Board có phương thức đặt tàu hoặc set trạng thái từng ô
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                if (board[y][x] > 0) {
                    // Đánh dấu có tàu tại (x, y)
                    result.getNode(x, y).setHasShip(true);
                }
            }
        }
        return result;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Vẽ lưới
        g.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i <= BOARD_SIZE; i++) {
            g.drawLine(i * cellSize, 0, i * cellSize, BOARD_SIZE * cellSize);
            g.drawLine(0, i * cellSize, BOARD_SIZE * cellSize, i * cellSize);
        }
        // Vẽ tàu đã đặt
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                if (board[y][x] > 0) {
                    g.setColor(new Color(70, 130, 180, 180));
                    g.fillRect(x * cellSize + 1, y * cellSize + 1, cellSize - 2, cellSize - 2);
                }
            }
        }
        // Vẽ viền cho tàu đang đặt
        if (currentShipIdx < shipLengths.length) {
            int len = shipLengths[currentShipIdx];
            g.setColor(Color.RED);
            // Mouse hover preview có thể bổ sung sau
        }
    }
}