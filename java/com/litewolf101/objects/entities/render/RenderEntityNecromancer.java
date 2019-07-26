package com.litewolf101.objects.entities.render;

import com.litewolf101.objects.entities.EntityNecromancer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelIllager;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySpellcasterIllager;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nullable;

public class RenderEntityNecromancer extends RenderLiving<EntityMob> {
    private ResourceLocation mobTexture = new ResourceLocation("illagers_plus:textures/entity/necromancer.png");
    public static final IRenderFactory FACTORY = new Factory();

    public RenderEntityNecromancer(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn) {
        super(rendermanagerIn, new ModelIllager(0.0F, 0.0F, 64, 64), 0.5F);
        this.addLayer(new LayerHeldItem(this) {
            public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
                if (((EntitySpellcasterIllager)entitylivingbaseIn).isSpellcasting()) {
                    super.doRenderLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
                }

            }

            protected void translateToHand(EnumHandSide p_191361_1_) {
                ((ModelIllager)this.livingEntityRenderer.getMainModel()).getArm(p_191361_1_).postRender(0.0625F);
            }
        });
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityMob entity) {
        return mobTexture;
    }

    public static class Factory implements IRenderFactory<EntityNecromancer> {

        @Override
        public Render<? super EntityNecromancer> createRenderFor(RenderManager manager) {
            return new RenderEntityNecromancer(manager, new ModelIllager(0.0F, 0.0F, 64, 64), 0.5F);
        }
    }

    @Override
    protected void preRenderCallback(EntityMob entitylivingbaseIn, float partialTickTime) {
        GlStateManager.scale(0.9375F, 0.9375F, 0.9375F);
    }
}
