package com.litewolf101.illagersplus.event;

import com.litewolf101.illagersplus.init.EntityInit;
import com.litewolf101.illagersplus.objects.entity.*;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.PillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.DifficultyInstance;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class MakeIllagersPlusActuallyEvilEvent {
    @SubscribeEvent
    public void onEntityJoin(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof VillagerEntity) {
            VillagerEntity villager = (VillagerEntity) event.getEntity();
            villager.goalSelector.addGoal(1, new AvoidEntityGoal(villager, EntityAbstractIllagerPlus.class, 16.0F, 0.8D, 0.8D));
        }

        if (event.getEntity() instanceof PillagerEntity) {
            PillagerEntity pillager = (PillagerEntity) event.getEntity();

            if (pillager.func_213658_ej() && pillager.isRaidActive()) {
                DifficultyInstance difficultyinstance = event.getWorld().getDifficultyForLocation(pillager.getPosition());

                int j = difficultyinstance.getDifficulty().getId();

                if (event.getWorld().rand.nextInt(18 - j) == 0) {
                    for (int i = 0; i < 2 + event.getWorld().rand.nextInt(2); ++i) {
                        EntityArcher archer = EntityInit.ARCHER.create(event.getWorld());
                        archer.setPosition((double) pillager.getPosition().getX() + 0.5D, (double) pillager.getPosition().getY() + 1.0D, (double) pillager.getPosition().getZ() + 0.5D);
                        archer.onInitialSpawn(event.getWorld(), event.getWorld().getDifficultyForLocation(pillager.getPosition()), SpawnReason.EVENT, null, null);
                        archer.onGround = true;
                        event.getWorld().addEntity(archer);
                    }
                }

                if (event.getWorld().rand.nextInt(15 - j) == 0) {
                    for (int i = 0; i < 2 + event.getWorld().rand.nextInt(3); ++i) {
                        EntityFurantur furantur = EntityInit.FURANTUR.create(event.getWorld());
                        furantur.setPosition((double) pillager.getPosition().getX() + 0.5D, (double) pillager.getPosition().getY() + 1.0D, (double) pillager.getPosition().getZ() + 0.5D);
                        furantur.onInitialSpawn(event.getWorld(), event.getWorld().getDifficultyForLocation(pillager.getPosition()), SpawnReason.EVENT, null, null);
                        furantur.onGround = true;
                        event.getWorld().addEntity(furantur);
                    }
                }

                if (pillager.getRaid().func_221315_l() > 1) {
                    if (event.getWorld().rand.nextInt(25 - j) == 0) {
                        for (int i = 0; i < 1 + event.getWorld().rand.nextInt(2); ++i) {
                            EntityEnchanter enchanter = EntityInit.ENCHANTER.create(event.getWorld());
                            enchanter.setPosition((double) pillager.getPosition().getX() + 0.5D, (double) pillager.getPosition().getY() + 1.0D, (double) pillager.getPosition().getZ() + 0.5D);
                            enchanter.onInitialSpawn(event.getWorld(), event.getWorld().getDifficultyForLocation(pillager.getPosition()), SpawnReason.EVENT, null, null);
                            enchanter.onGround = true;
                            event.getWorld().addEntity(enchanter);
                        }
                    }
                }

                if (pillager.getRaid().func_221315_l() > 3) {
                    if (event.getWorld().rand.nextInt(50 - j) == 0) {
                        EntityNecromancer necromancer = EntityInit.NECROMANCER.create(event.getWorld());
                        necromancer.setPosition((double) pillager.getPosition().getX() + 0.5D, (double) pillager.getPosition().getY() + 1.0D, (double) pillager.getPosition().getZ() + 0.5D);
                        necromancer.onInitialSpawn(event.getWorld(), event.getWorld().getDifficultyForLocation(pillager.getPosition()), SpawnReason.EVENT, null, null);
                        necromancer.onGround = true;
                        event.getWorld().addEntity(necromancer);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void debugStructureLocation (TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        if (player != null) {
            //System.out.println(STRUCTURES.values());
        }

    }
}
