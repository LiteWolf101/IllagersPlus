package com.litewolf101.objects.entities.render;

import com.litewolf101.objects.entities.EntityEnchanter;
import com.litewolf101.objects.entities.model.ModelEnchanter;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nullable;

public class RenderEntityEnchanter extends RenderLiving<EntityEnchanter> {

    private ResourceLocation mobTexture = new ResourceLocation("illagers_plus:textures/entity/enchanter.png");
    public static final IRenderFactory FACTORY = new Factory();
    private final ModelEnchanter modelEnchanter;

    public RenderEntityEnchanter(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn) {
        super(rendermanagerIn, new ModelEnchanter(0.0F, 0.0F, 96, 96), 0.5F);
        modelEnchanter = (ModelEnchanter) super.mainModel;
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityEnchanter entity) {
        return mobTexture;
    }

    public static class Factory implements IRenderFactory<EntityEnchanter> {

        @Override
        public Render<? super EntityEnchanter> createRenderFor(RenderManager manager) {
            return new RenderEntityEnchanter(manager, new ModelEnchanter(0.0F, 0.0F, 64, 64), 0.5F);
        }
    }

    @Override
    protected void preRenderCallback(EntityEnchanter entitylivingbaseIn, float partialTickTime) {
        GlStateManager.scale(0.9375F, 0.9375F, 0.9375F);
    }
}
