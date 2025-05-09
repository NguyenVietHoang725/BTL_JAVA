package com.battleship.controller.challenge;

import com.battleship.controller.AppController;
import com.battleship.enums.AttackType;
import com.battleship.enums.CellState;
import com.battleship.model.logic.ChallengeModeLogic;
import com.battleship.model.ship.Ship;
import com.battleship.utils.ResourceLoader;
import com.battleship.utils.ViewConstants;
import com.battleship.model.board.Node;
import com.battleship.view.panels.challenge.ChallengePlayPanel;
import com.battleship.view.panels.challenge.ChallengeInfoAttackPanel;
import com.battleship.view.panels.challenge.ChallengeBoardPanel;
import com.battleship.view.components.buttons.CustomToggleButton;
import com.battleship.view.components.dialog.GameOverDialog;
import com.battleship.view.components.dialog.PauseDialog;
import com.battleship.view.components.dialog.SettingsDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.List;

public class ChallengeController {
    private final ChallengeModeLogic gameLogic;
    private final ChallengePlayPanel playPanel;
    private final ChallengeInfoAttackPanel infoPanel;
    private final ChallengeBoardPanel boardPanel;
    private final AppController appController;
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
            AppController appController,
            int cellSize
        ) {
            this.gameLogic = gameLogic;
            this.playPanel = playPanel;
            this.infoPanel = infoPanel;
            this.boardPanel = boardPanel;
			this.appController = appController;
            this.cellSize = cellSize;
            this.gameOverDialogShown = false;
            setupPauseKey();

        // Load icon hit/miss
        this.hitIcon = new ImageIcon(ResourceLoader.loadImage(ViewConstants.CELL_HIT_IMG)
                .getScaledInstance(cellSize, cellSize, java.awt.Image.SCALE_SMOOTH));
        this.missIcon = new ImageIcon(ResourceLoader.loadImage(ViewConstants.CELL_MISS_IMG)
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
        if (!boardPanel.getButton(x, y).isEnabled()) return; // Không cho bắn lại ô đã bắn

        List<Node> attacked = gameLogic.attack(selectedAttackType, x, y);

        for (Node node : attacked) {
            int r = node.getX(), c = node.getY();
            if (node.isHasShip()) {
                boardPanel.setCellState(r, c, CellState.HIT);
            } else {
                boardPanel.setCellState(r, c, CellState.MISS);
            }
            
            // XÓA LISTENER để không hover/click lại và không đổi lại icon
            JButton btn = boardPanel.getButton(r, c);
            for (MouseListener ml : btn.getMouseListeners()) {
                btn.removeMouseListener(ml);
            }
            for (ActionListener al : btn.getActionListeners()) {
                btn.removeActionListener(al);
            }
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
        if (gameOverDialogShown) return;
        gameOverDialogShown = true;
        if (timer != null) timer.stop();

        String message = gameLogic.isPlayerWin() ? "You win!" : "You lose!";

        GameOverDialog.showDialog(
            (JFrame) SwingUtilities.getWindowAncestor(playPanel),
            message,
            () -> appController.startChallengeMode(),
            () -> appController.showMenu()
        );
    }

    private String formatTime(int seconds) {
        int min = seconds / 60;
        int sec = seconds % 60;
        return String.format("%02d:%02d", min, sec);
    }
    
    public ChallengePlayPanel getPlayPanel() {
        return playPanel;
    }
    
    private void setupPauseKey() {
        playPanel.setFocusable(true);
        playPanel.requestFocusInWindow();
        playPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "showPause");
        playPanel.getActionMap().put("showPause", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPauseDialog();
            }
        });
    }

    private void showPauseDialog() {
        PauseDialog.showDialog(
            (JFrame) SwingUtilities.getWindowAncestor(playPanel),
            // Resume
            () -> playPanel.requestFocusInWindow(),
            // Setting
            () -> {
                // Ẩn PauseDialog, show SettingDialog, khi cancel thì show lại PauseDialog
                JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(playPanel);
                SettingsDialog.showDialog(parent);
                // Sau khi đóng SettingDialog, show lại PauseDialog
                showPauseDialog();
            },
            // MainMenu
            () -> appController.showMenu()
        );
    }


}