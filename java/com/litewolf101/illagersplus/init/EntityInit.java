package com.litewolf101.illagersplus.init;

import com.litewolf101.illagersplus.IllagersPlus;
import com.litewolf101.illagersplus.objects.entity.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class EntityInit {
    public static Item entity_egg_item;
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
    }
}
