package com.battleship.controller;

import com.battleship.view.MainFrame;
import com.battleship.controller.menu.MenuController;
import com.battleship.controller.challenge.ChallengeController;
import com.battleship.controller.vsbot.VsBotController;
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
    private VsBotController vsBotController;

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
            String[] challengeFiles = ViewConstants.CHALLENGE_FILE_PATHS;
            String filePath = challengeFiles[new java.util.Random().nextInt(challengeFiles.length)];

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
                this,
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
    
    public void startVsBotMode() {
        // 1. Khởi tạo font và các thông số giao diện
        Font font = com.battleship.view.utils.ResourceLoader.loadFont(
            com.battleship.view.utils.ViewConstants.FONT_PATH, 16f
        );
        int cellSize = 50;

        // 2. Tạo panel đặt tàu
        var placementPanel = new com.battleship.view.panels.vsbot.placement.VsBotShipPlacementPanel(font, cellSize);

        // 3. Đăng ký sự kiện xác nhận đặt tàu
        // Giả sử bạn thêm callback hoặc setter cho sự kiện xác nhận, ví dụ:
        placementPanel.getConfirmButton().addActionListener(e -> {
            if (placementPanel.confirm()) {
                // Lấy board người chơi
                var playerBoard = placementPanel.getBoard();
                // Lấy độ khó
                String diff = placementPanel.getSelectedDifficulty();

                // Khởi tạo chiến lược bot
                com.battleship.interfaces.IBotAttackStrategy botStrategy;
                switch (diff) {
                    case "Dễ":
                        botStrategy = new com.battleship.model.botstrategy.EasyBotAtkStrategy();
                        break;
                    case "Trung bình":
                        botStrategy = new com.battleship.model.botstrategy.MediumBotAtkStrategy();
                        break;
                    case "Khó":
                        botStrategy = new com.battleship.model.botstrategy.HardBotAtkStrategy();
                        break;
                    default:
                        botStrategy = new com.battleship.model.botstrategy.EasyBotAtkStrategy();
                }

                // Khởi tạo Player và Bot
                var player = new com.battleship.model.player.Player("Player", playerBoard, null);
                var botBoard = new com.battleship.model.board.Board();
                var bot = new com.battleship.model.player.Bot("Bot", botBoard, null, botStrategy);

                // Khởi tạo panel chơi
                var playPanel = new com.battleship.view.panels.vsbot.play.VsBotPlayPanel(font, cellSize);

                // Khởi tạo controller
                vsBotController = new com.battleship.controller.vsbot.VsBotController(
                    player, bot, playPanel, this
                );

                // Chuyển sang màn chơi
                mainFrame.getCardPanel().setPanel(
                    com.battleship.view.utils.ViewConstants.VSBOT_PLAY, playPanel
                );
                mainFrame.switchScreen(com.battleship.view.utils.ViewConstants.VSBOT_PLAY);
            }
        });

        // 4. Hiển thị panel đặt tàu lên MainFrame
        mainFrame.getCardPanel().setPanel(
            com.battleship.view.utils.ViewConstants.VSBOT_SHIP_PLACEMENT, placementPanel
        );
        mainFrame.switchScreen(com.battleship.view.utils.ViewConstants.VSBOT_SHIP_PLACEMENT);
    }
}