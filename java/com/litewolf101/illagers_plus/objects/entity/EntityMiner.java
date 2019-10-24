package com.litewolf101.illagers_plus.objects.entity;

import com.litewolf101.illagers_plus.utils.INeedIllagerBoost;
import com.litewolf101.illagers_plus.utils.IllagerPlusLootTable;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.VindicatorEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityMiner extends EntityAbstractIllagerPlus implements INeedIllagerBoost {

    public EntityMiner(EntityType<? extends EntityMiner> type, World world) {
        super(type, world);
        this.experienceValue = 7;
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
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(10.0D);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.6D);
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        int randNumb = this.rand.nextInt(100);
        Item pick = Items.COAL;
        if (randNumb <= 40) {
            pick = Items.WOODEN_PICKAXE;
        }
        if (randNumb > 40 && randNumb <= 65) {
            pick = Items.STONE_PICKAXE;
        }
        if (randNumb > 65 && randNumb <= 80) {
            pick = Items.IRON_PICKAXE;
        }
        if (randNumb > 80 && randNumb <= 97) {
            pick = Items.GOLDEN_PICKAXE;
        }
        if (randNumb > 97) {
            pick = Items.DIAMOND_PICKAXE;
        }
        this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(pick));
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    protected ResourceLocation getLootTable()
    {
        return IllagerPlusLootTable.ARCHER;
    }

    public boolean isOnSameTeam(Entity entityIn)
    {
        if (super.isOnSameTeam(entityIn))
        {
            return true;
        }
        else if (entityIn instanceof LivingEntity && ((LivingEntity)entityIn).getCreatureAttribute() == CreatureAttribute.ILLAGER)
        {
            return this.getTeam() == null && entityIn.getTeam() == null;
        }
        else
        {
            return false;
        }
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_VINDICATOR_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_VINDICATOR_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_VINDICATOR_HURT;
    }
}
