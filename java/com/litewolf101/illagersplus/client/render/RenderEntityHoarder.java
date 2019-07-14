package com.litewolf101.illagersplus.client.render;

import com.litewolf101.illagersplus.client.model.ModelHoarder;
import com.litewolf101.illagersplus.objects.entity.EntityHoarder;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class RenderEntityHoarder extends LivingRenderer<EntityHoarder, ModelHoarder> {
    private ResourceLocation mobTexture = new ResourceLocation("illagers_plus:textures/entity/hoarder.png");

    public RenderEntityHoarder(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new ModelHoarder(0.0F, 0.0F, 64, 64), 0.5F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityHoarder entity) {
        return mobTexture;
    }

    public static class RenderFactory implements IRenderFactory<EntityHoarder> {

        @Override
        public EntityRenderer<? super EntityHoarder> createRenderFor(EntityRendererManager manager) {
            return new RenderEntityHoarder(manager);
        }
    }

    @Override
    protected void preRenderCallback(EntityHoarder entitylivingbaseIn, float partialTickTime) {
        GlStateManager.scalef(0.9375F, 0.9375F, 0.9375F);
    }
}
