package com.litewolf101.init;

import com.litewolf101.IllagersPlus;
import com.litewolf101.objects.entities.*;
import com.litewolf101.objects.entities.render.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModEntities {
    public static void init() {
        int id = 1;
        EntityRegistry.registerModEntity(new ResourceLocation(IllagersPlus.MOD_ID, "furantur"), EntityFurantur.class, "furantur", id++, IllagersPlus.INSTANCE, 64, 3, true, 9804699, 1855266);
        EntityRegistry.registerModEntity(new ResourceLocation(IllagersPlus.MOD_ID, "hoarder"), EntityHoarder.class, "hoarder", id++, IllagersPlus.INSTANCE, 64, 3, true, 9804699, 7158918);
        EntityRegistry.registerModEntity(new ResourceLocation(IllagersPlus.MOD_ID, "necromancer"), EntityNecromancer.class, "necromancer", id++, IllagersPlus.INSTANCE, 64, 3, true, 9804699, 0);
        EntityRegistry.registerModEntity(new ResourceLocation(IllagersPlus.MOD_ID, "illager_king"), EntityIllagerKing.class, "illager_king", id++, IllagersPlus.INSTANCE, 64, 3, true, 9804699, 15787008);
        EntityRegistry.registerModEntity(new ResourceLocation(IllagersPlus.MOD_ID, "enchanter"), EntityEnchanter.class, "enchanter", id++, IllagersPlus.INSTANCE, 64, 3, true, 9804699, 1120750);
        EntityRegistry.registerModEntity(new ResourceLocation(IllagersPlus.MOD_ID, "archer"), EntityArcher.class, "archer", id++, IllagersPlus.INSTANCE, 64, 3, true, 9804699, 1973274);
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        RenderingRegistry.registerEntityRenderingHandler(EntityFurantur.class, RenderEntityFurantur.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityHoarder.class, RenderEntityHoarder.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityNecromancer.class, RenderEntityNecromancer.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityIllagerKing.class, RenderEntityIllagerKing.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityEnchanter.class, RenderEntityEnchanter.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityArcher.class, RenderEntityArcher.FACTORY);
    }
}
