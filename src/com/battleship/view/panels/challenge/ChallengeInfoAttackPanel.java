package com.battleship.view.panels.challenge;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;
import com.battleship.view.components.buttons.CustomButton;
import com.battleship.view.utils.ViewConstants;

public class ChallengeInfoAttackPanel extends JPanel {
	private JLabel timeLabel;
    private JLabel shotsLabel;
    private CustomButton[] attackButtons;

    public ChallengeInfoAttackPanel(Font font, int preferredWidth) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
        setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.WHITE, 3, true),
            "INFO & ATTACK",
            TitledBorder.CENTER,
            TitledBorder.TOP,
            font.deriveFont(Font.BOLD, 18f),
            Color.WHITE
        ));
        setPreferredSize(new Dimension(preferredWidth, 0));

        // Info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
        	    BorderFactory.createTitledBorder(
        	        BorderFactory.createLineBorder(Color.WHITE, 1, true),
        	        "INFO",
        	        TitledBorder.CENTER,
        	        TitledBorder.TOP,
        	        font.deriveFont(Font.BOLD, 15f),
        	        Color.WHITE
        	    ),
        	    BorderFactory.createEmptyBorder(20, 10, 20, 10) // top, left, bottom, right
        	));

        timeLabel = new JLabel("Time Left: 02:00", SwingConstants.CENTER);
        timeLabel.setFont(font.deriveFont(Font.BOLD, 24f));
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        shotsLabel = new JLabel("Shots Left: 30", SwingConstants.CENTER);
        shotsLabel.setFont(font.deriveFont(Font.BOLD, 24f));
        shotsLabel.setForeground(Color.WHITE);
        shotsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        infoPanel.add(Box.createVerticalStrut(18));
        infoPanel.add(timeLabel);
        infoPanel.add(Box.createVerticalStrut(24));
        infoPanel.add(shotsLabel);
        infoPanel.add(Box.createVerticalStrut(18));

     // Attack panel
        JPanel attackPanel = new JPanel(new GridLayout(2, 2, 16, 16));
        attackPanel.setOpaque(false);
        attackPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1, true),
                "ATTACK",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                font.deriveFont(Font.BOLD, 15f),
                Color.WHITE
            ),
            BorderFactory.createEmptyBorder(30, 10, 30, 10) // top, left, bottom, right
        ));

        String[] btnOnImages = ViewConstants.CHALLENGE_ATK_ON_BUTTON_IMAGES;
        String[] btnHoverImages = ViewConstants.CHALLENGE_ATK_HOVER_BUTTON_IMAGES;
        String[] btnPressedImages = ViewConstants.CHALLENGE_ATK_PRESSED_BUTTON_IMAGES;
        int btnWidth = 192, btnHeight = 96;

        attackButtons = new CustomButton[4];
        for (int i = 0; i < 4; i++) {
            attackButtons[i] = new CustomButton(
                btnOnImages[i], btnHoverImages[i], btnPressedImages[i], btnWidth, btnHeight
            );
            attackPanel.add(attackButtons[i]);
        }

        add(Box.createVerticalStrut(20));
        add(infoPanel);
        add(Box.createVerticalStrut(30));
        add(attackPanel);
        add(Box.createVerticalGlue());
    }

    public void setTime(String time) {
        timeLabel.setText("Time Left: " + time);
    }

    public void setShots(int shots) {
        shotsLabel.setText("Shots Left: " + shots);
    }

    public CustomButton getAttackButton(int idx) {
        return attackButtons[idx];
    }
}
