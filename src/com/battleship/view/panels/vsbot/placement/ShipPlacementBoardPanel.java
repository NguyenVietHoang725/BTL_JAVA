package com.battleship.view.panels.vsbot.placement;

import com.battleship.enums.CellState;
import com.battleship.view.components.board.GameBoardPanel;
import com.battleship.view.utils.ResourceLoader;
import com.battleship.view.utils.ViewConstants;
import com.battleship.model.board.Board;
import com.battleship.model.ship.Ship;
import com.battleship.model.ship.ShipPlacement;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class ShipPlacementBoardPanel extends JPanel {
    private static final int BOARD_SIZE = 10;
    private int cellSize;
    private Font font;

    private GameBoardPanel boardPanel;
    private ShipPlacement shipPlacement;
    private int selectedShipButtonIdx = -1; // index của nút tàu đang chọn (0-4)
    private boolean horizontal = true;
    private int lastPreviewX = -1, lastPreviewY = -1, lastPreviewLen = -1;
    private boolean lastPreviewHorizontal = true;

    // Callback để cập nhật trạng thái nút tàu trên InfoPanel
    private Runnable[] shipButtonUpdaters = new Runnable[ShipPlacement.SHIP_LENGTHS.length];

    // Đánh dấu nút nào đã đặt (kể cả 2 nút tàu 3)
    private boolean[] shipButtonPlaced = new boolean[ShipPlacement.SHIP_LENGTHS.length];

    // Lưu lại thứ tự đặt tàu (để undo đúng nút)
    private final List<Integer> placedShipButtonOrder = new ArrayList<>();

    public ShipPlacementBoardPanel(Font font, int cellSize) {
        this.font = font;
        this.cellSize = cellSize;

        setLayout(new BorderLayout());
        setOpaque(false);

        shipPlacement = new ShipPlacement(new Board());

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
        ImageIcon shipIcon = new ImageIcon(ResourceLoader.loadImage(ViewConstants.CELL_SHIP_IMG)
                .getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH));
        
        boardPanel = new GameBoardPanel(
            "", // Không cần title ở đây, đã có outerBorder
            BOARD_SIZE,
            normalIcon, missIcon, hoverIcon, hitIcon, shipIcon,
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
                        if (selectedShipButtonIdx != -1 && !shipButtonPlaced[selectedShipButtonIdx]) {
                            int len = ShipPlacement.SHIP_LENGTHS[selectedShipButtonIdx];
                            if (canPlaceShip(fx, fy, len, horizontal)) {
                                lastPreviewHorizontal = horizontal;
                                lastPreviewX = fx;
                                lastPreviewY = fy;
                                lastPreviewLen = len;
                                previewShip(fx, fy, len, true);
                            }
                        }
                    }
                    @Override
                    public void mouseExited(MouseEvent e) {
                        if (selectedShipButtonIdx != -1 && !shipButtonPlaced[selectedShipButtonIdx]) {
                            int len = ShipPlacement.SHIP_LENGTHS[selectedShipButtonIdx];
                            previewShip(fx, fy, len, false);
                        }
                    }
                });
            }
        }

        add(boardPanel, BorderLayout.CENTER);
    }

    // Được gọi từ InfoPanel khi chọn nút tàu
    public void setSelectedShipButtonIdx(int idx) {
        this.selectedShipButtonIdx = idx;
    }

    // Được gọi từ InfoPanel để truyền callback cập nhật trạng thái nút tàu
    public void setShipButtonUpdater(int idx, Runnable updater) {
        shipButtonUpdaters[idx] = updater;
    }

    public ShipPlacement getShipPlacement() {
        return shipPlacement;
    }

    private void placeShip(int x, int y) {
        if (selectedShipButtonIdx == -1) return;
        int idx = selectedShipButtonIdx;
        if (shipButtonPlaced[idx]) return;

        // Chỉ cho phép đặt nếu vị trí click trùng với preview cuối cùng
        if (x != lastPreviewX || y != lastPreviewY || lastPreviewLen != ShipPlacement.SHIP_LENGTHS[idx]) {
            JOptionPane.showMessageDialog(this, "Please hover over the cell before placing the ship!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean placed = shipPlacement.placeShip(lastPreviewLen, lastPreviewX, lastPreviewY, lastPreviewHorizontal);
        if (placed) {
            shipButtonPlaced[idx] = true;
            placedShipButtonOrder.add(idx);
            if (shipButtonUpdaters[idx] != null) shipButtonUpdaters[idx].run();
            selectedShipButtonIdx = -1;
            updateBoardView();
        }
    }

    public void undo() {
        if (shipPlacement.undo()) {
            // Lấy index nút tàu vừa undo (theo thứ tự đặt)
            if (!placedShipButtonOrder.isEmpty()) {
                int idx = placedShipButtonOrder.remove(placedShipButtonOrder.size() - 1);
                shipButtonPlaced[idx] = false;
                if (shipButtonUpdaters[idx] != null) shipButtonUpdaters[idx].run();
            }
            updateBoardView();
        }
    }

    public void redo() {
        if (shipPlacement.redo()) {
            // Tìm index nút tàu cần disable lại (theo thứ tự đặt lại)
            // Đếm số tàu đã đặt, disable đúng nút tiếp theo chưa disable
            for (int i = 0; i < shipButtonPlaced.length; i++) {
                if (!shipButtonPlaced[i]) {
                    shipButtonPlaced[i] = true;
                    placedShipButtonOrder.add(i);
                    if (shipButtonUpdaters[i] != null) shipButtonUpdaters[i].run();
                    break;
                }
            }
            updateBoardView();
        }
    }

    public void toggleOrientation() {
        horizontal = !horizontal;
        // Nếu đang hover, có thể gọi lại previewShip với hướng mới (nếu muốn)
    }

    public void resetBoard() {
        shipPlacement.reset();
        selectedShipButtonIdx = -1;
        for (int i = 0; i < shipButtonPlaced.length; i++) {
            shipButtonPlaced[i] = false;
            if (shipButtonUpdaters[i] != null) shipButtonUpdaters[i].run();
        }
        placedShipButtonOrder.clear();
        updateBoardView();
    }

    private boolean canPlaceShip(int x, int y, int len, boolean horizontal) {
        return shipPlacement.canPlaceShip(len, x, y, horizontal);
    }

    private void previewShip(int x, int y, int len, boolean hover) {
        for (int i = 0; i < len; i++) {
            int nx = x + (horizontal ? 0 : i);
            int ny = y + (horizontal ? i : 0);
            if (nx < 0 || nx >= BOARD_SIZE || ny < 0 || ny >= BOARD_SIZE) continue;
            boolean hasShip = shipPlacement.getBoard().getBoard()[nx][ny].isHasShip();
            if (!hasShip) {
                boardPanel.setCellState(ny, nx, hover ? CellState.HOVER : CellState.NORMAL);
            }
        }
    }

    private void updateBoardView() {
        Board logicBoard = shipPlacement.getBoard();
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                if (logicBoard.getBoard()[x][y].isHasShip()) {
                    boardPanel.setCellState(y, x, CellState.SHIP);
                } else {
                    boardPanel.setCellState(y, x, CellState.NORMAL);
                }
            }
        }
    }
    
    public boolean isAllShipsPlaced() {
        boolean[] placed = shipButtonPlaced;
        for (boolean b : placed) if (!b) return false;
        return true;
    }
    
    public Board getBoard() {
        return shipPlacement.getBoard();
    }
    
    public boolean isShipButtonPlaced(int idx) {
        return shipButtonPlaced[idx];
    }
}