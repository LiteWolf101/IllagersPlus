package com.litewolf101.objects.entities.render;

import com.litewolf101.objects.entities.EntityFurantur;
import com.litewolf101.objects.entities.model.ModelFurantur;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nullable;

public class RenderEntityFurantur extends RenderLiving<EntityFurantur> {

    private ResourceLocation mobTexture = new ResourceLocation("illagers_plus:textures/entity/furantur.png");
    public static final IRenderFactory FACTORY = new Factory();
    private final ModelFurantur modelFurantur;

    public RenderEntityFurantur(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn) {
        super(rendermanagerIn, new ModelFurantur(0.0F, 0.0F, 64, 64), 0.5F);
        modelFurantur = (ModelFurantur) super.mainModel;

        this.addLayer(new LayerHeldItem(this)
        {
            public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
            {
                super.doRenderLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
            }
            protected void translateToHand(EnumHandSide p_191361_1_)
            {
                ((ModelFurantur)this.livingEntityRenderer.getMainModel()).getArm(p_191361_1_).postRender(0.0625F);
            }
        });
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityFurantur entity) {
        return mobTexture;
    }

    public static class Factory implements IRenderFactory<EntityFurantur> {

        @Override
        public Render<? super EntityFurantur> createRenderFor(RenderManager manager) {
            return new RenderEntityFurantur(manager, new ModelFurantur(0.0F, 0.0F, 64, 64), 0.5F);
        }
    }

    @Override
    protected void preRenderCallback(EntityFurantur entitylivingbaseIn, float partialTickTime) {
        GlStateManager.scale(0.9375F, 0.9375F, 0.9375F);
    }
}
