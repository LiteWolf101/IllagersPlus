package com.litewolf101.objects.entities;

import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.world.World;

public class EntityAbstractIllagerPlus extends EntityMob implements IMob {
    public EntityAbstractIllagerPlus(World worldIn) {
        super(worldIn);
    }


    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ILLAGER;
    }
}
