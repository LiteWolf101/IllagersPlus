package com.litewolf101.illagers_plus.world;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.HeightWithChanceConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;

public class AddBiomeDecoConfig {
    public static void addIllagerArcherTower(Biome biome) {
        biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(StructureRegistry.ILLAGER_ARCHER_TOWER, IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_CHANCE_HEIGHTMAP, new HeightWithChanceConfig(1, 0.1F)));
    }

    public static void addIllagerTower(Biome biome) {
        biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(StructureRegistry.ILLAGER_TOWER, IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_CHANCE_HEIGHTMAP, new HeightWithChanceConfig(1, 0.1F)));
    }
}
