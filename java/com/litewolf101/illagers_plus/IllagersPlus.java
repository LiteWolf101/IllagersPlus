package com.litewolf101.illagers_plus;

import com.litewolf101.illagers_plus.client.render.RenderRegistry;
import com.litewolf101.illagers_plus.event.MakeIllagersPlusActuallyEvilEvent;
import com.litewolf101.illagers_plus.init.IllagersPlusItemGroup;
import com.litewolf101.illagers_plus.proxy.ClientProxy;
import com.litewolf101.illagers_plus.proxy.IProxy;
import com.litewolf101.illagers_plus.proxy.ServerProxy;
import com.litewolf101.illagers_plus.world.AddBiomeDecoConfig;
import net.minecraft.item.ItemGroup;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
        public static void registerFeatures(final RegistryEvent.Register<Feature<?>> event) {
            LOGGER.info("Adding Illager Features to the world");
            AddBiomeDecoConfig.addIllagerArcherTower(Biomes.PLAINS);
            AddBiomeDecoConfig.addIllagerArcherTower(Biomes.SWAMP);
            AddBiomeDecoConfig.addIllagerArcherTower(Biomes.FOREST);
            AddBiomeDecoConfig.addIllagerArcherTower(Biomes.SNOWY_TAIGA);
            AddBiomeDecoConfig.addIllagerArcherTower(Biomes.TAIGA);
            AddBiomeDecoConfig.addIllagerArcherTower(Biomes.SAVANNA);
            AddBiomeDecoConfig.addIllagerArcherTower(Biomes.DESERT);
            AddBiomeDecoConfig.addIllagerArcherTower(Biomes.SNOWY_TUNDRA);

            //Tower is too big, has too many chunk issues. TODO: break up structure into 4 parts or shrink it
            //AddBiomeDecoConfig.addIllagerTower(Biomes.PLAINS);
            //AddBiomeDecoConfig.addIllagerTower(Biomes.DARK_FOREST);
        }
    }
}
