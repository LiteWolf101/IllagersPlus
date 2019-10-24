package com.litewolf101.illagers_plus.world;

import com.litewolf101.illagers_plus.world.structureConfig.IllagerMineConfig;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;

public class AddBiomeDecoConfig {
    public static void addIllagerArcherTower(Biome biome) {
        biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(StructureRegistry.ILLAGER_ARCHER_TOWER, IFeatureConfig.NO_FEATURE_CONFIG, Placement.NOPE, IPlacementConfig.NO_PLACEMENT_CONFIG));
        biome.addStructure(StructureRegistry.ILLAGER_ARCHER_TOWER, IFeatureConfig.NO_FEATURE_CONFIG);
    }

    public static void addIllagerMine(Biome biome) {
        biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(StructureRegistry.ILLAGER_MINE, new IllagerMineConfig(0.017D), Placement.NOPE, IPlacementConfig.NO_PLACEMENT_CONFIG));
        biome.addStructure(StructureRegistry.ILLAGER_MINE, new IllagerMineConfig(0.017D));
    }
}
