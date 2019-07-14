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

public class EntityAbstractIllagerPlus extends AbstractRaiderEntity {
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

    /*@Override
    @SuppressWarnings("unchecked")
    public void tick() {
        super.tick();
        List<VillagerEntity> villagers = this.world.getEntitiesWithinAABB(VillagerEntity.class, this.getBoundingBox().grow(20d));
        List basicList = new ArrayList<>();
        for (VillagerEntity villager : villagers) {
            villager.goalSelector.addGoal(1, new AvoidEntityGoal(villager, EntityAbstractIllagerPlus.class, 8.0F, 0.6D, 1.0D));

            /*Class<GoalSelector> obj = GoalSelector.class;
            try {
                Field f = obj.getDeclaredField("goals");
                f.setAccessible(true);
                try {
                    Set<GoalSelector> test = (Set<GoalSelector>) f.get(obj);
                    List<GoalSelector> list = new ArrayList<GoalSelector>(test);
                    basicList.addAll(list);

                    if (!basicList.contains(new AvoidEntityGoal(villager, EntityAbstractIllagerPlus.class, 8.0F, 0.6D, 1.0D))) {
                        villager.goalSelector.addGoal(1, new AvoidEntityGoal(villager, EntityAbstractIllagerPlus.class, 8.0F, 0.6D, 1.0D));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            //List<EntityAITasks.EntityAITaskEntry> list = new ArrayList<EntityAITasks.EntityAITaskEntry>(test);
            //for (EntityAITasks.EntityAITaskEntry name : list) {
            //    basicList.add(name.action);
            //}
            //
        }
    }*/
}
