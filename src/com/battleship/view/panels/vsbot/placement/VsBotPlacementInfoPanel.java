package com.battleship.view.panels.vsbot.placement;

import com.battleship.view.components.buttons.CustomButton;
import com.battleship.view.components.buttons.CustomToggleButton;
import com.battleship.view.utils.ViewConstants;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class VsBotPlacementInfoPanel extends JPanel {
    private CustomToggleButton easyBtn, mediumBtn, hardBtn;
    private ButtonGroup difficultyGroup;
    private JLabel infoLabel;
    private CustomButton rotateButton, confirmButton, resetButton, undoButton, redoButton;
    private JToggleButton[] shipButtons;

    public VsBotPlacementInfoPanel(Font font) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);

        // Border ngoài cùng
        Border outerBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE, 3, true),
                "INFO",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                font.deriveFont(Font.BOLD, 18f),
                Color.WHITE
            ),
            BorderFactory.createEmptyBorder(16, 16, 16, 16)
        );
        setBorder(outerBorder);

        // ==== PHẦN 1: CHỌN ĐỘ KHÓ ====
        JPanel difficultyPanel = new JPanel();
        difficultyPanel.setOpaque(false);
        difficultyPanel.setLayout(new BoxLayout(difficultyPanel, BoxLayout.X_AXIS));
        difficultyPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.WHITE, 2, true),
            "Difficulty", TitledBorder.CENTER, TitledBorder.TOP, font.deriveFont(Font.BOLD, 14f), Color.WHITE
        ));

        easyBtn = new CustomToggleButton(
            ViewConstants.VSBOT_EASY_BUTTON,
            ViewConstants.VSBOT_EASY_HOVER_BUTTON,
            ViewConstants.VSBOT_EASY_PRESSED_BUTTON,
            96, 24
        );
        mediumBtn = new CustomToggleButton(
            ViewConstants.VSBOT_MEDIUM_BUTTON,
            ViewConstants.VSBOT_MEDIUM_HOVER_BUTTON,
            ViewConstants.VSBOT_MEDIUM_PRESSED_BUTTON,
            96, 24
        );
        hardBtn = new CustomToggleButton(
            ViewConstants.VSBOT_HARD_BUTTON,
            ViewConstants.VSBOT_HARD_HOVER_BUTTON,
            ViewConstants.VSBOT_HARD_PRESSED_BUTTON,
            96, 24
        );

        difficultyGroup = new ButtonGroup();
        difficultyGroup.add(easyBtn);
        difficultyGroup.add(mediumBtn);
        difficultyGroup.add(hardBtn);
        easyBtn.setSelected(true);

        difficultyPanel.add(Box.createHorizontalGlue());
        difficultyPanel.add(easyBtn);
        difficultyPanel.add(Box.createHorizontalStrut(8));
        difficultyPanel.add(mediumBtn);
        difficultyPanel.add(Box.createHorizontalStrut(8));
        difficultyPanel.add(hardBtn);
        difficultyPanel.add(Box.createHorizontalGlue());

        // ==== PHẦN 2: CHỌN TÀU ====
        JPanel shipPanel = new JPanel();
        shipPanel.setOpaque(false);
        shipPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 8));
        shipPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.WHITE, 2, true),
            "Select Ship", TitledBorder.CENTER, TitledBorder.TOP, font.deriveFont(Font.BOLD, 14f), Color.WHITE
        ));

        int[] shipSizes = {2, 3, 3, 4, 5};
        shipButtons = new JToggleButton[5];
        ButtonGroup shipGroup = new ButtonGroup();
        for (int i = 0; i < 5; i++) {
            shipButtons[i] = new JToggleButton(String.valueOf(shipSizes[i]));
            shipButtons[i].setFont(font.deriveFont(Font.BOLD, 22f));
            shipButtons[i].setPreferredSize(new Dimension(48, 36));
            shipPanel.add(shipButtons[i]);
            shipGroup.add(shipButtons[i]);
        }

        // ==== PHẦN 3: CÁC NÚT CHỨC NĂNG ====
        JPanel actionPanel = new JPanel();
        actionPanel.setOpaque(false);
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));
        actionPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.WHITE, 2, true),
            "Actions", TitledBorder.CENTER, TitledBorder.TOP, font.deriveFont(Font.BOLD, 14f), Color.WHITE
        ));

        // Undo/Redo trên cùng một hàng
        JPanel undoRedoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        undoRedoPanel.setOpaque(false);
        undoButton = new CustomButton(
            ViewConstants.VSBOT_UNDO_BUTTON,
            ViewConstants.VSBOT_UNDO_HOVER_BUTTON,
            ViewConstants.VSBOT_UNDO_PRESSED_BUTTON,
            72, 24
        );
        redoButton = new CustomButton(
            ViewConstants.VSBOT_REDO_BUTTON,
            ViewConstants.VSBOT_REDO_HOVER_BUTTON,
            ViewConstants.VSBOT_REDO_PRESSED_BUTTON,
            72, 24
        );
        undoRedoPanel.add(undoButton);
        undoRedoPanel.add(redoButton);

        rotateButton = new CustomButton(
            ViewConstants.VSBOT_ROTATE_BUTTON,
            ViewConstants.VSBOT_ROTATE_HOVER_BUTTON,
            ViewConstants.VSBOT_ROTATE_PRESSED_BUTTON,
            96, 24
        );
        resetButton = new CustomButton(
            ViewConstants.VSBOT_RESET_BUTTON,
            ViewConstants.VSBOT_RESET_HOVER_BUTTON,
            ViewConstants.VSBOT_RESET_PRESSED_BUTTON,
            96, 24
        );
        confirmButton = new CustomButton(
            ViewConstants.VSBOT_CONFIRM_BUTTON,
            ViewConstants.VSBOT_CONFIRM_HOVER_BUTTON,
            ViewConstants.VSBOT_CONFIRM_PRESSED_BUTTON,
            96, 24
        );

        actionPanel.add(Box.createVerticalStrut(8));
        actionPanel.add(undoRedoPanel);
        actionPanel.add(Box.createVerticalStrut(16));
        actionPanel.add(rotateButton);
        actionPanel.add(Box.createVerticalStrut(16));
        actionPanel.add(resetButton);
        actionPanel.add(Box.createVerticalStrut(16));
        actionPanel.add(confirmButton);
        actionPanel.add(Box.createVerticalStrut(8));

        // ==== LABEL HƯỚNG DẪN ====
        infoLabel = new JLabel("Select a ship and place it on the board");
        infoLabel.setFont(font.deriveFont(Font.BOLD, 16f));
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ==== THÊM VÀO PANEL CHÍNH ====
        add(Box.createVerticalStrut(8));
        add(difficultyPanel);
        add(Box.createVerticalStrut(16));
        add(shipPanel);
        add(Box.createVerticalStrut(16));
        add(actionPanel);
        add(Box.createVerticalStrut(16));
        add(infoLabel);
        add(Box.createVerticalGlue());
    }

    public String getSelectedDifficulty() {
        if (easyBtn.isSelected()) return "Easy";
        if (mediumBtn.isSelected()) return "Medium";
        return "Hard";
    }
    public JButton getRotateButton() { return rotateButton; }
    public JButton getConfirmButton() { return confirmButton; }
    public JButton getResetButton() { return resetButton; }
    public JButton getUndoButton() { return undoButton; }
    public JButton getRedoButton() { return redoButton; }
    public JLabel getInfoLabel() { return infoLabel; }
    public JToggleButton[] getShipButtons() { return shipButtons; }
}