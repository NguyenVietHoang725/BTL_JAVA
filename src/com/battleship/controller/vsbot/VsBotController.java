package com.battleship.controller.vsbot;

import com.battleship.model.player.Player;
import com.battleship.model.player.Bot;
import com.battleship.model.board.Node;
import com.battleship.view.panels.vsbot.play.VsBotPlayPanel;
import com.battleship.controller.AppController;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VsBotController {
    private Player player;
    private Bot bot;
    private VsBotPlayPanel playPanel;
    private AppController appController;
    private boolean isPlayerTurn = true; // Người chơi đi trước
    private boolean gameEnded = false;

    public VsBotController(Player player, Bot bot, VsBotPlayPanel playPanel, AppController appController) {
        this.player = player;
        this.bot = bot;
        this.playPanel = playPanel;
        this.appController = appController;

        initGame();
    }

    private void initGame() {
        updateTurnLabel();
        // Thay vì add MouseListener cho panel, add ActionListener cho từng ô:
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int x = i, y = j;
                playPanel.getBotBoardPanel().getButton(x, y).addActionListener(e -> {
                    if (isPlayerTurn && !gameEnded) {
                        playerAttack(x, y);
                    }
                });
            }
        }
    }

    private void playerAttack(int x, int y) {
        Node node = bot.getBoard().getNode(x, y);
        if (node.isHit()) {
            JOptionPane.showMessageDialog(playPanel, "This cell has already been attacked!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        node.setHit(true);
        playPanel.getBotBoardPanel().repaint();

        if (bot.getBoard().allShipsSunk()) {
            gameEnded = true;
            updateStatusLabel("You win!");
            showGameOverDialog("Congratulations! You win!");
            return;
        }

        isPlayerTurn = false;
        updateTurnLabel();
        botTurn();
    }

    private void botTurn() {
        Timer timer = new Timer(700, e -> {
            int[] move = bot.chooseAttack(player.getBoard());
            int x = move[0], y = move[1];
            if (x < 0 || y < 0) {
                gameEnded = true;
                updateStatusLabel("Draw!");
                showGameOverDialog("Draw!");
                return;
            }
            Node node = player.getBoard().getNode(x, y);
            node.setHit(true);
            playPanel.getPlayerBoardPanel().repaint();

            if (player.getBoard().allShipsSunk()) {
                gameEnded = true;
                updateStatusLabel("Bot wins!");
                showGameOverDialog("You lose! Bot wins!");
                return;
            }

            isPlayerTurn = true;
            updateTurnLabel();
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
        JPanel panel = new JPanel();
        panel.add(new JLabel(message));
        JButton replayBtn = new JButton("Replay");
        JButton menuBtn = new JButton("Main Menu");
        panel.add(replayBtn);
        panel.add(menuBtn);

        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(playPanel), "Game Over", true);
        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(playPanel);

        replayBtn.addActionListener(e -> {
            dialog.dispose();
            appController.replayVsBotMode();
        });
        menuBtn.addActionListener(e -> {
            dialog.dispose();
            appController.showMenu();
        });

        dialog.setVisible(true);
    }
}