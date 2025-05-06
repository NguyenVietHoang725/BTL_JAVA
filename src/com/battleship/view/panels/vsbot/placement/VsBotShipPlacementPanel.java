package com.battleship.view.panels.vsbot.placement;

import javax.swing.*;

import com.battleship.model.board.Board;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VsBotShipPlacementPanel extends JPanel {
    private ShipPlacementBoardPanel boardPanel;
    private JButton rotateButton, confirmButton, resetButton;
    private JLabel infoLabel;
    private JComboBox<String> difficultyBox;

    public VsBotShipPlacementPanel(Font font, int cellSize) {
        setLayout(new BorderLayout(24, 0));
        setOpaque(false);

        // Bảng đặt tàu
        boardPanel = new ShipPlacementBoardPanel(font, cellSize);

        // Panel chức năng bên phải
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setOpaque(false);

        infoLabel = new JLabel("Chọn tàu và đặt lên bảng");
        infoLabel.setFont(font.deriveFont(Font.BOLD, 16f));
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        rotateButton = new JButton("Xoay tàu");
        confirmButton = new JButton("Xác nhận");
        resetButton = new JButton("Đặt lại");

        rotateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        difficultyBox = new JComboBox<>(new String[] { "Dễ", "Trung bình", "Khó" });
        difficultyBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(Box.createVerticalStrut(16));
        JLabel diffLabel = new JLabel("Chọn độ khó:");
        diffLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(diffLabel);

        rightPanel.add(Box.createVerticalStrut(8));

        difficultyBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(difficultyBox);
        rightPanel.add(difficultyBox);
        
        rightPanel.add(Box.createVerticalStrut(32));
        rightPanel.add(infoLabel);
        rightPanel.add(Box.createVerticalStrut(32));
        rightPanel.add(rotateButton);
        rightPanel.add(Box.createVerticalStrut(16));
        rightPanel.add(resetButton);
        rightPanel.add(Box.createVerticalStrut(16));
        rightPanel.add(confirmButton);

        add(boardPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        // Sự kiện
        rotateButton.addActionListener(e -> boardPanel.toggleOrientation());
        resetButton.addActionListener(e -> boardPanel.resetBoard());
        confirmButton.addActionListener(e -> {
            if (boardPanel.isAllShipsPlaced()) {
                // TODO: Lấy dữ liệu board và chuyển sang màn chơi
                // Board playerBoard = boardPanel.getBoard();
                // callback.onPlacementDone(playerBoard);
            } else {
                JOptionPane.showMessageDialog(this, "Bạn chưa đặt đủ tất cả các tàu!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });
    }
    
    public boolean confirm() {
        if (boardPanel.isAllShipsPlaced()) {
            return true;
        } else {
            JOptionPane.showMessageDialog(this, "Bạn chưa đặt đủ tất cả các tàu!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }

    // Có thể thêm getter cho boardPanel nếu cần
    public ShipPlacementBoardPanel getBoardPanel() { return boardPanel; }
    
    public String getSelectedDifficulty() {
        return (String) difficultyBox.getSelectedItem();
    }

	public JButton getConfirmButton() {
		return confirmButton;
	}

	public void setConfirmButton(JButton confirmButton) {
		this.confirmButton = confirmButton;
	}

	public void setBoardPanel(ShipPlacementBoardPanel boardPanel) {
		this.boardPanel = boardPanel;
	}
    
	public Board getBoard() {
        return boardPanel.getBoard();
    }
}