package com.battleship.controller.setting;

import javax.sound.sampled.*;
import com.battleship.model.setting.SoundSettings;
import com.battleship.utils.AppConstants;
import com.battleship.utils.ResourceLoader;

import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    private static Clip bgmClip;
    private static final SoundSettings settings = new SoundSettings();
    private static final Map<String, Clip> sfxCache = new HashMap<>();
    private static final Map<String, Clip> bgmCache = new HashMap<>();
    private static String currentBgmPath;

    public static void preloadSounds() {
        preloadBGM(AppConstants.BGM_MAIN_MENU);
        preloadBGM(AppConstants.BGM_CHALLENGE);
        preloadBGM(AppConstants.BGM_SHIP_PLACEMENT);
        preloadBGM(AppConstants.BGM_EASY_BOT);
        preloadBGM(AppConstants.BGM_MEDIUM_BOT);
        preloadBGM(AppConstants.BGM_HARD_BOT);

        preloadSFX(AppConstants.SFX_SINGLE_ATTACK);
        preloadSFX(AppConstants.SFX_CROSS_ATTACK);
    }

    private static void preloadSFX(String path) {
        try {
            if (!sfxCache.containsKey(path)) {
                Clip clip = ResourceLoader.loadAudioClip(path);
                sfxCache.put(path, clip);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void preloadBGM(String path) {
        try {
            if (!bgmCache.containsKey(path)) {
                Clip clip = ResourceLoader.loadAudioClip(path);
                bgmCache.put(path, clip);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playBGM(String path) {
        stopBGM();
        if (!settings.isMusicEnabled()) return;

        try {
            if (bgmCache.containsKey(path)) {
                bgmClip = bgmCache.get(path);
            } else {
                bgmClip = ResourceLoader.loadAudioClip(path);
                bgmCache.put(path, bgmClip);
            }
            currentBgmPath = path;
            setVolume(bgmClip, settings.getMusicVolume());
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopBGM() {
        if (bgmClip != null && bgmClip.isRunning()) {
            bgmClip.stop();
            bgmClip.close();
        }
    }

    public static void playSFX(String path) {
        if (!settings.isSfxEnabled()) return;

        new Thread(() -> {
            try {
                Clip clip = ResourceLoader.loadAudioClip(path);
                setVolume(clip, settings.getSfxVolume());
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static boolean isBgmPlaying() {
        return bgmClip != null && bgmClip.isRunning();
    }

    public static void updateBgmVolume() {
        if (bgmClip != null) {
            setVolume(bgmClip, settings.getMusicVolume());
        }
    }

    public static void updateSfxVolume() {
        // No need now, but placeholder for future
    }

    private static void setVolume(Clip clip, float volume) {
        try {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float gain;
            if (volume <= 0.01f) {
                gain = gainControl.getMinimum();
            } else {
                gain = (float) (Math.log10(volume) * 20); // log-scale dB
            }
            gainControl.setValue(Math.max(gainControl.getMinimum(), Math.min(gainControl.getMaximum(), gain)));
        } catch (IllegalArgumentException ignored) {
            // If clip does not support volume control
        }
    }

    public static String getCurrentBgmPath() {
        return currentBgmPath;
    }

    public static SoundSettings getSettings() {
        return settings;
    }
}
