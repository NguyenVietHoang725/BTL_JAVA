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
        // Lắng nghe sự kiện click trên bảng bot để người chơi bắn
        playPanel.getBotBoardPanel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isPlayerTurn && !gameEnded) {
                    int cellSize = playPanel.getBotBoardPanel().getCellSize();
                    int x = e.getX() / cellSize;
                    int y = e.getY() / cellSize;
                    playerAttack(x, y);
                }
            }
        });
    }

    private void playerAttack(int x, int y) {
        Node node = bot.getBoard().getNode(x, y);
        if (node.isHit()) {
            // Đã bắn rồi, không cho bắn lại
            JOptionPane.showMessageDialog(playPanel, "Ô này đã bắn rồi!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        node.setHit(true);
        playPanel.getBotBoardPanel().repaint();

        // Kiểm tra thắng/thua
        if (bot.getBoard().allShipsSunk()) {
            gameEnded = true;
            updateStatusLabel("Bạn thắng!");
            JOptionPane.showMessageDialog(playPanel, "Chúc mừng! Bạn đã thắng!", "Kết thúc", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Chuyển lượt cho bot
        isPlayerTurn = false;
        updateTurnLabel();
        botTurn();
    }

    private void botTurn() {
        // Để bot "nghĩ" 1 chút cho tự nhiên
        Timer timer = new Timer(700, e -> {
            int[] move = bot.chooseAttack(player.getBoard());
            int x = move[0], y = move[1];
            if (x < 0 || y < 0) {
                // Không còn nước đi
                gameEnded = true;
                updateStatusLabel("Hòa!");
                JOptionPane.showMessageDialog(playPanel, "Hòa!", "Kết thúc", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            Node node = player.getBoard().getNode(x, y);
            node.setHit(true);
            playPanel.getPlayerBoardPanel().repaint();

            // Kiểm tra thắng/thua
            if (player.getBoard().allShipsSunk()) {
                gameEnded = true;
                updateStatusLabel("Bot thắng!");
                JOptionPane.showMessageDialog(playPanel, "Bạn đã thua! Bot thắng!", "Kết thúc", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Chuyển lượt lại cho người chơi
            isPlayerTurn = true;
            updateTurnLabel();
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void updateTurnLabel() {
        if (isPlayerTurn) {
            playPanel.getInfoAttackPanel().getTurnLabel().setText("Lượt của bạn");
        } else {
            playPanel.getInfoAttackPanel().getTurnLabel().setText("Lượt của Bot");
        }
    }

    private void updateStatusLabel(String status) {
        playPanel.getInfoAttackPanel().getStatusLabel().setText("Status: " + status);
    }
}