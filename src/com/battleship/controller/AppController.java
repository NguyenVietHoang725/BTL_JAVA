package com.battleship.controller;

import com.battleship.view.MainFrame;
import com.battleship.controller.menu.MenuController;
import com.battleship.controller.challenge.ChallengeController;
import com.battleship.model.logic.ChallengeModeLogic;
import com.battleship.model.board.Board;
import com.battleship.model.loader.ChallengeBoardLoader;
import com.battleship.model.player.Player;
import com.battleship.view.panels.challenge.ChallengePlayPanel;
import com.battleship.view.utils.ResourceLoader;
import com.battleship.view.utils.ViewConstants;
import com.battleship.view.panels.challenge.ChallengeInfoAttackPanel;
import com.battleship.view.panels.challenge.ChallengeBoardPanel;

import java.awt.Font;

public class AppController {
    private MainFrame mainFrame;
    private MenuController menuController;
    private ChallengeController challengeController;

    public void start() {
        mainFrame = new MainFrame(this);
        showMenu();
        mainFrame.setVisible(true);
    }

    public void showMenu() {
        if (menuController == null) {
            menuController = new MenuController(this, mainFrame);
            mainFrame.getCardPanel().setPanel(ViewConstants.MAIN_MENU, menuController.getMenuPanel());
        }
        mainFrame.switchScreen(ViewConstants.MAIN_MENU);
    }

    public void startChallengeMode() {
    	System.out.println("AppController: startChallengeMode");
    	try {
            // 1. Lấy font từ ViewConstants
            Font font = ResourceLoader.loadFont(ViewConstants.FONT_PATH, 16f);

            // 2. Đường dẫn file challenge
            String filePath = "/txt/challenge/level1.txt";

            // 3. Khởi tạo loader và load file
            ChallengeBoardLoader loader = new ChallengeBoardLoader();

            // 4. Load board từ file
            Board board = loader.loadBoard(filePath);

            // 5. Khởi tạo player với board
            Player player = new Player("Player", board, null);

            // 6. Khởi tạo logic
            ChallengeModeLogic gameLogic = new ChallengeModeLogic(player, loader);

            // 6. Khởi tạo view tổng
            int leftPanelWidth = 500;
            int boardPanelSize = 780; // hoặc cellSize * 11
            int cellSize = 50;
            ChallengePlayPanel playPanel = new ChallengePlayPanel(font, leftPanelWidth, boardPanelSize, cellSize);

            // 7. Lấy panel con
            ChallengeInfoAttackPanel infoPanel = playPanel.getInfoPanel();
            ChallengeBoardPanel boardPanel = playPanel.getBoardPanel();

            // 8. Khởi tạo controller
            challengeController = new ChallengeController(
                gameLogic,
                playPanel,
                infoPanel,
                boardPanel,
                cellSize
            );

            // 9. Hiển thị lên MainFrame
            mainFrame.getCardPanel().setPanel(ViewConstants.CHALLENGE_PLAY, playPanel);
            mainFrame.switchScreen(ViewConstants.CHALLENGE_PLAY);
        } catch (Exception e) {
            e.printStackTrace();
            // showMessage("Không thể khởi tạo chế độ Challenge!");
        }
    }
}