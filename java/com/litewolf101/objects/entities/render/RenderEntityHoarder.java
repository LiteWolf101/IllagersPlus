package com.litewolf101.objects.entities.render;

import com.litewolf101.objects.entities.EntityHoarder;
import com.litewolf101.objects.entities.model.ModelHoarder;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nullable;

public class RenderEntityHoarder extends RenderLiving<EntityHoarder> {

    private ResourceLocation mobTexture = new ResourceLocation("illagers_plus:textures/entity/hoarder.png");
    public static final IRenderFactory FACTORY = new Factory();
    private final ModelHoarder modelHoarder;

    public RenderEntityHoarder(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn) {
        super(rendermanagerIn, new ModelHoarder(0.0F, 0.0F, 64, 64), 0.5F);
        modelHoarder = (ModelHoarder) super.mainModel;
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityHoarder entity) {
        return mobTexture;
    }

    public static class Factory implements IRenderFactory<EntityHoarder> {

        @Override
        public Render<? super EntityHoarder> createRenderFor(RenderManager manager) {
            return new RenderEntityHoarder(manager, new ModelHoarder(0.0F, 0.0F, 64, 64), 0.5F);
        }
    }

    @Override
    protected void preRenderCallback(EntityHoarder entitylivingbaseIn, float partialTickTime) {
        GlStateManager.scale(0.9375F, 0.9375F, 0.9375F);
    }
}
