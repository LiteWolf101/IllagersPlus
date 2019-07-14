package com.litewolf101.illagersplus.init;

import com.litewolf101.illagersplus.IllagersPlus;
import com.litewolf101.illagersplus.objects.entity.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;

public class EntityInit {
    public static Item entity_egg_item;
    //Entities
    public static EntityType<?> ARCHER = register("archer", EntityType.Builder.create(EntityArcher::new, EntityClassification.MONSTER).size(0.6F, 1.95F));
    public static EntityType<?> ENCHANTER = register("enchanter", EntityType.Builder.create(EntityEnchanter::new, EntityClassification.MONSTER).size(0.7F, 2F));
    public static EntityType<?> FURANTUR = register("furantur", EntityType.Builder.create(EntityFurantur::new, EntityClassification.MONSTER).size(0.7F, 2F));
    public static EntityType<?> HOARDER = register("hoarder", EntityType.Builder.create(EntityHoarder::new, EntityClassification.MONSTER).size(0.7F, 2F));
    public static EntityType<?> ILLAGER_KING = register("illager_king", EntityType.Builder.create(EntityIllagerKing::new, EntityClassification.MONSTER).size(1f, 4.15f));
    public static EntityType<?> NECROMANCER = register("necromancer", EntityType.Builder.create(EntityNecromancer::new, EntityClassification.MONSTER).size(0.6F, 1.95F));


    private static <T extends Entity> EntityType<T> register(String key, EntityType.Builder<T> builder) {
        return (EntityType<T>) Registry.register(Registry.ENTITY_TYPE, key, builder.build(key).setRegistryName(IllagersPlus.MOD_ID, key));
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
