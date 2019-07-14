package com.litewolf101.illagersplus.utils;

import com.google.common.collect.Sets;
import com.litewolf101.illagersplus.IllagersPlus;
import net.minecraft.data.LootTableProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTables;

import java.util.Set;

public class IllagerPlusLootTable extends LootTables {
    private static final Set<ResourceLocation> ILLAGERS_PLUS_LOOT_TABLES = Sets.newHashSet();
    //Structures
    public static final ResourceLocation ILLAGER_TOWER_COMMON = register("structure/illager_tower_common");
    public static final ResourceLocation ILLAGER_TOWER_UNCOMMON = register("structure/illager_tower_uncommon");
    public static final ResourceLocation ILLAGER_TOWER_RARE = register("structure/illager_tower_rare");
    public static final ResourceLocation ILLAGER_TOWER_RESEARCHER = register("structure/illager_tower_researcher");
    public static final ResourceLocation ILLAGER_TOWER_TREES = register("structure/illager_tower_trees");
    public static final ResourceLocation ILLAGER_TOWER_TNT_TRAP = register("structure/illager_tower_tnt_trap");
    public static final ResourceLocation ILLAGER_TOWER_ROOM_FARMS = register("structure/illager_tower_room_farms");
    public static final ResourceLocation ILLAGER_TOWER_ROOM_MECHANICS = register("structure/illager_tower_room_mechanics");
    public static final ResourceLocation ILLAGER_TOWER_ROOM_ARMORY = register("structure/illager_tower_room_armory");
    public static final ResourceLocation ILLAGER_TOWER_ROOM_BREWERY = register("structure/illager_tower_room_brewery");
    public static final ResourceLocation ILLAGER_ARCHER_TOWER = register("structure/illager_archer_tower");
    public static final ResourceLocation ILLAGER_CENTRE = register("structure/illager_centre");

    //Entities
    public static final ResourceLocation ARCHER = register("entity/archer");
    public static final ResourceLocation ENCHANTER = register("entity/enchanter");
    public static final ResourceLocation FURANTUR = register("entity/furantur");
    public static final ResourceLocation ILLAGER_KING = register("entity/illager_king");

    private static ResourceLocation register(String id) {
        return register(new ResourceLocation(IllagersPlus.MOD_ID, id));
    }

    private static ResourceLocation register(ResourceLocation id) {
        if (ILLAGERS_PLUS_LOOT_TABLES.add(id)) {
            return id;
        } else {
            throw new IllegalArgumentException(id + " is already a registered built-in loot table");
        }
    }
}
