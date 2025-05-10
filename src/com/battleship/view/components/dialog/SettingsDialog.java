package com.battleship.view.components.dialog;

import com.battleship.controller.setting.SoundManager;
import com.battleship.model.setting.SoundSettings;
import com.battleship.view.components.buttons.CustomButton;
import com.battleship.view.components.common.ImageBackgroundPanel;
import com.battleship.view.components.slider.CustomSliderUI;
import com.battleship.utils.ResourceLoader;
import com.battleship.utils.ViewConstants;

import javax.swing.*;
import java.awt.*;

public class SettingsDialog {
    public static void showDialog(JFrame parentFrame) {
        JDialog dialog = new JDialog(parentFrame, "Settings", true);
        dialog.setUndecorated(true);
        dialog.setSize(240, 135);
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(parentFrame);

        // Background
        ImageBackgroundPanel bgPanel = new ImageBackgroundPanel(ViewConstants.SETTING_DIALOG_BG);
        bgPanel.setBounds(0, 0, 240, 135);

        SoundSettings settings = SoundManager.getSettings();

        Font font = new Font("Arial", Font.BOLD, 12);

        // BGM
        JLabel bgmLabel = new JLabel("BGM:");
        bgmLabel.setFont(font);
        bgmLabel.setForeground(Color.WHITE);
        bgmLabel.setBounds(16, 20, 40, 20);

        JCheckBox bgmCheck = new JCheckBox();
        bgmCheck.setSelected(settings.isMusicEnabled());
        bgmCheck.setOpaque(false);
        bgmCheck.setBounds(60, 20, 16, 16);
        bgmCheck.setIcon(new ImageIcon(ResourceLoader.loadImage(ViewConstants.CHECKBOX_OFF)));
        bgmCheck.setSelectedIcon(new ImageIcon(ResourceLoader.loadImage(ViewConstants.CHECKBOX_ON)));

        JSlider bgmSlider = new JSlider(0, 100, (int)(settings.getMusicVolume() * 100));
        bgmSlider.setBounds(90, 20, 130, 16);
        bgmSlider.setOpaque(false);
        bgmSlider.setUI(new CustomSliderUI(
            bgmSlider,
            ResourceLoader.loadImage(ViewConstants.SLIDER_TRACK),
            ResourceLoader.loadImage(ViewConstants.SLIDER_THUMB_NORMAL),
            ResourceLoader.loadImage(ViewConstants.SLIDER_THUMB_HOVER),
            ResourceLoader.loadImage(ViewConstants.SLIDER_THUMB_PRESSED)
        ));

        // SFX
        JLabel sfxLabel = new JLabel("SFX:");
        sfxLabel.setFont(font);
        sfxLabel.setForeground(Color.WHITE);
        sfxLabel.setBounds(16, 50, 40, 20);

        JCheckBox sfxCheck = new JCheckBox();
        sfxCheck.setSelected(settings.isSfxEnabled());
        sfxCheck.setOpaque(false);
        sfxCheck.setBounds(60, 50, 16, 16);
        sfxCheck.setIcon(new ImageIcon(ResourceLoader.loadImage(ViewConstants.CHECKBOX_OFF)));
        sfxCheck.setSelectedIcon(new ImageIcon(ResourceLoader.loadImage(ViewConstants.CHECKBOX_ON)));

        JSlider sfxSlider = new JSlider(0, 100, (int)(settings.getSfxVolume() * 100));
        sfxSlider.setBounds(90, 50, 130, 16);
        sfxSlider.setOpaque(false);
        sfxSlider.setUI(new CustomSliderUI(
            sfxSlider,
            ResourceLoader.loadImage(ViewConstants.SLIDER_TRACK),
            ResourceLoader.loadImage(ViewConstants.SLIDER_THUMB_NORMAL),
            ResourceLoader.loadImage(ViewConstants.SLIDER_THUMB_HOVER),
            ResourceLoader.loadImage(ViewConstants.SLIDER_THUMB_PRESSED)
        ));

        // Buttons
        CustomButton applyBtn = new CustomButton(
            ViewConstants.BTN_APPLY_NORMAL,
            ViewConstants.BTN_APPLY_HOVER,
            ViewConstants.BTN_APPLY_PRESSED,
            72, 24
        );
        applyBtn.setBounds(32, 95, 72, 24);
        applyBtn.addActionListener(e -> {
            boolean musicEnabled = bgmCheck.isSelected();
            boolean sfxEnabled = sfxCheck.isSelected();
            float musicVolume = bgmSlider.getValue() / 100f;
            float sfxVolume = sfxSlider.getValue() / 100f;

            settings.setMusicEnabled(musicEnabled);
            settings.setMusicVolume(musicVolume);
            settings.setSfxEnabled(sfxEnabled);
            settings.setSfxVolume(sfxVolume);

            if (!musicEnabled) {
                SoundManager.stopBGM();
            } else {
                if (SoundManager.isBgmPlaying()) {
                    SoundManager.updateBgmVolume();
                } else {
                    SoundManager.playBGM(SoundManager.getCurrentBgmPath());
                }
            }

            SoundManager.updateSfxVolume();
        });

        CustomButton cancelBtn = new CustomButton(
            ViewConstants.BTN_CANCEL_NORMAL,
            ViewConstants.BTN_CANCEL_HOVER,
            ViewConstants.BTN_CANCEL_PRESSED,
            72, 24
        );
        cancelBtn.setBounds(128, 95, 72, 24);
        cancelBtn.addActionListener(e -> dialog.dispose());

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(240, 135));

        layeredPane.add(bgPanel, Integer.valueOf(0));
        layeredPane.add(bgmLabel, Integer.valueOf(1));
        layeredPane.add(bgmCheck, Integer.valueOf(1));
        layeredPane.add(bgmSlider, Integer.valueOf(1));
        layeredPane.add(sfxLabel, Integer.valueOf(1));
        layeredPane.add(sfxCheck, Integer.valueOf(1));
        layeredPane.add(sfxSlider, Integer.valueOf(1));
        layeredPane.add(applyBtn, Integer.valueOf(1));
        layeredPane.add(cancelBtn, Integer.valueOf(1));

        dialog.setContentPane(layeredPane);
        dialog.setVisible(true);
    }
}
