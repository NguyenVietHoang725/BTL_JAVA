package com.battleship.view.components.dialog;

import com.battleship.utils.ResourceLoader;
import com.battleship.utils.ViewConstants;
import com.battleship.view.components.buttons.CustomButton;
import com.battleship.view.components.common.ImageBackgroundPanel;

import javax.swing.*;
import java.awt.*;

public class RulesDialog {
    public static void showDialog(JFrame parentFrame) {
        int dialogWidth = 900;  // Tăng kích thước
        int dialogHeight = 700;

        // Dialog setup
        JDialog dialog = new JDialog(parentFrame, true);
        dialog.setUndecorated(true);
        dialog.setSize(dialogWidth, dialogHeight);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setLayout(null);

        // LayeredPane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        layeredPane.setBounds(0, 0, dialogWidth, dialogHeight);

        // Background
        ImageBackgroundPanel background = new ImageBackgroundPanel(ViewConstants.SETTING_DIALOG_BG);
        background.setBounds(0, 0, dialogWidth, dialogHeight);
        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);

        // Title
//        Font titleFont = ResourceLoader.loadFont(ViewConstants.FONT_PATH, 36f);
//        JLabel titleLabel = new JLabel("Game Rules", SwingConstants.CENTER);
//        titleLabel.setFont(titleFont);
//        titleLabel.setForeground(Color.WHITE);
//        titleLabel.setBounds(0, 20, dialogWidth, 40);
//        layeredPane.add(titleLabel, JLayeredPane.PALETTE_LAYER);

        // Content Panel with GridBagLayout
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Use system default font for content
        Font contentFont = new Font("Segoe UI", Font.PLAIN, 14);
        String[] rules = {
        	    "1. Mục tiêu",
        	    "Phá huỷ toàn bộ tàu địch.",
        	    "",
        	    "2. Chế độ chơi",
        	    "Chơi một mình: Bạn bắn vào bản đồ có tàu được đặt ngẫu nhiên.",
        	    "",
        	    "Chơi với bot: Bạn và bot thay phiên nhau bắn. Ai phá huỷ hết tàu của đối phương trước là người thắng.",
        	    "",
        	    "3. Cách chơi",
        	    "Mỗi lượt, bạn chọn một ô trên bản đồ đối phương để bắn.",
        	    "",
        	    "Hit: bắn trúng tàu.",
        	    "Miss: bắn trượt.",
        	    "",
        	    "Trong chế độ với bot, bot cũng sẽ bắn lại sau lượt của bạn.",
        	    "",
        	    "4. Điều kiện thắng – thua",
        	    "Thắng: Khi bạn phá huỷ toàn bộ tàu của đối thủ.",
        	    "",
        	    "Thua:",
        	    "Hết số lượt bắn cho phép (chơi một mình).",
        	    "Hết thời gian (chơi một mình).",
        	    "Bị bot phá huỷ hết tàu (chơi với bot)."
        	};


        for (String rule : rules) {
            JLabel label = new JLabel(rule);
            label.setFont(contentFont);
            label.setForeground(Color.WHITE);
            contentPanel.add(label, gbc);
            gbc.gridy++;
        }

        // Scroll Pane with fixed size
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBounds(50, 80, dialogWidth - 100, dialogHeight - 180); // Tăng padding
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        layeredPane.add(scrollPane, JLayeredPane.PALETTE_LAYER);

        // Close button
        CustomButton closeBtn = new CustomButton(
            ViewConstants.BTN_APPLY_NORMAL,
            ViewConstants.BTN_APPLY_HOVER,
            ViewConstants.BTN_APPLY_PRESSED,
            120, 40
        );
//        closeBtn.setText("Close");
//        closeBtn.setFont(ResourceLoader.loadFont(ViewConstants.FONT_PATH, 16f));
        closeBtn.setBounds((dialogWidth - 120) / 2, dialogHeight - 60, 120, 40);
        closeBtn.addActionListener(e -> dialog.dispose());
        layeredPane.add(closeBtn, JLayeredPane.MODAL_LAYER);

        dialog.setContentPane(layeredPane);
        dialog.setVisible(true);
    }
}