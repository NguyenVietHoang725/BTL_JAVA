package com.battleship.view.panels.vsbot.play;

import com.battleship.utils.ViewConstants;
import com.battleship.view.components.buttons.CustomToggleButton;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class VsBotInfoAttackPanel extends JPanel {
    private JLabel turnLabel;
    private JLabel statusLabel;
    private JLabel[] attackLabels;
    private CustomToggleButton[] attackButtons;

    public VsBotInfoAttackPanel(Font font) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setOpaque(false);

        // Info panel (bên trái)
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        infoPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.WHITE, 1, true),
            "INFO",
            TitledBorder.CENTER,
            TitledBorder.TOP,
            font.deriveFont(Font.BOLD, 15f),
            Color.WHITE
        ));

        turnLabel = new JLabel("Your Turn");
        turnLabel.setFont(font.deriveFont(Font.BOLD, 22f));
        turnLabel.setForeground(Color.WHITE);
        turnLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        statusLabel = new JLabel("Status: Playing");
        statusLabel.setFont(font.deriveFont(Font.BOLD, 18f));
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        infoPanel.add(Box.createVerticalStrut(18));
        infoPanel.add(turnLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(statusLabel);
        infoPanel.add(Box.createVerticalStrut(18));

        // Attack panel (bên phải, các nút ngang hàng)
        JPanel attackPanel = new JPanel();
        attackPanel.setLayout(new BoxLayout(attackPanel, BoxLayout.X_AXIS));
        attackPanel.setOpaque(false);
        attackPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.WHITE, 1, true),
            "ATTACK",
            TitledBorder.CENTER,
            TitledBorder.TOP,
            font.deriveFont(Font.BOLD, 15f),
            Color.WHITE
        ));

        String[] attackNames = {"Single", "Cross", "Random", "Diamond"};
        String[] btnOnImages = ViewConstants.CHALLENGE_ATK_BUTTON_IMAGES;
        String[] btnHoverImages = ViewConstants.CHALLENGE_ATK_HOVER_BUTTON_IMAGES;
        String[] btnPressedImages = ViewConstants.CHALLENGE_ATK_PRESSED_BUTTON_IMAGES;
        int btnWidth = 96, btnHeight = 48;

        attackLabels = new JLabel[4];
        attackButtons = new CustomToggleButton[4];
        ButtonGroup attackButtonGroup = new ButtonGroup();

        for (int i = 0; i < 4; i++) {
            JPanel atkPanel = new JPanel();
            atkPanel.setLayout(new BoxLayout(atkPanel, BoxLayout.Y_AXIS));
            atkPanel.setOpaque(false);

            String labelText = attackNames[i];
            if (i > 0) labelText += ": 0";
            JLabel atkLabel = new JLabel(labelText);
            atkLabel.setFont(font.deriveFont(Font.BOLD, 14f));
            atkLabel.setForeground(Color.WHITE);
            atkLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            attackLabels[i] = atkLabel;

            CustomToggleButton atkBtn = new CustomToggleButton(
                btnOnImages[i], btnHoverImages[i], btnPressedImages[i], btnWidth, btnHeight
            );
            atkBtn.setAlignmentY(Component.CENTER_ALIGNMENT);
            attackButtons[i] = atkBtn;
            attackButtonGroup.add(atkBtn);

            atkPanel.add(atkLabel);
            atkPanel.add(Box.createVerticalStrut(4));
            atkPanel.add(atkBtn);
            attackPanel.add(Box.createHorizontalStrut(12));
            attackPanel.add(atkPanel);
        }
        attackButtons[0].setSelected(true);

        add(Box.createHorizontalStrut(24));
        add(infoPanel);
        add(Box.createHorizontalGlue());
        add(attackPanel);
        add(Box.createHorizontalStrut(24));
    }

    public JLabel getTurnLabel() { return turnLabel; }
    public JLabel getStatusLabel() { return statusLabel; }
    public CustomToggleButton[] getAttackButtons() { return attackButtons; }
    public JLabel[] getAttackLabels() { return attackLabels; }
}