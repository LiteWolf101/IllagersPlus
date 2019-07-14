package com.litewolf101.objects.entities.render;

import com.litewolf101.objects.entities.EntityArcher;
import com.litewolf101.objects.entities.model.ModelArcher;
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

public class RenderEntityArcher extends RenderLiving<EntityArcher> {
    private ResourceLocation mobTexture = new ResourceLocation("illagers_plus:textures/entity/archer.png");
    public static final IRenderFactory FACTORY = new Factory();
    private final ModelArcher modelArcher;

    public RenderEntityArcher(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn) {
        super(rendermanagerIn, new ModelArcher(0.0F, 0.0F, 64, 64), 0.5F);
        modelArcher = (ModelArcher) super.mainModel;
        this.addLayer(new LayerHeldItem(this)
        {
            public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
            {
                super.doRenderLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
            }
            protected void translateToHand(EnumHandSide p_191361_1_)
            {
                ((ModelArcher)this.livingEntityRenderer.getMainModel()).getArm(p_191361_1_).postRender(0.0625F);
            }
        });
        ((ModelArcher)this.getMainModel()).hat.showModel = true;

    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityArcher entity) {
        return mobTexture;
    }

    public static class Factory implements IRenderFactory<EntityArcher> {

        @Override
        public Render<? super EntityArcher> createRenderFor(RenderManager manager) {
            return new RenderEntityArcher(manager, new ModelArcher(0.0F, 0.0F, 64, 64), 0.5F);
        }
    }

    @Override
    protected void preRenderCallback(EntityArcher entitylivingbaseIn, float partialTickTime) {
        GlStateManager.scale(0.9375F, 0.9375F, 0.9375F);
    }
}
