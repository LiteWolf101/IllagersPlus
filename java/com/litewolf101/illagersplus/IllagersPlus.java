package com.litewolf101.illagersplus;

import com.litewolf101.illagersplus.client.render.RenderRegistry;
import com.litewolf101.illagersplus.event.MakeIllagersPlusActuallyEvilEvent;
import com.litewolf101.illagersplus.init.IllagersPlusItemGroup;
import com.litewolf101.illagersplus.init.EntityInit;
import com.litewolf101.illagersplus.proxy.ClientProxy;
import com.litewolf101.illagersplus.proxy.IProxy;
import com.litewolf101.illagersplus.proxy.ServerProxy;
import com.litewolf101.illagersplus.world.StructureRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@Mod("illagers_plus")
public class IllagersPlus {
    private static final Logger LOGGER = LogManager.getLogger();
    public static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());
    public static final ItemGroup ILLAGERS_PLUS = new IllagersPlusItemGroup();
    public static final String MOD_ID = "illagers_plus";

    public IllagersPlus() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientRegistries);
    }

    private void setup(final FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new MakeIllagersPlusActuallyEvilEvent());
    }

    private void clientRegistries(final FMLClientSetupEvent event) {
        RenderRegistry.registerEntityRenders();
        LOGGER.info("Made things look pretty");
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void registerEntityEggs(final RegistryEvent.Register<Item> event) {
            EntityInit.registerSpawnEggs(event);
            LOGGER.info("Stuffed entities into eggs!");
        }

        @SubscribeEvent
        public static void registerEntities(final RegistryEvent.Register<EntityType<?>> event) {
            event.getRegistry().registerAll(
                    EntityInit.ARCHER,
                    EntityInit.ENCHANTER,
                    EntityInit.FURANTUR,
                    EntityInit.HOARDER,
                    EntityInit.ILLAGER_KING,
                    EntityInit.NECROMANCER
            );
            LOGGER.info("Hatched Entities!");
        }

        @SubscribeEvent
        public static void registerFeatures(final RegistryEvent.Register<Feature<?>> event) {
            StructureRegistry.init();
        }
    }
}
