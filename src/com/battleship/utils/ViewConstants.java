package com.battleship.utils;

public class ViewConstants {
	
	// Menu
    // Đường dẫn resource
    public static final String MAINMENU_BG_GIF_PATH = "/images/backgrounds/menu_background.gif";
    public static final String LOGO_PATH = "/images/logo/logo.png";
    public static final String CHALLENGE_BG_IMG_PATH = "/images/backgrounds/challenge_background.png";

    // Tên panel cho CardPanel
    public static final String MAIN_MENU = "mainMenu";
    public static final String CHALLENGE_PLAY = "challengePlay";
    public static final String VSBOT_SHIP_PLACEMENT = "vsbotShipPlacement";
    public static final String VSBOT_PLAY = "vsbotPlay";
    public static final String RULE = "rule";
    
    public static final String[] MENU_BUTTON_IMAGES = {
    	"/images/buttons/menu/challenge_button.png",
        "/images/buttons/menu/vsbot_button.png",
        "/images/buttons/menu/rule_button.png",
        "/images/buttons/menu/setting_button.png",
        "/images/buttons/menu/quit_button.png"
    };
    
    public static final String[] MENU_HOVER_BUTTON_IMAGES = {
    	"/images/buttons/menu/challenge_hover_button.png",
        "/images/buttons/menu/vsbot_hover_button.png",
        "/images/buttons/menu/rule_hover_button.png",
        "/images/buttons/menu/setting_hover_button.png",
        "/images/buttons/menu/quit_hover_button.png"
    };
    
    public static final String[] MENU_PRESSED_BUTTON_IMAGES = {
        "/images/buttons/menu/challenge_pressed_button.png",
        "/images/buttons/menu/vsbot_pressed_button.png",
        "/images/buttons/menu/rule_pressed_button.png",
        "/images/buttons/menu/setting_pressed_button.png",
        "/images/buttons/menu/quit_pressed_button.png"
    };
    
    // Challenge Manage
    public static final String FONT_PATH = "/fonts/groundhog.ttf";

    public static final String[] CHALLENGE_ATK_BUTTON_IMAGES = {
        	"/images/buttons/challenge/challenge_single_atk_btn.png",
            "/images/buttons/challenge/challenge_cross_atk_btn.png",
            "/images/buttons/challenge/challenge_random_atk_btn.png",
            "/images/buttons/challenge/challenge_diamond_atk_btn.png"
        };
    
    public static final String[] CHALLENGE_ATK_HOVER_BUTTON_IMAGES = {
        	"/images/buttons/challenge/challenge_single_atk_hover_btn.png",
            "/images/buttons/challenge/challenge_cross_atk_hover_btn.png",
            "/images/buttons/challenge/challenge_random_atk_hover_btn.png",
            "/images/buttons/challenge/challenge_diamond_atk_hover_btn.png"
        };
    
    public static final String[] CHALLENGE_ATK_PRESSED_BUTTON_IMAGES = {
            "/images/buttons/challenge/challenge_single_atk_pressed_btn.png",
            "/images/buttons/challenge/challenge_cross_atk_pressed_btn.png",
            "/images/buttons/challenge/challenge_random_atk_pressed_btn.png",
            "/images/buttons/challenge/challenge_diamond_atk_pressed_btn.png"
        };
    
    public static final String CELL_NORMAL_IMG = "/images/buttons/board/board_cell.png";
    public static final String CELL_HOVER_IMG = "/images/buttons/board/board_cell_hover.png";
    public static final String CELL_HIT_IMG = "/images/buttons/board/board_cell_hit.png";
    public static final String CELL_MISS_IMG = "/images/buttons/board/board_cell_miss.png";
    public static final String CELL_SHIP_IMG = "/images/buttons/board/board_cell_ship.png";
    
    public static final String[] CHALLENGE_FILE_PATHS = {
    		"/data/challenge/level_1.txt",
    	    "/data/challenge/level_2.txt",
    	    "/data/challenge/level_3.txt",
    	    "/data/challenge/level_4.txt",
    	    "/data/challenge/level_5.txt",
    	    "/data/challenge/level_6.txt",
    	    "/data/challenge/level_7.txt",
    	    "/data/challenge/level_8.txt",
    	    "/data/challenge/level_9.txt",
    	    "/data/challenge/level_10.txt",
    	    "/data/challenge/level_11.txt",
    	    "/data/challenge/level_12.txt",
    	    "/data/challenge/level_13.txt",
    	    "/data/challenge/level_14.txt"
    	};
    
