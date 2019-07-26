package com.litewolf101.proxy;

import com.litewolf101.init.ModEntities;
import com.litewolf101.world.WorldGenCustomStructures;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        ModEntities.init();
        GameRegistry.registerWorldGenerator(new WorldGenCustomStructures(), 0);
    }

    public void init(FMLInitializationEvent event) {
    }

    public void postInit(FMLPostInitializationEvent event) {
    }
}
