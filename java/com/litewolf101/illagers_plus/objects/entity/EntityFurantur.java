package com.litewolf101.illagers_plus.objects.entity;

import com.litewolf101.illagers_plus.utils.IllagerPlusLootTable;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.VindicatorEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityFurantur extends EntityAbstractIllagerPlus {

    @SuppressWarnings("unchecked")
    public EntityFurantur(EntityType<? extends EntityFurantur> type, World world) {
        super(type, world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new SwimGoal(this));
        goalSelector.addGoal(1, new HurtByTargetGoal(this, new Class[]{VindicatorEntity.class}));
        goalSelector.addGoal(2, new NearestAttackableTargetGoal(this, PlayerEntity.class, true));
        goalSelector.addGoal(3, new NearestAttackableTargetGoal(this, VillagerEntity.class, true));
        goalSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolemEntity.class, true));
        goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.2D, false));
        goalSelector.addGoal(8, new RandomWalkingGoal(this, 0.6D));
        goalSelector.addGoal(9, new LookAtGoal(this, PlayerEntity.class, 3.0F, 1.0F));
        goalSelector.addGoal(10, new LookAtGoal(this, LivingEntity.class, 8.0F));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2499999940395355D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
    }

    @Override
    protected ResourceLocation getLootTable() {
        return IllagerPlusLootTable.FURANTUR;
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        ILivingEntityData iLivingEntityData = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        this.setEquipmentBasedOnDifficulty(difficultyIn);
        this.setEnchantmentBasedOnDifficulty(difficultyIn);
        return iLivingEntityData;
    }

    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
        this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.WOODEN_SWORD));
    }

    public boolean isOnSameTeam(Entity entityIn) {
        if (super.isOnSameTeam(entityIn)) {
            return true;
        } else if (entityIn instanceof LivingEntity && ((LivingEntity) entityIn).getCreatureAttribute() == CreatureAttribute.ILLAGER) {
            return this.getTeam() == null && entityIn.getTeam() == null;
        } else {
            return false;
        }
    }

    @Override
    public void onCollideWithPlayer(PlayerEntity entityIn) {
        if (entityIn != null && !entityIn.getActivePotionEffects().isEmpty() && !entityIn.isCreative()) {
            for (EffectInstance effect : entityIn.getActivePotionMap().values()) {
                if (effect != null && effect.getPotion().isBeneficial()) {
                    this.addPotionEffect(effect);
                }
            }
            entityIn.clearActivePotions();
        }
    }

    @Override
    public void onDeath(DamageSource cause) {
        for (EffectInstance effect : this.getActivePotionMap().values()) {
            if (effect != null) {
                AreaEffectCloudEntity effectCloud = new AreaEffectCloudEntity(this.world, this.posX + rand.nextInt(6) - 3, this.posY, this.posZ + rand.nextInt(6) - 3);
                effectCloud.setRadius(2.1f);
                effectCloud.setRadiusPerTick(-((float) effectCloud.getDuration() / 100000));
                effectCloud.addEffect(effect);
                world.addEntity(effectCloud);
            }
        }
        super.onDeath(cause);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_EVOKER_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_EVOKER_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_EVOKER_HURT;
    }
}