    public static final String VSBOT_PLACESHIP_BG_IMG = "/images/backgrounds/vsbot_placeship_background.png";
    public static final String VSBOT_EASY_BG_IMG = "/images/backgrounds/vsbot_easy_background.png";
    public static final String VSBOT_MEDIUM_BG_IMG = "/images/backgrounds/vsbot_medium_background.png";
    public static final String VSBOT_HARD_BG_IMG = "/images/backgrounds/vsbot_hard_background.png";
    
    public static final String[] VSBOT_FILE_PATHS = {
    	    "/data/vsbot/level_1.txt",
    	    "/data/vsbot/level_2.txt",
    	    "/data/vsbot/level_3.txt",
    	    "/data/vsbot/level_4.txt",
    	    "/data/vsbot/level_5.txt",
    	    "/data/vsbot/level_6.txt",
    	    "/data/vsbot/level_7.txt",
    	    "/data/vsbot/level_8.txt",
    	    "/data/vsbot/level_9.txt",
    	    "/data/vsbot/level_10.txt"
    	};

	 // VSBot button images
	 public static final String VSBOT_EASY_BUTTON = "/images/buttons/vsbot/easy_button.png";
	 public static final String VSBOT_EASY_HOVER_BUTTON = "/images/buttons/vsbot/easy_hover_button.png";
	 public static final String VSBOT_EASY_PRESSED_BUTTON = "/images/buttons/vsbot/easy_pressed_button.png";
	
	 public static final String VSBOT_MEDIUM_BUTTON = "/images/buttons/vsbot/medium_button.png";
	 public static final String VSBOT_MEDIUM_HOVER_BUTTON = "/images/buttons/vsbot/medium_hover_button.png";
	 public static final String VSBOT_MEDIUM_PRESSED_BUTTON = "/images/buttons/vsbot/medium_pressed_button.png";
	
	 public static final String VSBOT_HARD_BUTTON = "/images/buttons/vsbot/hard_button.png";
	 public static final String VSBOT_HARD_HOVER_BUTTON = "/images/buttons/vsbot/hard_hover_button.png";
	 public static final String VSBOT_HARD_PRESSED_BUTTON = "/images/buttons/vsbot/hard_pressed_button.png";
	
	 public static final String VSBOT_ROTATE_BUTTON = "/images/buttons/vsbot/rotate_button.png";
	 public static final String VSBOT_ROTATE_HOVER_BUTTON = "/images/buttons/vsbot/rotate_hover_button.png";
	 public static final String VSBOT_ROTATE_PRESSED_BUTTON = "/images/buttons/vsbot/rotate_pressed_button.png";
	
	 public static final String VSBOT_RESET_BUTTON = "/images/buttons/vsbot/reset_button.png";
	 public static final String VSBOT_RESET_HOVER_BUTTON = "/images/buttons/vsbot/reset_hover_button.png";
	 public static final String VSBOT_RESET_PRESSED_BUTTON = "/images/buttons/vsbot/reset_pressed_button.png";
	
	 public static final String VSBOT_CONFIRM_BUTTON = "/images/buttons/vsbot/confirm_button.png";
	 public static final String VSBOT_CONFIRM_HOVER_BUTTON = "/images/buttons/vsbot/confirm_hover_button.png";
	 public static final String VSBOT_CONFIRM_PRESSED_BUTTON = "/images/buttons/vsbot/confirm_pressed_button.png";
	
	 public static final String VSBOT_UNDO_BUTTON = "/images/buttons/vsbot/undo_button.png";
	 public static final String VSBOT_UNDO_HOVER_BUTTON = "/images/buttons/vsbot/undo_hover_button.png";
	 public static final String VSBOT_UNDO_PRESSED_BUTTON = "/images/buttons/vsbot/undo_pressed_button.png";
	
	 public static final String VSBOT_REDO_BUTTON = "/images/buttons/vsbot/redo_button.png";
	 public static final String VSBOT_REDO_HOVER_BUTTON = "/images/buttons/vsbot/redo_hover_button.png";
	 public static final String VSBOT_REDO_PRESSED_BUTTON = "/images/buttons/vsbot/redo_pressed_button.png";
	 
