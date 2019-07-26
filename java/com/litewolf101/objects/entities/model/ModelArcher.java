package com.litewolf101.objects.entities.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelArcher extends ModelBase {
    public ModelRenderer head;
    public ModelRenderer hat;
    public ModelRenderer body;
    public ModelRenderer leg0;
    public ModelRenderer leg1;
    public ModelRenderer nose;
    public ModelRenderer rightArm;
    public ModelRenderer leftArm;

    public ModelArcher(float scaleFactor, float p_i47227_2_, int textureWidthIn, int textureHeightIn) {
        this.head = (new ModelRenderer(this)).setTextureSize(textureWidthIn, textureHeightIn);
        this.head.setRotationPoint(0.0F, 0.0F + p_i47227_2_, 0.0F);
        this.head.setTextureOffset(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, scaleFactor);
        this.hat = (new ModelRenderer(this, 32, 0)).setTextureSize(textureWidthIn, textureHeightIn);
        this.hat.addBox(-4.0F, -10.0F, -4.0F, 8, 12, 8, scaleFactor + 0.45F);
        this.head.addChild(this.hat);
        this.hat.showModel = true;
        this.nose = (new ModelRenderer(this)).setTextureSize(textureWidthIn, textureHeightIn);
        this.nose.setRotationPoint(0.0F, p_i47227_2_ - 2.0F, 0.0F);
        this.nose.setTextureOffset(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2, 4, 2, scaleFactor);
        this.head.addChild(this.nose);
        this.body = (new ModelRenderer(this)).setTextureSize(textureWidthIn, textureHeightIn);
        this.body.setRotationPoint(0.0F, 0.0F + p_i47227_2_, 0.0F);
        this.body.setTextureOffset(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, scaleFactor);
        this.body.setTextureOffset(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8, 18, 6, scaleFactor + 0.5F);
        this.leg0 = (new ModelRenderer(this, 0, 22)).setTextureSize(textureWidthIn, textureHeightIn);
        this.leg0.setRotationPoint(-2.0F, 12.0F + p_i47227_2_, 0.0F);
        this.leg0.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, scaleFactor);
        this.leg1 = (new ModelRenderer(this, 0, 22)).setTextureSize(textureWidthIn, textureHeightIn);
        this.leg1.mirror = true;
        this.leg1.setRotationPoint(2.0F, 12.0F + p_i47227_2_, 0.0F);
        this.leg1.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, scaleFactor);
        this.rightArm = (new ModelRenderer(this, 40, 46)).setTextureSize(textureWidthIn, textureHeightIn);
        this.rightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, scaleFactor);
        this.rightArm.setRotationPoint(-5.0F, 2.0F + p_i47227_2_, 0.0F);
        this.leftArm = (new ModelRenderer(this, 40, 46)).setTextureSize(textureWidthIn, textureHeightIn);
        this.leftArm.mirror = true;
        this.leftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, scaleFactor);
        this.leftArm.setRotationPoint(5.0F, 2.0F + p_i47227_2_, 0.0F);
    }

    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        this.head.render(scale);
        this.body.render(scale);
        this.leg0.render(scale);
        this.leg1.render(scale);
        this.leftArm.render(scale);
        this.rightArm.render(scale);
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleX = headPitch * 0.017453292F;
        this.rightArm.rotateAngleY = -0.1F + this.head.rotateAngleY;
        this.rightArm.rotateAngleX = -((float)Math.PI / 2F) + this.head.rotateAngleX;
        this.leftArm.rotateAngleX = -0.9424779F + this.head.rotateAngleX;
        this.leftArm.rotateAngleY = this.head.rotateAngleY - 0.4F;
        this.leftArm.rotateAngleZ = ((float)Math.PI / 2F);
        this.leg0.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
        this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount * 0.5F;
        this.leg0.rotateAngleY = 0.0F;
        this.leg1.rotateAngleY = 0.0F;
    }

    public ModelRenderer getArm(EnumHandSide p_191216_1_)
    {
        return p_191216_1_ == EnumHandSide.LEFT ? this.leftArm : this.rightArm;
    }
}
