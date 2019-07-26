package com.litewolf101.events;

import com.litewolf101.objects.entities.EntityAbstractIllagerPlus;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class MakeVillagersScaredEvent {
    @SubscribeEvent
    public static void scareVillagers(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof EntityVillager) {
            ((EntityVillager)event.getEntity()).tasks.addTask(1, new EntityAIAvoidEntity(((EntityVillager)event.getEntity()), EntityAbstractIllagerPlus.class, 10.0F, 0.6D, 1.0D));
        }
    }
}
