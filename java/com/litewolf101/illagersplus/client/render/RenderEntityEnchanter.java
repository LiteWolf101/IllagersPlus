package com.litewolf101.illagersplus.client.render;

import com.litewolf101.illagersplus.client.model.ModelEnchanter;
import com.litewolf101.illagersplus.objects.entity.EntityEnchanter;
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
public class RenderEntityEnchanter extends LivingRenderer<EntityEnchanter, ModelEnchanter> {
    private ResourceLocation mobTexture = new ResourceLocation("illagers_plus:textures/entity/enchanter.png");

    public RenderEntityEnchanter(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new ModelEnchanter(0.0F, 0.0F, 96, 96), 0.5F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityEnchanter entity) {
        return mobTexture;
    }

    public static class RenderFactory implements IRenderFactory<EntityEnchanter> {

        @Override
        public EntityRenderer<? super EntityEnchanter> createRenderFor(EntityRendererManager manager) {
            return new RenderEntityEnchanter(manager);
        }
    }

    @Override
    protected void preRenderCallback(EntityEnchanter entitylivingbaseIn, float partialTickTime) {
        GlStateManager.scalef(0.9375F, 0.9375F, 0.9375F);
    }
}
