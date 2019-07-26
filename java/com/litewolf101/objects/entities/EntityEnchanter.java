package com.litewolf101.objects.entities;

import com.litewolf101.utils.INeedIllagerBoost;
import com.litewolf101.utils.IllagerPlusLootTable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.AbstractIllager;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.List;

public class EntityEnchanter extends EntityAbstractIllagerPlus implements INeedIllagerBoost {
    int beat;
    public EntityEnchanter(World worldIn) {
        super(worldIn);
        this.setSize(0.7F, 2F);
    }

    @Override
    protected ResourceLocation getLootTable()
    {
        return IllagerPlusLootTable.ENCHANTER;
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(7, new EntityAIAvoidEntity(this, EntityPlayer.class, 2.0F, 0.6D, 0.6D));
        this.tasks.addTask(8, new EntityAIWander(this, 0.6D));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 3.0F, 1.0F));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[]{EntityVindicator.class}));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[]{EntityFurantur.class}));
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_ILLUSION_ILLAGER_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_ILLAGER_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_ILLUSION_ILLAGER_HURT;
    }

    @Override
    public void onUpdate() {
        this.beat++;
        List<EntityLiving> entities = this.world.getEntitiesWithinAABB(EntityLiving.class, this.getEntityBoundingBox().grow(32));

        if (this.beat % 200 == 0) {
            for (EntityLiving entityLiving : entities) {
                if (entityLiving instanceof AbstractIllager || entityLiving instanceof INeedIllagerBoost) {
                    entityLiving.addPotionEffect(new PotionEffect(Potion.getPotionById(5), 100, 0));
                    world.playSound(null, entityLiving.getPosition(), SoundEvents.EVOCATION_ILLAGER_PREPARE_SUMMON, SoundCategory.HOSTILE, 0.2f, 1);
                }
                if (entityLiving instanceof EntityVillager) {
                    entityLiving.addPotionEffect(new PotionEffect(Potion.getPotionById(2), 50, 2));
                    world.playSound(null, entityLiving.getPosition(), SoundEvents.EVOCATION_ILLAGER_PREPARE_SUMMON, SoundCategory.HOSTILE, 0.2f, 1);
                }
            }
        }

        if (this.beat % 10 == 0) {
            this.world.playSound(null, this.getPosition(), SoundEvents.BLOCK_NOTE_BASEDRUM, SoundCategory.HOSTILE, 0.5f, 0.1f);
            this.world.spawnParticle(EnumParticleTypes.NOTE, this.posX, this.posY + 2f, this.posZ, 0, 0, 0);
        }

        super.onUpdate();
    }
}
