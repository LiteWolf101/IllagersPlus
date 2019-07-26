package com.litewolf101.illagersplus.objects.entity;

import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Stream;

public class EntityAbstractIllagerPlus extends AbstractIllagerEntity {
    //TODO add entity to raid spawn
    protected EntityAbstractIllagerPlus(EntityType<? extends EntityAbstractIllagerPlus> type, World world) {
        super(type, world);
    }

    /** This will make the Raid class recognize this entity as an illager and add it to the raid score.*/
    public void func_213660_a(int p_213660_1_, boolean p_213660_2_) {
    }

    @Override
    public SoundEvent getRaidLossSound() {
        return SoundEvents.ENTITY_EVOKER_CELEBRATE;
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.ILLAGER;
    }

    public boolean canBeLeader() {
        return false;
    }

    @Override
    public boolean canDespawn(double distanceToClosestPlayer) {
        return false;
    }

    public boolean func_213658_ej() {
        return true;
    }

    protected void registerGoals() {
        super.registerGoals();
    }
}
