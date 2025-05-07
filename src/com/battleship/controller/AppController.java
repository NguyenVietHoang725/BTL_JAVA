package com.battleship.controller;

import com.battleship.view.MainFrame;
import com.battleship.controller.menu.MenuController;
import com.battleship.controller.challenge.ChallengeController;
import com.battleship.controller.vsbot.VsBotController;
import com.battleship.interfaces.IBotAttackStrategy;
import com.battleship.model.logic.ChallengeModeLogic;
import com.battleship.model.attack.AttackInventory;
import com.battleship.model.board.Board;
import com.battleship.model.board.Node;
import com.battleship.model.botstrategy.EasyBotAtkStrategy;
import com.battleship.model.botstrategy.HardBotAtkStrategy;
import com.battleship.model.botstrategy.MediumBotAtkStrategy;
import com.battleship.model.loader.BotBoardLoader;
import com.battleship.model.loader.ChallengeBoardLoader;
import com.battleship.model.player.Bot;
import com.battleship.model.player.Player;
import com.battleship.model.ship.Ship;
import com.battleship.view.panels.challenge.ChallengePlayPanel;
import com.battleship.view.panels.vsbot.placement.VsBotShipPlacementPanel;
import com.battleship.view.panels.vsbot.play.VsBotPlayPanel;
import com.battleship.view.utils.ResourceLoader;
import com.battleship.view.utils.ViewConstants;
import com.battleship.view.panels.challenge.ChallengeInfoAttackPanel;
import com.battleship.view.panels.challenge.ChallengeBoardPanel;

import java.awt.Font;

import javax.swing.JOptionPane;

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
        Font font = ResourceLoader.loadFont(ViewConstants.FONT_PATH, 16f);
        int cellSize1 = 42;
        int cellSize2 = 38;

        var placementPanel = new VsBotShipPlacementPanel(font, cellSize1);

        placementPanel.getInfoPanel().getConfirmButton().addActionListener(e -> {
            if (placementPanel.getBoardPanel().isAllShipsPlaced()) {
                try {
                    Board originalPlayerBoard = placementPanel.getBoardPanel().getBoard();
                    Board playerBoard = new Board();
                    // Copy trạng thái node
                    for (int x = 0; x < 10; x++)
                        for (int y = 0; y < 10; y++)
                            playerBoard.getNode(x, y).setHasShip(originalPlayerBoard.getNode(x, y).isHasShip());
                    // Tạo lại từng ship trên board mới
                    for (Ship ship : originalPlayerBoard.getShips()) {
                        int length = ship.getLength();
                        boolean isHorizontal = ship.isHorizontal();
                        Node firstNode = ship.getNodes().get(0);
                        int x = firstNode.getX();
                        int y = firstNode.getY();
                        playerBoard.addShip(x, y, length, isHorizontal);
                    }

                    String diff = placementPanel.getInfoPanel().getSelectedDifficulty();

                    // 2. Khởi tạo chiến lược bot
                    IBotAttackStrategy botStrategy;
                    switch (diff) {
                        case "Easy":
                            botStrategy = new EasyBotAtkStrategy();
                            break;
                        case "Medium":
                            botStrategy = new MediumBotAtkStrategy();
                            break;
                        case "Hard":
                            botStrategy = new HardBotAtkStrategy();
                            break;
                        default:
                            botStrategy = new EasyBotAtkStrategy();
                    }

                    // 3. Load board cho bot từ file txt (random)
                    Board botBoard = loadRandomVsBotBoard();
                    if (botBoard == null || botBoard.getShips().isEmpty()) {
                        throw new Exception("Failed to load bot board");
                    }

                    // 4. Khởi tạo player và bot
                    AttackInventory playerInventory = new AttackInventory();
                    AttackInventory botInventory = new AttackInventory();
                    
                    var player = new Player("Player", playerBoard, playerInventory);
                    var bot = new Bot("Bot", botBoard, botInventory, botStrategy, diff);
                    bot.setDifficulty(diff);

                    // 5. Khởi tạo giao diện chơi vs bot
                    var playPanel = new VsBotPlayPanel(font, cellSize2, diff);

                    // 6. Cập nhật giao diện board người chơi với các tàu đã đặt
                    playPanel.getPlayerBoardPanel().updatePlayerBoard(playerBoard);
                    
                    // Cập nhật giao diện board bot (chỉ hiển thị các ô chưa tấn công)
                    playPanel.getBotBoardPanel().updateBotBoard(botBoard);

                    // 7. Khởi tạo controller
                    vsBotController = new VsBotController(player, bot, playPanel, this);

                    // 8. Hiển thị lên MainFrame
                    mainFrame.getCardPanel().setPanel(ViewConstants.VSBOT_PLAY, playPanel);
                    mainFrame.switchScreen(ViewConstants.VSBOT_PLAY);
                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(
                        mainFrame,
                        "Error initializing game: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            } else {
                JOptionPane.showMessageDialog(
                    mainFrame,
                    "You haven't placed all ships!",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
                );
            }
        });

        mainFrame.getCardPanel().setPanel(ViewConstants.VSBOT_SHIP_PLACEMENT, placementPanel);
        mainFrame.switchScreen(ViewConstants.VSBOT_SHIP_PLACEMENT);
    }
    
    public void replayVsBotMode() {
        startVsBotMode();
    }

    private Board loadRandomVsBotBoard() {
        String[] files = com.battleship.view.utils.ViewConstants.VSBOT_FILE_PATHS;
        String file = files[new java.util.Random().nextInt(files.length)];
        try {
            String filePath = getClass().getResource(file).getPath();
            com.battleship.model.loader.BotBoardLoader loader = new com.battleship.model.loader.BotBoardLoader();
            return loader.loadBoard(filePath);
        } catch (Exception e) {
            e.printStackTrace();
            return new Board();
        }
    }
}