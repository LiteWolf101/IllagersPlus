package com.litewolf101.illagersplus.client.render;

import com.litewolf101.illagersplus.client.model.ModelArcher;
import com.litewolf101.illagersplus.objects.entity.EntityArcher;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class RenderEntityArcher extends LivingRenderer<EntityArcher, ModelArcher> {
    private ResourceLocation mobTexture = new ResourceLocation("illagers_plus:textures/entity/archer.png");

    public RenderEntityArcher(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new ModelArcher(0.0F, 0.0F, 64, 64), 0.5F);
        this.addLayer(new HeldItemLayer<EntityArcher, ModelArcher>(this) {
            public void render(EntityArcher entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
            {
                super.render(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
            }
            protected void translateToHand(HandSide p_191361_1_)
            {
                ((ModelArcher)this.getEntityModel()).getArm(p_191361_1_).postRender(0.0625F);
            }
        });
        ((ModelArcher)this.getEntityModel()).hat.showModel = true;

    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityArcher entity) {
        return mobTexture;
    }

    public static class RenderFactory implements IRenderFactory<EntityArcher> {

        @Override
        public EntityRenderer<? super EntityArcher> createRenderFor(EntityRendererManager manager) {
            return new RenderEntityArcher(manager);
        }
    }

    @Override
    protected void preRenderCallback(EntityArcher entitylivingbaseIn, float partialTickTime) {
        GlStateManager.scalef(0.9375F, 0.9375F, 0.9375F);
    }
}
