package com.litewolf101.illagers_plus.client.model;

import com.litewolf101.illagers_plus.objects.entity.EntityHoarder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModelHoarder extends EntityModel<EntityHoarder> {
    public RendererModel head;
    public RendererModel hat;
    public RendererModel body;
    public RendererModel arms;
    public RendererModel leg0;
    public RendererModel leg1;
    public RendererModel nose;

    public ModelHoarder(float scaleFactor, float p_i47227_2_, int textureWidthIn, int textureHeightIn)
    {
        this.head = (new RendererModel(this)).setTextureSize(textureWidthIn, textureHeightIn);
        this.head.setRotationPoint(0.0F, 0.0F + p_i47227_2_, 0.0F);
        this.head.setTextureOffset(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, scaleFactor);
        this.hat = (new RendererModel(this, 32, 0)).setTextureSize(textureWidthIn, textureHeightIn);
        this.hat.addBox(-4.0F, -10.0F, -4.0F, 8, 12, 8, scaleFactor + 0.45F);
        this.head.addChild(this.hat);
        this.hat.showModel = false;
        this.nose = (new RendererModel(this)).setTextureSize(textureWidthIn, textureHeightIn);
        this.nose.setRotationPoint(0.0F, p_i47227_2_ - 2.0F, 0.0F);
        this.nose.setTextureOffset(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2, 4, 2, scaleFactor);
        this.head.addChild(this.nose);
        this.body = (new RendererModel(this)).setTextureSize(textureWidthIn, textureHeightIn);
        this.body.setRotationPoint(0.0F, 0.0F + p_i47227_2_, 0.0F);
        this.body.setTextureOffset(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, scaleFactor);
        this.body.setTextureOffset(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8, 18, 6, scaleFactor + 0.5F);
        this.arms = (new RendererModel(this)).setTextureSize(textureWidthIn, textureHeightIn);
        this.arms.setRotationPoint(0.0F, 0.0F + p_i47227_2_ + 2.0F, 0.0F);
        this.arms.setTextureOffset(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4, 8, 4, scaleFactor);
        RendererModel modelrenderer = (new RendererModel(this, 44, 22)).setTextureSize(textureWidthIn, textureHeightIn);
        modelrenderer.mirror = true;
        modelrenderer.addBox(4.0F, -2.0F, -2.0F, 4, 8, 4, scaleFactor);
        this.arms.addChild(modelrenderer);
        this.arms.setTextureOffset(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8, 4, 4, scaleFactor);
        this.leg0 = (new RendererModel(this, 0, 22)).setTextureSize(textureWidthIn, textureHeightIn);
        this.leg0.setRotationPoint(-2.0F, 12.0F + p_i47227_2_, 0.0F);
        this.leg0.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, scaleFactor);
        this.leg1 = (new RendererModel(this, 0, 22)).setTextureSize(textureWidthIn, textureHeightIn);
        this.leg1.mirror = true;
        this.leg1.setRotationPoint(2.0F, 12.0F + p_i47227_2_, 0.0F);
        this.leg1.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, scaleFactor);
    }


    public void render(EntityHoarder entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        this.head.render(scale);
        this.body.render(scale);
        this.leg0.render(scale);
        this.leg1.render(scale);
        this.arms.render(scale);
    }


    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, EntityHoarder entityIn)
    {
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleX = headPitch * 0.017453292F;
        this.arms.rotationPointY = 3.0F;
        this.arms.rotationPointZ = -1.0F;
        this.arms.rotateAngleX = -0.75F;
        this.leg0.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
        this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount * 0.5F;
        this.leg0.rotateAngleY = 0.0F;
        this.leg1.rotateAngleY = 0.0F;
    }
}
