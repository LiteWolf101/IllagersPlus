package com.litewolf101.init;

import com.litewolf101.utils.IllagersPlusConfig;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;

public class ModConfiguration {
    public static void init(File configDirectory)
    {
        IllagersPlusConfig.initConfig(new File(configDirectory, "illagers_plus.cfg"));
        MinecraftForge.EVENT_BUS.register(new IllagersPlusConfig());
    }
}
