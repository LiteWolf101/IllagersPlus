package com.litewolf101.illagers_plus.world;

import com.litewolf101.illagers_plus.IllagersPlus;
import com.litewolf101.illagers_plus.world.features.IllagerArcherTowerFeature;
import com.litewolf101.illagers_plus.world.features.IllagerTowerFeature;
import com.litewolf101.illagers_plus.world.pieces.IllagerArcherTowerPieces;
import com.litewolf101.illagers_plus.world.pieces.IllagerTowerPieces;
import com.litewolf101.illagers_plus.world.structures.IllagerArcherTowerStructure;
import com.litewolf101.illagers_plus.world.structures.IllagerTowerStructure;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.placement.ChanceConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.litewolf101.illagers_plus.IllagersPlus.MOD_ID;
import static net.minecraft.world.biome.Biome.LOGGER;
import static net.minecraft.world.gen.placement.Placement.CHANCE_HEIGHTMAP;

/*@ObjectHolder(IllagersPlus.MOD_ID)*/
public class StructureRegistry {

    //public static final Structure<NoFeatureConfig> ILLAGER_ARCHER_TOWER = new IllagerArcherTowerStructure(NoFeatureConfig::deserialize);

    /*@SubscribeEvent
    public static void registerStructures(IForgeRegistry<Feature<?>> event) {
        RegUtils.generic(event).add("illager_archer_tower", ILLAGER_ARCHER_TOWER);
        System.out.println("Structure has been added");
    }*/


    //public static Structure<?> ILLAGER_ARCHER_TOWER_STRUC = registerStructureFeature("illager_archer_tower", ILLAGER_ARCHER_TOWER);


    //public static final IStructurePieceType ILLAGER_ARCHER_TOWER_PIECE = Registry.register(Registry.STRUCTURE_PIECE, MOD_ID + ":illager_archer_tower_piece", IllagerArcherTowerPieces.Piece::new);
    public static final Feature<NoFeatureConfig> ILLAGER_ARCHER_TOWER = new IllagerArcherTowerFeature(NoFeatureConfig::deserialize, false);

    public static final Feature<NoFeatureConfig> ILLAGER_TOWER = new IllagerTowerFeature(NoFeatureConfig::deserialize, false);

    //public static final IStructurePieceType ILLAGER_TOWER_PIECE = Registry.register(Registry.STRUCTURE_PIECE,IllagersPlus.MOD_ID + ":illager_tower_piece", IllagerTowerPieces.Piece::new);
    //public static Structure<NoFeatureConfig> ILLAGER_TOWER = registerFeature(MOD_ID + ":illager_tower", new IllagerTowerStructure());

    /*public static void init() {
        //Feature.STRUCTURES.put("Illager Archer Tower", ILLAGER_ARCHER_TOWER);
        //Feature.STRUCTURES.put("Illager Tower", ILLAGER_ARCHER_TOWER);

        List<Biome> illagerArcherTowerBiomes = new ArrayList<>();
        illagerArcherTowerBiomes.add(Biomes.PLAINS);
        illagerArcherTowerBiomes.add(Biomes.SWAMP);
        illagerArcherTowerBiomes.add(Biomes.FOREST);
        illagerArcherTowerBiomes.add(Biomes.SNOWY_TAIGA);
        illagerArcherTowerBiomes.add(Biomes.TAIGA);
        illagerArcherTowerBiomes.add(Biomes.SAVANNA);
        illagerArcherTowerBiomes.add(Biomes.DESERT);
        illagerArcherTowerBiomes.add(Biomes.SNOWY_TUNDRA);

        for (Biome biome : Biome.BIOMES) {
            if (illagerArcherTowerBiomes.contains(biome)) {
                biome.addStructure(ILLAGER_ARCHER_TOWER, IFeatureConfig.NO_FEATURE_CONFIG);
                biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(ILLAGER_ARCHER_TOWER, new NoFeatureConfig(), CHANCE_HEIGHTMAP, new ChanceConfig(400)));
            }
            if (biome.hasStructure(ILLAGER_ARCHER_TOWER)) {
                LOGGER.info("Added[" + ILLAGER_ARCHER_TOWER + "]to biome: " + biome);
            }
        }
        if (!Registry.STRUCTURE_PIECE.containsKey(new ResourceLocation(MOD_ID, "illager_archer_tower_piece"))) {
            LOGGER.error(ILLAGER_ARCHER_TOWER_PIECE + "Is not in registry " + Registry.STRUCTURE_PIECE);
        }

        for (Biome biome : Biome.BIOMES) {
            if (biome == Biomes.PLAINS || biome == Biomes.DARK_FOREST) {
                biome.addStructure(ILLAGER_TOWER, IFeatureConfig.NO_FEATURE_CONFIG);
                biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(ILLAGER_TOWER, new NoFeatureConfig(), CHANCE_HEIGHTMAP, new ChanceConfig(250)));
            }
            if (biome.hasStructure(ILLAGER_TOWER)) {
                LOGGER.info("Added[" + ILLAGER_TOWER + "]to biome: " + biome);
            }
        }
        if (!Registry.STRUCTURE_PIECE.containsKey(new ResourceLocation(IllagersPlus.MOD_ID, "illager_tower_piece"))) {
            LOGGER.error(ILLAGER_TOWER_PIECE + "Is not in registry " + Registry.STRUCTURE_PIECE);
        }


    }

    private static <C extends IFeatureConfig, F extends Feature<C>> F registerFeature(String string_1, F feature_1) {
        return Registry.register(Registry.FEATURE, string_1, feature_1);
    }

    private static Structure<?> registerStructureFeature(String string_1, Structure<?> structureFeature_1) {
        return Registry.register(Registry.STRUCTURE_FEATURE, string_1.toLowerCase(Locale.ROOT), structureFeature_1);
    }*/

}
