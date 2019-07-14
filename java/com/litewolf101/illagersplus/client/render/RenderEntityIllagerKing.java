package com.litewolf101.illagersplus.client.render;

import com.litewolf101.illagersplus.client.model.ModelIllagerKing;
import com.litewolf101.illagersplus.objects.entity.EntityIllagerKing;
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
public class RenderEntityIllagerKing extends LivingRenderer<EntityIllagerKing, ModelIllagerKing> {
    private ResourceLocation mobTexture = new ResourceLocation("illagers_plus:textures/entity/illager_king.png");

    public RenderEntityIllagerKing(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new ModelIllagerKing(0.0F, 0.0F, 64, 64), 1.5F);
        this.addLayer(new HeldItemLayer<EntityIllagerKing, ModelIllagerKing>(this) {
            public void render(EntityIllagerKing entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
                super.render(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
            }
            protected void translateToHand(HandSide p_191361_1_) {
                ((ModelIllagerKing)this.getEntityModel()).getArm(p_191361_1_).postRender(0.0625F);
            }
        });
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityIllagerKing entity) {
        return mobTexture;
    }

    public static class RenderFactory implements IRenderFactory<EntityIllagerKing> {

        @Override
        public EntityRenderer<? super EntityIllagerKing> createRenderFor(EntityRendererManager manager) {
            return new RenderEntityIllagerKing(manager);
        }
    }

    @Override
    protected void preRenderCallback(EntityIllagerKing entitylivingbaseIn, float partialTickTime) {
        GlStateManager.scalef(2F, 2F, 2F);
    }
}
