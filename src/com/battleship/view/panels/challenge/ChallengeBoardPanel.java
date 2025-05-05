package com.battleship.view.panels.challenge;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import com.battleship.view.utils.ViewConstants;
import com.battleship.view.utils.ResourceLoader;

public class ChallengeBoardPanel extends JPanel {
    private JButton[][] boardButtons;

    public ChallengeBoardPanel(Font font, int cellSize) {
        setLayout(new BorderLayout());
        setOpaque(false);

        // Border ngoài cùng với padding bên trong
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE, 3, true),
                "CHALLENGE BOARD",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                font.deriveFont(Font.BOLD, 18f),
                Color.WHITE
            ),
            BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        add(Box.createVerticalStrut(10), BorderLayout.NORTH);

        JPanel boardPanel = new JPanel(new GridLayout(11, 11, 0, 0));
        boardPanel.setOpaque(false);

        boardButtons = new JButton[10][10];

        // Chọn màu vàng nhạt cho header
        Color headerColor = new Color(255, 215, 0); // Gold

        ImageIcon normalIcon = new ImageIcon(
            ResourceLoader.loadImage(ViewConstants.CHALLENGE_CELL_NORMAL_IMG)
                .getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH)
        );

        for (int row = 0; row <= 10; row++) {
            for (int col = 0; col <= 10; col++) {
                if (row == 0 && col == 0) {
                    boardPanel.add(new JLabel(""));
                } else if (row == 0) {
                    // Header cột (A-J)
                    JLabel label = new JLabel(String.valueOf((char) ('A' + col - 1)), SwingConstants.CENTER);
                    label.setFont(font.deriveFont(Font.BOLD, 22f)); // chữ to hơn
                    label.setForeground(headerColor);
                    label.setPreferredSize(new Dimension(cellSize, cellSize));
                    // Border: top dày, left dày nếu là cột 1, phải dày nếu là cột 10, bottom chỉ 1px
                    int top = 2;
                    int left = (col == 1) ? 2 : 1;
                    int bottom = 1;
                    int right = (col == 10) ? 2 : 1;
                    label.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.WHITE));
                    boardPanel.add(label);
                } else if (col == 0) {
                    // Header hàng (1-10)
                    JLabel label = new JLabel(String.valueOf(row), SwingConstants.CENTER);
                    label.setFont(font.deriveFont(Font.BOLD, 22f)); // chữ to hơn
                    label.setForeground(headerColor);
                    label.setPreferredSize(new Dimension(cellSize, cellSize));
                    // Border: left dày, top dày nếu là hàng 1, bottom dày nếu là hàng 10, phải chỉ 1px
                    int top = (row == 1) ? 2 : 1;
                    int left = 2;
                    int bottom = (row == 10) ? 2 : 1;
                    int right = 1;
                    label.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.WHITE));
                    boardPanel.add(label);
                } else {
                    // Ô bàn cờ
                    JButton btn = new JButton();
                    btn.setPreferredSize(new Dimension(cellSize, cellSize));
                    btn.setIcon(normalIcon);
                    btn.setContentAreaFilled(false);
                    btn.setFocusPainted(false);

                    // Border cho ô bàn cờ
                    // Nếu là hàng đầu tiên (ngay dưới header), top chỉ 1px (header đã dày)
                    // Nếu là cột đầu tiên (ngay phải header), left chỉ 1px (header đã dày)
                    int top = (row == 1) ? 1 : 1;
                    int left = (col == 1) ? 1 : 1;
                    int bottom = (row == 10) ? 2 : 1;
                    int right = (col == 10) ? 2 : 1;
                    btn.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.WHITE));

                    boardButtons[row - 1][col - 1] = btn;
                    boardPanel.add(btn);
                }
            }
        }

        int boardSize = cellSize * 11;
        boardPanel.setPreferredSize(new Dimension(boardSize, boardSize));

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);
        wrapper.add(boardPanel);

        add(wrapper, BorderLayout.CENTER);
    }

    public JButton getButton(int row, int col) {
        return boardButtons[row][col];
    }
}