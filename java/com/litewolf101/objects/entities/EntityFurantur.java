package com.litewolf101.objects.entities;

import com.litewolf101.utils.IllagerPlusLootTable;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityFurantur extends EntityAbstractIllagerPlus {
    public EntityFurantur(World worldIn) {
        super(worldIn);
        this.setSize(0.7F, 2F);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.2D, false));
        this.tasks.addTask(8, new EntityAIWander(this, 0.6D));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 3.0F, 1.0F));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[] {EntityVindicator.class}));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityVillager.class, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2499999940395355D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
    }

    protected void entityInit()
    {
        super.entityInit();
    }

    @Override
    protected ResourceLocation getLootTable()
    {
        return IllagerPlusLootTable.FURANTUR;
    }

    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        IEntityLivingData ientitylivingdata = super.onInitialSpawn(difficulty, livingdata);
        this.setEquipmentBasedOnDifficulty(difficulty);
        this.setEnchantmentBasedOnDifficulty(difficulty);
        return ientitylivingdata;
    }

    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.WOODEN_SWORD));
    }

    public boolean isOnSameTeam(Entity entityIn)
    {
        if (super.isOnSameTeam(entityIn))
        {
            return true;
        }
        else if (entityIn instanceof EntityLivingBase && ((EntityLivingBase)entityIn).getCreatureAttribute() == EnumCreatureAttribute.ILLAGER)
        {
            return this.getTeam() == null && entityIn.getTeam() == null;
        }
        else
        {
            return false;
        }
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer entityIn) {
        if (entityIn != null && !entityIn.getActivePotionEffects().isEmpty() && !entityIn.isCreative()) {
            for (PotionEffect effect : entityIn.getActivePotionMap().values()) {
                if (effect != null && !effect.getPotion().isBadEffect()) {
                    this.addPotionEffect(effect);
                }
            }
            entityIn.clearActivePotions();
        }
    }

    @Override
    public void onDeath(DamageSource cause) {
        for (PotionEffect effect : this.getActivePotionMap().values()) {
            if (effect != null) {
                EntityAreaEffectCloud effectCloud = new EntityAreaEffectCloud(this.world);
                effectCloud.posX = this.posX + rand.nextInt(6) - 3;
                effectCloud.posY = this.posY;
                effectCloud.posZ = this.posZ + rand.nextInt(6) - 3;
                effectCloud.setRadius(2.1f);
                effectCloud.setRadiusPerTick(-((float) effectCloud.getDuration()/100000));
                effectCloud.addEffect(effect);
                world.spawnEntity(effectCloud);
            }
        }


        super.onDeath(cause);
    }

    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_EVOCATION_ILLAGER_AMBIENT;
    }

    protected SoundEvent getDeathSound()
    {
        return SoundEvents.EVOCATION_ILLAGER_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_EVOCATION_ILLAGER_HURT;
    }
}
