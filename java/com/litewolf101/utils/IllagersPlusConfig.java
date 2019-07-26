package com.litewolf101.utils;

import com.litewolf101.IllagersPlus;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class IllagersPlusConfig {
    public static Configuration config;

    public static final String CATEGORY_GENERAL = "config.category.general.name";

    public static int illager_tower_freq;
    public static int illager_archer_tower_freq;
    public static int illager_centre_freq;

    public static void initConfig(File configFile) {
        if (config == null) {
            config = new Configuration(configFile);
            loadConfiguration();
        }
    }

    @SuppressWarnings("deprecation")
    private static void loadConfiguration() {
        try {
            illager_tower_freq = config.getInt("config.illager_tower_freq.name", CATEGORY_GENERAL, 100, 10, 9001, I18n.translateToLocal("config.illager_tower_freq.comment"));
            illager_archer_tower_freq = config.getInt("config.illager_archer_tower_freq.name", CATEGORY_GENERAL, 100, 10, 9001, I18n.translateToLocal("config.illager_archer_tower_freq.comment"));
            illager_centre_freq = config.getInt("config.illager_centre_freq.name", CATEGORY_GENERAL, 100, 10, 9001, I18n.translateToLocal("config.illager_centre_freq.comment"));
        } catch (Exception e) {
            System.err.println("Error loading config! " + e);
        } finally {
            if (config.hasChanged()) config.save();
        }
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equalsIgnoreCase(IllagersPlus.MOD_ID)) {
            loadConfiguration();
        }
    }
}
