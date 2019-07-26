package com.litewolf101.illagersplus.world;

import com.litewolf101.illagersplus.IllagersPlus;
import com.litewolf101.illagersplus.world.pieces.IllagerArcherTowerGenerator;
import com.litewolf101.illagersplus.world.structures.IllagerArcherTowerFeature;
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
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.placement.ChanceConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static net.minecraft.world.biome.Biome.LOGGER;
import static net.minecraft.world.gen.placement.Placement.CHANCE_HEIGHTMAP;

public class StructureRegistry {
    public static Structure<NoFeatureConfig> ILLAGER_ARCHER_TOWER_FEAT = registerFeature(IllagersPlus.MOD_ID + ":illager_archer_tower", new IllagerArcherTowerFeature());
    public static final IStructurePieceType ILLAGER_ARCHER_TOWER_PIECE = Registry.register(Registry.STRUCTURE_PIECE,IllagersPlus.MOD_ID + ":illager_archer_tower_piece", IllagerArcherTowerGenerator.Piece::new);
    public static Structure<?> ILLAGER_ARCHER_TOWER_STRUC = registerStructureFeature("illager_archer_tower", ILLAGER_ARCHER_TOWER_FEAT);

    public static void init() {
        Feature.STRUCTURES.put("Illager Archer Tower", ILLAGER_ARCHER_TOWER_FEAT);

        List<Biome> illagerArcherTowerBiomes = new ArrayList<>();
        illagerArcherTowerBiomes.add(Biomes.PLAINS);
        illagerArcherTowerBiomes.add(Biomes.FOREST);
        illagerArcherTowerBiomes.add(Biomes.SNOWY_TAIGA);
        illagerArcherTowerBiomes.add(Biomes.TAIGA);
        illagerArcherTowerBiomes.add(Biomes.SAVANNA);
        illagerArcherTowerBiomes.add(Biomes.DESERT);
        illagerArcherTowerBiomes.add(Biomes.SNOWY_TUNDRA);

        for (Biome biome : Biome.BIOMES) {
            if (illagerArcherTowerBiomes.contains(biome)) {
                biome.addStructure(ILLAGER_ARCHER_TOWER_FEAT, new NoFeatureConfig());
                biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(ILLAGER_ARCHER_TOWER_FEAT, new NoFeatureConfig(), CHANCE_HEIGHTMAP, new ChanceConfig(250)));
            }
            if (biome.hasStructure(ILLAGER_ARCHER_TOWER_STRUC)) {
                LOGGER.info("Added[" + ILLAGER_ARCHER_TOWER_STRUC + "]to biome: " + biome);
            }
        }

        if (!Registry.STRUCTURE_PIECE.containsKey(new ResourceLocation(IllagersPlus.MOD_ID, "illager_archer_tower_piece"))) {
            LOGGER.error(ILLAGER_ARCHER_TOWER_PIECE + "Is not in registry " + Registry.STRUCTURE_PIECE);
        }
    }

    private static <C extends IFeatureConfig, F extends Feature<C>> F registerFeature(String string_1, F feature_1) {
        return Registry.register(Registry.FEATURE, string_1, feature_1);
    }

    private static Structure<?> registerStructureFeature(String string_1, Structure<?> structureFeature_1) {
        return Registry.register(Registry.STRUCTURE_FEATURE, string_1.toLowerCase(Locale.ROOT), structureFeature_1);
    }
}
