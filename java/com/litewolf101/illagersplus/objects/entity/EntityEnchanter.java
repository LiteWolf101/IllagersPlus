package com.litewolf101.illagersplus.objects.entity;
import com.litewolf101.illagersplus.utils.INeedIllagerBoost;
import com.litewolf101.illagersplus.utils.IllagerPlusLootTable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.PatrollerEntity;
import net.minecraft.entity.monster.VindicatorEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.List;

public class EntityEnchanter extends EntityAbstractIllagerPlus implements INeedIllagerBoost {
    private int beat;

    @SuppressWarnings("unchecked")
    public EntityEnchanter(EntityType<? extends EntityEnchanter> type, World world) {
        super(type, world);
    }

    @Override
    protected ResourceLocation getLootTable()
    {
        return IllagerPlusLootTable.ENCHANTER;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new SwimGoal(this));
        goalSelector.addGoal(7, new AvoidEntityGoal(this, PlayerEntity.class, 2.0F, 0.6D, 0.6D));
        goalSelector.addGoal(8, new RandomWalkingGoal(this, 0.6D));
        goalSelector.addGoal(9, new LookAtGoal(this, PlayerEntity.class, 3.0F, 1.0F));
        goalSelector.addGoal(10, new LookAtGoal(this, LivingEntity.class, 8.0F));
        targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[]{VindicatorEntity.class}));
        targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[]{EntityFurantur.class}));
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_ILLUSIONER_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_PILLAGER_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_ILLUSIONER_HURT;
    }

    @Override
    public void tick() {
        this.beat++;
        List<LivingEntity> entities = this.world.getEntitiesWithinAABB(LivingEntity.class, this.getBoundingBox().grow(32));

        if (this.beat % 200 == 0) {
            for (LivingEntity entityLiving : entities) {
                if (entityLiving instanceof PatrollerEntity || entityLiving instanceof INeedIllagerBoost) {
                    entityLiving.addPotionEffect(new EffectInstance(Effects.STRENGTH, 100, 0));
                    world.playSound(null, entityLiving.getPosition(), SoundEvents.ENTITY_EVOKER_PREPARE_SUMMON, SoundCategory.HOSTILE, 0.2f, 1);
                }
                if (entityLiving instanceof VillagerEntity) {
                    entityLiving.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 50, 2));
                    world.playSound(null, entityLiving.getPosition(), SoundEvents.ENTITY_EVOKER_PREPARE_SUMMON, SoundCategory.HOSTILE, 0.2f, 1);
                }
                if (entityLiving instanceof PlayerEntity) {
                    entityLiving.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 50, 3));
                    world.playSound(null, entityLiving.getPosition(), SoundEvents.ENTITY_EVOKER_PREPARE_SUMMON, SoundCategory.HOSTILE, 0.2f, 1);
                }
            }
        }

        if (this.beat % 10 == 0) {
            this.world.playSound(null, this.getPosition(), SoundEvents.BLOCK_NOTE_BLOCK_BASEDRUM, SoundCategory.HOSTILE, 0.5f, 0.1f);
            this.world.addParticle(ParticleTypes.NOTE, this.posX, this.posY + 2f, this.posZ, 0, 0, 0);
        }

        super.tick();
    }
}
