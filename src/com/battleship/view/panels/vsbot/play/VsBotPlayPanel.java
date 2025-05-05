/*
 * tốt rồi, tôi thấy giao diện ổn rồi, bây giờ tôi muốn đổi qua làm giao diện cho chế độ vsbot. ở chế độ này có 2 giao diện, một giao diện đặt tàu và chọn độ khó, một giao diện khi chơi. Vậy nên tôi sẽ muốn làm tiếp giao diện khi chơi để cho tương tự với giao diện challenge trước đó. 
 * 
 */

//package com.battleship.view.panels.vsbot.play;
//
//import javax.swing.*;
//
//import com.battleship.view.components.board.GameBoardPanel;
//
//import java.awt.*;
//
//public class VsBotPlayPanel extends JPanel {
//	public VsBotPlayPanel(Font font) {
//        setLayout(new BorderLayout());
//        setOpaque(false);
//
//        // Panel chứa 2 bảng chơi
//        JPanel boardsPanel = new JPanel();
//        boardsPanel.setLayout(new BoxLayout(boardsPanel, BoxLayout.X_AXIS));
//        boardsPanel.setOpaque(false);
//
//        // Bảng người chơi
//        JPanel playerPanel = new JPanel(new BorderLayout());
//        playerPanel.setOpaque(false);
//        JLabel playerLabel = new JLabel("Player", SwingConstants.CENTER);
//        playerLabel.setFont(font.deriveFont(Font.BOLD, 16f));
//        playerPanel.add(playerLabel, BorderLayout.NORTH);
//        playerPanel.add(new GameBoardPanel(), BorderLayout.CENTER);
//
//        // Bảng challenge
//        JPanel challengePanel = new JPanel(new BorderLayout());
//        challengePanel.setOpaque(false);
//        JLabel challengeLabel = new JLabel("Challenge", SwingConstants.CENTER);
//        challengeLabel.setFont(font.deriveFont(Font.BOLD, 16f));
//        challengePanel.add(challengeLabel, BorderLayout.NORTH);
//        challengePanel.add(new GameBoardPanel(), BorderLayout.CENTER);
//
//        boardsPanel.add(Box.createHorizontalGlue());
//        boardsPanel.add(playerPanel);
//        boardsPanel.add(Box.createRigidArea(new Dimension(40, 0))); // khoảng cách giữa 2 bảng
//        boardsPanel.add(challengePanel);
//        boardsPanel.add(Box.createHorizontalGlue());
//
//        add(boardsPanel, BorderLayout.CENTER);
//
//        // Panel chứa 4 button tấn công
//        JPanel attackPanel = new JPanel();
//        attackPanel.setLayout(new BoxLayout(attackPanel, BoxLayout.X_AXIS));
//        attackPanel.setOpaque(false);
//
//        String[] attackNames = {"Normal Attack", "Missile", "Bomb", "Special"};
//        for (String name : attackNames) {
//            JButton btn = new JButton(name);
//            btn.setFont(font.deriveFont(15f));
//            btn.setAlignmentY(Component.CENTER_ALIGNMENT);
//            btn.setFocusable(false);
//            btn.setPreferredSize(new Dimension(120, 36));
//            btn.setMaximumSize(new Dimension(120, 36));
//            btn.setMinimumSize(new Dimension(120, 36));
//            btn.setToolTipText("Attack type: " + name);
//            attackPanel.add(btn);
//            attackPanel.add(Box.createRigidArea(new Dimension(20, 0)));
//        }
//
//        attackPanel.add(Box.createHorizontalGlue());
//        add(attackPanel, BorderLayout.SOUTH);
//    }
//}
