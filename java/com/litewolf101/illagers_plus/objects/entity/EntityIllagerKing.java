package com.litewolf101.illagers_plus.objects.entity;

import com.litewolf101.illagers_plus.utils.INeedIllagerBoost;
import com.litewolf101.illagers_plus.utils.IllagerPlusLootTable;
import net.minecraft.enchantment.EnchantmentHelper;
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
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityIllagerKing extends EntityAbstractIllagerPlus implements INeedIllagerBoost {

    @SuppressWarnings("unchecked")
    public EntityIllagerKing(EntityType<? extends EntityIllagerKing> type, World world) {
        super(type, world);
        this.experienceValue = 40;
    }


    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new SwimGoal(this));
        goalSelector.addGoal(1, new HurtByTargetGoal(this, new Class[] {VindicatorEntity.class}));
        goalSelector.addGoal(2, new NearestAttackableTargetGoal(this, PlayerEntity.class, true));
        goalSelector.addGoal(3, new NearestAttackableTargetGoal(this, VillagerEntity.class, true));
        goalSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolemEntity.class, true));
        goalSelector.addGoal(4, new MeleeAttackGoal(this, 1D, false));
        goalSelector.addGoal(8, new RandomWalkingGoal(this, 0.2D));
        goalSelector.addGoal(9, new LookAtGoal(this, PlayerEntity.class, 3.0F, 1.0F));
        goalSelector.addGoal(10, new LookAtGoal(this, LivingEntity.class, 8.0F));
    }

    @Override
    protected ResourceLocation getLootTable()
    {
        return IllagerPlusLootTable.ILLAGER_KING;
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3499999940395355D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(75.0D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
    }


    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        ILivingEntityData ientitylivingdata = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        this.setEquipmentBasedOnDifficulty(difficultyIn);
        this.setEnchantmentBasedOnDifficulty(difficultyIn);
        return ientitylivingdata;
    }

    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
        ItemStack prettyHeavyStack = new ItemStack(Items.DIAMOND_AXE);
        EnchantmentHelper.addRandomEnchantment(this.rand, prettyHeavyStack, 2, false);
        this.setItemStackToSlot(EquipmentSlotType.MAINHAND, prettyHeavyStack);
        this.setItemStackToSlot(EquipmentSlotType.OFFHAND, new ItemStack(Items.SHIELD));
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

    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_VINDICATOR_AMBIENT;
    }

    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_VINDICATOR_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_VINDICATOR_HURT;
    }
}