package com.litewolf101.illagers_plus.client.render;

import com.litewolf101.illagers_plus.client.model.ModelFurantur;
import com.litewolf101.illagers_plus.objects.entity.EntityFurantur;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nullable;
@OnlyIn(Dist.CLIENT)
public class RenderEntityFurantur extends MobRenderer<EntityFurantur, ModelFurantur> {

    private ResourceLocation mobTexture = new ResourceLocation("illagers_plus:textures/entity/furantur.png");

    public RenderEntityFurantur(EntityRendererManager manager) {
        super(manager, new ModelFurantur(0.0F, 0.0F, 64, 64), 0.5f);
        this.addLayer(new HeldItemLayer<EntityFurantur, ModelFurantur>(this) {
            public void render(EntityFurantur entityIn, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {
                super.render(entityIn, p_212842_2_, p_212842_3_, p_212842_4_, p_212842_5_, p_212842_6_, p_212842_7_, p_212842_8_);
            }

            @Override
            protected void translateToHand(HandSide p_191361_1_) {
                ((ModelFurantur)getEntityModel()).getArm(p_191361_1_).postRender(0.0625F);
            }
        });
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityFurantur entity) {
        return mobTexture;
    }

    public static class RenderFactory implements IRenderFactory<EntityFurantur> {

        @Override
        public EntityRenderer<? super EntityFurantur> createRenderFor(EntityRendererManager manager) {
            return new RenderEntityFurantur(manager);
        }
    }

    @Override
    protected void preRenderCallback(EntityFurantur entitylivingbaseIn, float partialTickTime) {
        GlStateManager.scalef(0.9375F, 0.9375F, 0.9375F);
    }
}
