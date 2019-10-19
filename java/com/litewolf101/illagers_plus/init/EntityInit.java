package com.litewolf101.illagers_plus.init;

import com.google.common.collect.Lists;
import com.litewolf101.illagers_plus.IllagersPlus;
import com.litewolf101.illagers_plus.objects.entity.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import java.util.List;

@Mod.EventBusSubscriber(modid = IllagersPlus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
//@ObjectHolder(IllagersPlus.MOD_ID)
@SuppressWarnings("rawtypes")
public class EntityInit {
    private static List<EntityType> ENTITIES = Lists.newArrayList();
    private static List<Item> SPAWN_EGGS = Lists.newArrayList();

    public static final EntityType<EntityArcher> ARCHER = createEntity(EntityArcher.class, EntityArcher::new, EntityClassification.MONSTER, "archer", 0.6F, 1.95F, 9804699, 1973274);
    public static final EntityType<EntityEnchanter> ENCHANTER = createEntity(EntityEnchanter.class, EntityEnchanter::new, EntityClassification.MONSTER, "enchanter", 0.7F, 2F, 9804699, 1120750);
    public static final EntityType<EntityFurantur> FURANTUR = createEntity(EntityFurantur.class, EntityFurantur::new, EntityClassification.MONSTER, "furantur", 0.7F, 2F, 9804699, 1855266);
    public static final EntityType<EntityHoarder> HOARDER = createEntity(EntityHoarder.class, EntityHoarder::new, EntityClassification.MONSTER, "hoarder", 0.7F, 2F, 9804699, 7158918);
    public static final EntityType<EntityIllagerKing> ILLAGER_KING = createEntity(EntityIllagerKing.class, EntityIllagerKing::new, EntityClassification.MONSTER, "illager_king", 1.65F, 4F, 9804699, 15787008);
    public static final EntityType<EntityNecromancer> NECROMANCER = createEntity(EntityNecromancer.class, EntityNecromancer::new, EntityClassification.MONSTER, "necromancer", 0.7F, 2F, 9804699, 0);


    private static <T extends Entity> EntityType<T> createEntity(Class<T> entityClass, EntityType.IFactory<T> factory, EntityClassification entityClassification, String name, float width, float height, int eggPrimary, int eggSecondary) {
        ResourceLocation location = new ResourceLocation(IllagersPlus.MOD_ID, name);

        EntityType<T> entity = EntityType.Builder.create(factory, entityClassification)
                .size(width, height).setTrackingRange(64)
                .setShouldReceiveVelocityUpdates(true)
                .setUpdateInterval(3)
                .build(location.toString());
        entity.setRegistryName(location);
        ENTITIES.add(entity);
        SPAWN_EGGS.add(createSpawnEggForEntity(entity, eggPrimary, eggSecondary, IllagersPlus.ILLAGERS_PLUS));
        return entity;
    }

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
        for (EntityType entity : ENTITIES) {
            event.getRegistry().register(entity);
        }

        //EntitySpawnPlacementRegistry.register(ARCHER, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::func_223325_c);
    }

    @SubscribeEvent
    public static void registerSpawnEggs(RegistryEvent.Register<Item> event) {
        for (Item spawnEgg : SPAWN_EGGS) {
            event.getRegistry().register(spawnEgg);
        }
    }

    public static Item createSpawnEggForEntity(@SuppressWarnings("rawtypes") EntityType entityType, int eggColor1, int eggColor2, net.minecraft.item.ItemGroup itemGroup) {
        return new SpawnEggItem(entityType, eggColor1, eggColor2, new Item.Properties().group(itemGroup)).setRegistryName(entityType.getRegistryName() + "_spawn_egg");
    }







    /*public static final EntityType<EntityArcher> ARCHER = EntityType.Builder.create(EntityArcher::new, EntityClassification.MONSTER)
            .setTrackingRange(80)
            .setUpdateInterval(3)
            .setShouldReceiveVelocityUpdates(true)
            .size(0.6F, 1.95F)
            .build(IllagersPlus.MOD_ID + ":archer");

    public static final EntityType<EntityEnchanter> ENCHANTER = EntityType.Builder.create(EntityEnchanter::new, EntityClassification.MONSTER)
            .setTrackingRange(80)
            .setUpdateInterval(3)
            .setShouldReceiveVelocityUpdates(true)
            .size(0.7F, 2F)
            .build(IllagersPlus.MOD_ID + ":enchanter");

    public static final EntityType<EntityFurantur> FURANTUR = EntityType.Builder.create(EntityFurantur::new, EntityClassification.MONSTER)
            .setTrackingRange(80)
            .setUpdateInterval(3)
            .setShouldReceiveVelocityUpdates(true)
            .size(0.7F, 2F)
            .build(IllagersPlus.MOD_ID + ":furantur");

    public static final EntityType<EntityHoarder> HOARDER = EntityType.Builder.create(EntityHoarder::new, EntityClassification.MONSTER)
            .setTrackingRange(80)
            .setUpdateInterval(3)
            .setShouldReceiveVelocityUpdates(true)
            .size(0.7F, 2F)
            .build(IllagersPlus.MOD_ID + ":hoarder");

    public static final EntityType<EntityIllagerKing> ILLAGER_KING = EntityType.Builder.create(EntityIllagerKing::new, EntityClassification.MONSTER)
            .setTrackingRange(80)
            .setUpdateInterval(3)
            .setShouldReceiveVelocityUpdates(true)
            .size(0.7F, 2F)
            .build(IllagersPlus.MOD_ID + ":illager_king");

    public static final EntityType<EntityNecromancer> NECROMANCER = EntityType.Builder.create(EntityNecromancer::new, EntityClassification.MONSTER)
            .setTrackingRange(80)
            .setUpdateInterval(3)
            .setShouldReceiveVelocityUpdates(true)
            .size(0.7F, 2F)
            .build(IllagersPlus.MOD_ID + ":necromancer");

    @SubscribeEvent
    public static void onRegisterEntities (final RegistryEvent.Register<EntityType<?>> event) {
        ARCHER.setRegistryName(IllagersPlus.MOD_ID, "archer");
        event.getRegistry().register(ARCHER);

        ENCHANTER.setRegistryName(IllagersPlus.MOD_ID, "enchanter");
        event.getRegistry().register(ENCHANTER);

        FURANTUR.setRegistryName(IllagersPlus.MOD_ID, "furantur");
        event.getRegistry().register(FURANTUR);

        HOARDER.setRegistryName(IllagersPlus.MOD_ID, "hoarder");
        event.getRegistry().register(HOARDER);

        ILLAGER_KING.setRegistryName(IllagersPlus.MOD_ID, "illager_king");
        event.getRegistry().register(ILLAGER_KING);

        NECROMANCER.setRegistryName(IllagersPlus.MOD_ID, "necromancer");
        event.getRegistry().register(NECROMANCER);
    }*/




    /*public static Item entity_egg_item;
    //Entities
    public static final EntityType<EntityArcher> ARCHER = createEntity(EntityArcher.class, EntityArcher::new, EntityClassification.MONSTER,"archer",0.6F, 1.95F);
    public static final EntityType<EntityEnchanter> ENCHANTER = createEntity(EntityEnchanter.class, EntityEnchanter::new, EntityClassification.MONSTER,"enchanter",0.7F, 2F);
    public static final EntityType<EntityFurantur> FURANTUR = createEntity(EntityFurantur.class, EntityFurantur::new, EntityClassification.MONSTER,"furantur",0.7F, 2F);
    public static final EntityType<EntityHoarder> HOARDER = createEntity(EntityHoarder.class, EntityHoarder::new, EntityClassification.MONSTER,"hoarder",0.7F, 2F);
    public static final EntityType<EntityIllagerKing> ILLAGER_KING = createEntity(EntityIllagerKing.class, EntityIllagerKing::new, EntityClassification.MONSTER,"illager_king",1F, 4.15F);
    public static final EntityType<EntityNecromancer> NECROMANCER = createEntity(EntityNecromancer.class, EntityNecromancer::new, EntityClassification.MONSTER,"necromancer",0.6F, 1.95F);

    private static <T extends Entity> EntityType<T> createEntity(Class<T> entityClass, EntityType.IFactory<T> factory, EntityClassification entityClassification, String name, float width, float height) {
        ResourceLocation location = new ResourceLocation(IllagersPlus.MOD_ID + ":" + name);

        EntityType<T> entity = EntityType.Builder.create(factory, entityClassification).size(width, height).build(location.toString());
        entity.setRegistryName(location);

        return entity;
    }

    @SubscribeEvent
    public static void registerEntity(IForgeRegistry<EntityType<?>> event) {

        event.register(ARCHER);

    }

    private static String prefix(String path) {

        return IllagersPlus.MOD_ID + "." + path;

    }

    //Spawn Eggs
    public static void registerSpawnEggs(final RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                entity_egg_item = registerSpawnEgg(ARCHER, "archer", 9804699, 1973274),
                entity_egg_item = registerSpawnEgg(ENCHANTER, "enchanter", 9804699, 1120750),
                entity_egg_item = registerSpawnEgg(FURANTUR, "furantur", 9804699, 1855266),
                entity_egg_item = registerSpawnEgg(HOARDER, "hoarder", 9804699, 7158918),
                entity_egg_item = registerSpawnEgg(ILLAGER_KING, "illager_king", 9804699, 15787008),
                entity_egg_item = registerSpawnEgg(NECROMANCER, "necromancer", 9804699, 0)
        );

    }

    public static Item registerSpawnEgg(EntityType<?> entityType, String name, int primaryClr, int secondaryClr){
        SpawnEggItem egg = new SpawnEggItem(entityType, primaryClr, secondaryClr, new Item.Properties().group(IllagersPlus.ILLAGERS_PLUS));
        egg.setRegistryName(IllagersPlus.MOD_ID, name + "_egg");

        return egg;
    }*/
}
