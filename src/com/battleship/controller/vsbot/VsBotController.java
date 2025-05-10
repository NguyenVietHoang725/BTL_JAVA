package com.battleship.controller.vsbot;

import com.battleship.model.player.Player;
import com.battleship.utils.ViewConstants;
import com.battleship.model.player.Bot;
import com.battleship.model.board.Board;
import com.battleship.model.board.Node;
import com.battleship.model.loader.BotBoardLoader;
import com.battleship.view.components.dialog.GameOverDialog;
import com.battleship.view.components.dialog.PauseDialog;
import com.battleship.view.components.dialog.SettingsDialog;
import com.battleship.view.panels.vsbot.play.VsBotPlayPanel;
import com.battleship.enums.CellState;
import com.battleship.controller.AppController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

public class VsBotController {
    private Player player;
    private Bot bot;
    private VsBotPlayPanel playPanel;
    private AppController appController;
    private boolean isPlayerTurn = true;
    private boolean gameEnded = false;

    public VsBotController(Player player, Bot bot, VsBotPlayPanel playPanel, AppController appController) {
        this.player = player;
        this.bot = bot;
        this.playPanel = playPanel;
        this.appController = appController;
        setupPauseKey();

        initGame();
    }

    private void initGame() {
        // Cập nhật bảng người chơi với tàu đã đặt
        playPanel.getPlayerBoardPanel().updatePlayerBoard(player.getBoard());
        
        // Load bảng cho bot - Sửa lại cách gọi loadBoard
        BotBoardLoader loader = new BotBoardLoader();
        String[] files = ViewConstants.VSBOT_FILE_PATHS;
        String file = files[new java.util.Random().nextInt(files.length)];
        try {
            String filePath = getClass().getResource(file).getPath();
            Board botBoard = loader.loadBoard(filePath);
            bot.setBoard(botBoard); // Set board mới cho bot
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        updateTurnLabel();
        updateStatusLabel("Playing");
        
        // Gắn sự kiện click cho bảng bot
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int x = i, y = j;
                playPanel.getBotBoardPanel().getButton(x, y).addActionListener(e -> {
                    System.out.println("[DEBUG] Clicked button: x(row)=" + x + ", y(col)=" + y);
                    if (isPlayerTurn && !gameEnded) {
                        playerAttack(x, y);
                    }
                });
            }
        }
    }

    private void playerAttack(int x, int y) {
    	System.out.println("[DEBUG] playerAttack: x(row)=" + x + ", y(col)=" + y);
        Node node = bot.getBoard().getNode(x, y);
        if (node.isHit()) {
            JOptionPane.showMessageDialog(playPanel, "This cell has already been attacked!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        node.setHit(true);

        playPanel.getBotBoardPanel().setCellState(x, y, node.isHasShip() ? CellState.HIT : CellState.MISS);
        
        // XÓA LISTENER để không hover/click lại và không đổi lại icon
        JButton btn = playPanel.getBotBoardPanel().getButton(x, y);
        for (MouseListener ml : btn.getMouseListeners()) {
            btn.removeMouseListener(ml);
        }
        for (ActionListener al : btn.getActionListeners()) {
            btn.removeActionListener(al);
        }
        
        if (node.isHasShip()) {
            if (bot.getBoard().isShipSunkAt(x, y)) {
                updateStatusLabel("You sunk a ship! Bot's turn.");
                if (bot.getBoard().allShipsSunk()) {
                    gameEnded = true;
                    updateStatusLabel("You win!");
                    showGameOverDialog("Congratulations! You win!");
                    return;
                }
                isPlayerTurn = false;
                updateTurnLabel();
                botTurn();
            } else {
                updateStatusLabel("Hit! Shoot again!");
            }
        } else {
            updateStatusLabel("Miss! Bot's turn.");
            isPlayerTurn = false;
            updateTurnLabel();
            botTurn();
        }
    }

    private void botTurn() {
        Timer timer = new Timer(700, e -> {
            int[] move = bot.chooseAttack(player.getBoard());
            int x = move[0], y = move[1];
            
            Node node = player.getBoard().getNode(x, y);
            node.setHit(true);
            
            if (node.isHasShip()) {
                playPanel.getPlayerBoardPanel().setCellState(x, y, CellState.HIT);
                if (player.getBoard().isShipSunkAt(x, y)) {
                    updateStatusLabel("Bot sunk your ship! Your turn.");
                    if (player.getBoard().allShipsSunk()) {
                        gameEnded = true;
                        updateStatusLabel("Bot wins!");
                        showGameOverDialog("You lose! Bot wins!");
                        return;
                    }
                    isPlayerTurn = true;
                    updateTurnLabel();
                } else {
                    updateStatusLabel("Bot hit! Bot shoots again!");
                    botTurn();
                }
            } else {
                playPanel.getPlayerBoardPanel().setCellState(x, y, CellState.MISS);
                updateStatusLabel("Bot missed! Your turn.");
                isPlayerTurn = true;
                updateTurnLabel();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void updateTurnLabel() {
        if (isPlayerTurn) {
            playPanel.getInfoAttackPanel().getTurnLabel().setText("Your Turn");
        } else {
            playPanel.getInfoAttackPanel().getTurnLabel().setText("Bot's Turn");
        }
    }

    private void updateStatusLabel(String status) {
        playPanel.getInfoAttackPanel().getStatusLabel().setText("Status: " + status);
    }

    private void showGameOverDialog(String message) {
        GameOverDialog.showDialog(
            (JFrame) SwingUtilities.getWindowAncestor(playPanel),
            message,
            () -> appController.replayVsBotMode(),
            () -> appController.showMenu()
        );
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
                JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(playPanel);
                SettingsDialog.showDialog(parent);
                showPauseDialog();
            },
            // MainMenu
            () -> appController.showMenu()
        );
    }


}