package com.litewolf101.objects.entities.render;

import com.litewolf101.objects.entities.EntityIllagerKing;
import com.litewolf101.objects.entities.model.ModelIllagerKing;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nullable;

public class RenderEntityIllagerKing extends RenderLiving<EntityMob> {
    private ResourceLocation mobTexture = new ResourceLocation("illagers_plus:textures/entity/illager_king.png");
    public static final IRenderFactory FACTORY = new Factory();

    public RenderEntityIllagerKing(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn) {
        super(rendermanagerIn, new ModelIllagerKing(0.0F, 0.0F, 64, 64), 1.5F);
        this.addLayer(new LayerHeldItem(this)
        {
            public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
            {
                super.doRenderLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
            }
            protected void translateToHand(EnumHandSide p_191361_1_)
            {
                ((ModelIllagerKing)this.livingEntityRenderer.getMainModel()).getArm(p_191361_1_).postRender(0.0625F);
            }
        });
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityMob entity) {
        return mobTexture;
    }

    public static class Factory implements IRenderFactory<EntityIllagerKing> {

        @Override
        public Render<? super EntityIllagerKing> createRenderFor(RenderManager manager) {
            return new RenderEntityIllagerKing(manager, new ModelIllagerKing(0.0F, 0.0F, 64, 64), 1.5F);
        }
    }

    @Override
    protected void preRenderCallback(EntityMob entitylivingbaseIn, float partialTickTime) {
        GlStateManager.scale(2F, 2F, 2F);
    }
}
