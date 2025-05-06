package com.battleship.controller.challenge;

import com.battleship.enums.AttackType;
import com.battleship.model.logic.ChallengeModeLogic;
import com.battleship.model.ship.Ship;
import com.battleship.model.board.Node;
import com.battleship.view.panels.challenge.ChallengePlayPanel;
import com.battleship.view.panels.challenge.ChallengeInfoAttackPanel;
import com.battleship.view.panels.challenge.ChallengeBoardPanel;
import com.battleship.view.components.buttons.CustomToggleButton;
import com.battleship.view.utils.ViewConstants;
import com.battleship.view.utils.ResourceLoader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ChallengeController {
    private final ChallengeModeLogic gameLogic;
    private final ChallengePlayPanel playPanel;
    private final ChallengeInfoAttackPanel infoPanel;
    private final ChallengeBoardPanel boardPanel;
    private boolean gameOverDialogShown = false;
    private final int cellSize;

    private AttackType selectedAttackType = AttackType.SINGLE;
    private Timer timer;

    // Icon cho hiệu ứng hit/miss
    private final ImageIcon hitIcon;
    private final ImageIcon missIcon;

    public ChallengeController(
            ChallengeModeLogic gameLogic,
            ChallengePlayPanel playPanel,
            ChallengeInfoAttackPanel infoPanel,
            ChallengeBoardPanel boardPanel,
            int cellSize
        ) {
            this.gameLogic = gameLogic;
            this.playPanel = playPanel;
            this.infoPanel = infoPanel;
            this.boardPanel = boardPanel;
            this.cellSize = cellSize;
            this.gameOverDialogShown = false;

        // Load icon hit/miss
        this.hitIcon = new ImageIcon(ResourceLoader.loadImage(ViewConstants.CHALLENGE_CELL_HIT_IMG)
                .getScaledInstance(cellSize, cellSize, java.awt.Image.SCALE_SMOOTH));
        this.missIcon = new ImageIcon(ResourceLoader.loadImage(ViewConstants.CHALLENGE_CELL_MISS_IMG)
                .getScaledInstance(cellSize, cellSize, java.awt.Image.SCALE_SMOOTH));
        
        // DEBUG: Kiểm tra icon có load được không
        System.out.println("DEBUG: hitIcon loaded: " + (hitIcon.getImage() != null));
        System.out.println("DEBUG: missIcon loaded: " + (missIcon.getImage() != null));
        
        initListeners();
        startTimer();
        updateInfoPanel();
    }

    private void initListeners() {
        // Lắng nghe các nút tấn công
        for (AttackType type : AttackType.values()) {
            int idx = type.ordinal();
            CustomToggleButton btn = infoPanel.getAttackButton(idx);
            if (btn != null) {
                btn.addActionListener(e -> selectedAttackType = type);
            }
        }

        // Lắng nghe click trên các ô bàn cờ
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int x = i, y = j;
                boardPanel.getButton(x, y).addActionListener(e -> handleAttack(x, y));
            }
        }
    }

    private void handleAttack(int x, int y) {
        if (gameLogic.isGameOver()) return;
        if (boardPanel.isCellAttacked(x, y)) return; // Không cho bắn lại ô đã bắn

        List<Node> attacked = gameLogic.attack(selectedAttackType, x, y);

        for (Node node : attacked) {
            int r = node.getX(), c = node.getY();
            if (node.isHasShip()) {
                boardPanel.setCellIcon(r, c, hitIcon);
            } else {
                boardPanel.setCellIcon(r, c, missIcon);
            }
            boardPanel.setCellAttacked(r, c, true);
        }

        updateInfoPanel();
        if (gameLogic.isGameOver()) {
            showGameOver();
        }
    }

    private void updateInfoPanel() {
        infoPanel.setShots(gameLogic.getShotsLeft());
        infoPanel.setTime(formatTime(gameLogic.getTimeLeftSeconds()));
        // Cập nhật số đạn đặc biệt
        infoPanel.setAttackCount(
            gameLogic.getAttackCount(AttackType.CROSS),
            gameLogic.getAttackCount(AttackType.RANDOM),
            gameLogic.getAttackCount(AttackType.DIAMOND)
        );
    }

    private void startTimer() {
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameLogic.tickTime();
                updateInfoPanel();
                if (gameLogic.isGameOver()) {
                    timer.stop();
                    showGameOver();
                }
            }
        });
        timer.start();
    }

    private void showGameOver() {
        if (gameOverDialogShown) return; // Đã hiển thị rồi thì không hiển thị lại
        gameOverDialogShown = true;
        if (timer != null) timer.stop();
        String message = gameLogic.isPlayerWin() ? "You win!" : "You lose!";
        JOptionPane.showMessageDialog(playPanel, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
        // Disable bàn cờ hoặc chuyển về menu nếu muốn
    }

    private String formatTime(int seconds) {
        int min = seconds / 60;
        int sec = seconds % 60;
        return String.format("%02d:%02d", min, sec);
    }
    
    public ChallengePlayPanel getPlayPanel() {
        return playPanel;
    }
}