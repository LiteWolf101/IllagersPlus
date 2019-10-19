package com.litewolf101.illagers_plus.objects.entity;

import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

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