	// Dialog Game Over
	 public static final String GAMEOVER_DIALOG_BG = "/images/backgrounds/gameover_background.png";

	 public static final String GAMEOVER_REPLAY_BTN = "/images/buttons/gameover/replay_button.png";
	 public static final String GAMEOVER_REPLAY_HOVER_BTN = "/images/buttons/gameover/replay_hover_button.png";
	 public static final String GAMEOVER_REPLAY_PRESSED_BTN = "/images/buttons/gameover/replay_pressed_button.png";

	 public static final String GAMEOVER_MENU_BTN = "/images/buttons/gameover/mainmenu_button.png";
	 public static final String GAMEOVER_MENU_HOVER_BTN = "/images/buttons/gameover/mainmenu_hover_button.png";
	 public static final String GAMEOVER_MENU_PRESSED_BTN = "/images/buttons/gameover/mainmenu_pressed_button.png";
	 
	 // --- Background ---
	 public static final String SETTING_DIALOG_BG = "/images/backgrounds/setting_background.png";

	 // --- Slider (track + thumb) ---
	 public static final String SLIDER_TRACK = "/images/slider/slider_track.png";
	 public static final String SLIDER_THUMB_NORMAL = "/images/slider/slider_thumb.png";
	 public static final String SLIDER_THUMB_HOVER = "/images/slider/slider_hover_thumb.png";
	 public static final String SLIDER_THUMB_PRESSED = "/images/slider/slider_pressed_thumb.png";


	 // --- Checkbox ---
	 public static final String CHECKBOX_OFF = "/images/buttons/setting/checkbox_button.png";
	 public static final String CHECKBOX_ON = "/images/buttons/setting/checkbox_clicked_button.png";

	 // --- Apply Button (72x24) ---
	 public static final String BTN_APPLY_NORMAL = "/images/buttons/setting/apply_button.png";
	 public static final String BTN_APPLY_HOVER = "/images/buttons/setting/apply_hover_button.png";
	 public static final String BTN_APPLY_PRESSED = "/images/buttons/setting/apply_pressed_button.png";

	 // --- Cancel Button (72x24) ---
	 public static final String BTN_CANCEL_NORMAL = "/images/buttons/setting/cancel_button.png";
	 public static final String BTN_CANCEL_HOVER = "/images/buttons/setting/cancel_hover_button.png";
	 public static final String BTN_CANCEL_PRESSED = "/images/buttons/setting/cancel_pressed_button.png";
	 
	 
	 public static final String ERROR_DIALOG_BG = "/images/backgrounds/error_background.png";
	 
	 public static final String ERROR_BACK_BTN = "/images/buttons/error/back_button.png";
	 public static final String ERROR_BACK_HOVER_BTN = "/images/buttons/error/back_hover_button.png";
	 public static final String ERROR_BACK_PRESSED_BTN = "/images/buttons/error/back_pressed_button.png";
	 
	 // --- Pause Dialog ---
	 public static final String PAUSE_DIALOG_BG = "/images/backgrounds/pause_background.png";

	 public static final String PAUSE_RESUME_BTN = "/images/buttons/pause/resume_button.png";
	 public static final String PAUSE_RESUME_HOVER_BTN = "/images/buttons/pause/resume_hover_button.png";
	 public static final String PAUSE_RESUME_PRESSED_BTN = "/images/buttons/pause/resume_pressed_button.png";

	 public static final String PAUSE_SETTING_BTN = "/images/buttons/pause/setting_on_button.png";
	 public static final String PAUSE_SETTING_HOVER_BTN = "/images/buttons/pause/setting_hover_button.png";
	 public static final String PAUSE_SETTING_PRESSED_BTN = "/images/buttons/pause/setting_off_button.png";

	 public static final String PAUSE_MAINMENU_BTN = "/images/buttons/pause/mainmenu_button.png";
	 public static final String PAUSE_MAINMENU_HOVER_BTN = "/images/buttons/pause/mainmenu_hover_button.png";
	 public static final String PAUSE_MAINMENU_PRESSED_BTN = "/images/buttons/pause/mainmenu_pressed_button.png";
}
