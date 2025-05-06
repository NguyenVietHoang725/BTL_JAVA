package com.battleship.view.panels.vsbot.placement;

import javax.swing.*;
import java.awt.*;

public class VsBotPlacementInfoPanel extends JPanel {
    private JToggleButton easyBtn, mediumBtn, hardBtn;
    private ButtonGroup difficultyGroup;
    private JLabel infoLabel;
    private JButton rotateButton, confirmButton, resetButton;

    public VsBotPlacementInfoPanel(Font font) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);

        JLabel diffLabel = new JLabel("Select difficulty:");
        diffLabel.setFont(font.deriveFont(Font.BOLD, 16f));
        diffLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        easyBtn = new JToggleButton(new ImageIcon(getClass().getResource("/images/buttons/vsbot/easy_button.png")));
        mediumBtn = new JToggleButton(new ImageIcon(getClass().getResource("/images/buttons/vsbot/medium_button.png")));
        hardBtn = new JToggleButton(new ImageIcon(getClass().getResource("/images/buttons/vsbot/hard_button.png")));
        easyBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        mediumBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        hardBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        difficultyGroup = new ButtonGroup();
        difficultyGroup.add(easyBtn);
        difficultyGroup.add(mediumBtn);
        difficultyGroup.add(hardBtn);
        easyBtn.setSelected(true);

        infoLabel = new JLabel("Select a ship and place it on the board");
        infoLabel.setFont(font.deriveFont(Font.BOLD, 16f));
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        rotateButton = new JButton("Rotate ship");
        confirmButton = new JButton("Confirm");
        resetButton = new JButton("Reset");
        rotateButton.setFont(font.deriveFont(Font.BOLD, 14f));
        confirmButton.setFont(font.deriveFont(Font.BOLD, 14f));
        resetButton.setFont(font.deriveFont(Font.BOLD, 14f));
        rotateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalStrut(16));
        add(diffLabel);
        add(Box.createVerticalStrut(8));
        add(easyBtn);
        add(Box.createVerticalStrut(8));
        add(mediumBtn);
        add(Box.createVerticalStrut(8));
        add(hardBtn);
        add(Box.createVerticalStrut(24));
        add(infoLabel);
        add(Box.createVerticalStrut(24));
        add(rotateButton);
        add(Box.createVerticalStrut(16));
        add(resetButton);
        add(Box.createVerticalStrut(16));
        add(confirmButton);
    }

    public String getSelectedDifficulty() {
        if (easyBtn.isSelected()) return "Easy";
        if (mediumBtn.isSelected()) return "Medium";
        return "Hard";
    }
    public JButton getRotateButton() { return rotateButton; }
    public JButton getConfirmButton() { return confirmButton; }
    public JButton getResetButton() { return resetButton; }
    public JLabel getInfoLabel() { return infoLabel; }
}