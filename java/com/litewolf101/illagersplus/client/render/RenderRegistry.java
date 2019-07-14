package com.litewolf101.illagersplus.client.render;

import com.litewolf101.illagersplus.objects.entity.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@OnlyIn(Dist.CLIENT)
public class RenderRegistry {
    public static void registerEntityRenders(){
        RenderingRegistry.registerEntityRenderingHandler(EntityArcher.class, new RenderEntityArcher.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityEnchanter.class, new RenderEntityEnchanter.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityFurantur.class, new RenderEntityFurantur.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityHoarder.class, new RenderEntityHoarder.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityIllagerKing.class, new RenderEntityIllagerKing.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(EntityNecromancer.class, new RenderEntityNecromancer.RenderFactory());
    }
}
