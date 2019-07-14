package com.litewolf101.objects.entities;

import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class EntityAbstractIllagerPlus extends EntityMob implements IMob {
    public EntityAbstractIllagerPlus(World worldIn) {
        super(worldIn);
    }


    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ILLAGER;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        List<EntityVillager> villagers = this.world.getEntitiesWithinAABB(EntityVillager.class, this.getEntityBoundingBox().grow(20d));
        List<EntityAIBase> basicList = new ArrayList<EntityAIBase>();
        for (EntityVillager villager : villagers) {
            Set<EntityAITasks.EntityAITaskEntry> test = villager.tasks.taskEntries;
            List<EntityAITasks.EntityAITaskEntry> list = new ArrayList<EntityAITasks.EntityAITaskEntry>(test);
            for (EntityAITasks.EntityAITaskEntry name : list) {
                basicList.add(name.action);
            }
            if (!basicList.contains(new EntityAIAvoidEntity(villager, EntityAbstractIllagerPlus.class, 8.0F, 0.6D, 1.0D))) {
                villager.tasks.addTask(1, new EntityAIAvoidEntity(villager, EntityAbstractIllagerPlus.class, 8.0F, 0.6D, 1.0D));
            }
        }
    }
}
