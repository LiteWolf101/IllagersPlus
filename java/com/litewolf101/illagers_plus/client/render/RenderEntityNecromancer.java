package com.litewolf101.illagers_plus.client.render;

import com.litewolf101.illagers_plus.client.model.ModelNecromancer;
import com.litewolf101.illagers_plus.objects.entity.EntityNecromancer;
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
public class RenderEntityNecromancer extends MobRenderer<EntityNecromancer, ModelNecromancer> {
    private ResourceLocation mobTexture = new ResourceLocation("illagers_plus:textures/entity/necromancer.png");

    public RenderEntityNecromancer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new ModelNecromancer(0.0F, 0.0F, 64, 64), 0.5F);
        this.addLayer(new HeldItemLayer<EntityNecromancer, ModelNecromancer>(this) {
            public void render(EntityNecromancer entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
                if (((EntityNecromancer)entitylivingbaseIn).isSpellcasting()) {
                    super.render(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
                }

            }

            protected void translateToHand(HandSide p_191361_1_) {
                ((ModelNecromancer)this.getEntityModel()).getArm(p_191361_1_).postRender(0.0625F);
            }
        });
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityNecromancer entity) {
        return mobTexture;
    }

    public static class RenderFactory implements IRenderFactory<EntityNecromancer> {

        @Override
        public EntityRenderer<? super EntityNecromancer> createRenderFor(EntityRendererManager manager) {
            return new RenderEntityNecromancer(manager);
        }
    }

    @Override
    protected void preRenderCallback(EntityNecromancer entitylivingbaseIn, float partialTickTime) {
        GlStateManager.scalef(0.9375F, 0.9375F, 0.9375F);
    }
}
