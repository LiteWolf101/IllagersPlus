package com.litewolf101.illagers_plus.world.structures;

import com.google.common.collect.Lists;
import com.litewolf101.illagers_plus.init.EntityInit;
import com.litewolf101.illagers_plus.world.StructureRegistry;
import com.litewolf101.illagers_plus.world.pieces.IllagerArcherTowerPieces;
import com.mojang.datafixers.Dynamic;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.IglooPieces;
import net.minecraft.world.gen.feature.structure.ScatteredStructure;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.List;
import java.util.Random;

public class IllagerArcherTowerStructure extends ScatteredStructure<NoFeatureConfig> {
    private static final List<Biome.SpawnListEntry> spawnList = Lists.newArrayList(new Biome.SpawnListEntry(EntityInit.ARCHER, 1, 1, 1));

    public IllagerArcherTowerStructure() {
        super(NoFeatureConfig::deserialize);
    }

    @Override
    protected int getSeedModifier() {
        return 14357618;
    }

    @Override
    public Structure.IStartFactory getStartFactory() {
        return IllagerArcherTowerStructure.Start::new;
    }

    @Override
    public String getStructureName() {
        return "illagers_plus:Illager_Archer_Tower";
    }

    @Override
    public int getSize() {
        return 5;
    }

    public List<Biome.SpawnListEntry> getSpawnList() {
        return spawnList;
    }

    public boolean hasStartAt(ChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ) {
        return true;
    }

    public static class Start extends StructureStart {
        public Start(Structure<?> structureIn, int chunkX, int chunkZ, Biome biomeIn, MutableBoundingBox boundsIn, int referenceIn, long seed) {
            super(structureIn, chunkX, chunkZ, biomeIn, boundsIn, referenceIn, seed);
        }

        @Override
        public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn) {
            //NoFeatureConfig nofeatureconfig = (NoFeatureConfig)generator.getStructureConfig(biomeIn, StructureRegistry.ILLAGER_ARCHER_TOWER);
            int i = chunkX * 16;
            int j = chunkZ * 16;
            BlockPos blockpos = new BlockPos(i, 90, j);
            Rotation rotation = Rotation.values()[this.rand.nextInt(Rotation.values().length)]; //TODO: Fix rotation issues
            //IllagerArcherTowerPieces.addTowerPieces(templateManagerIn, blockpos, Rotation.NONE, this.components, this.rand, nofeatureconfig);
            this.recalculateStructureSize();
        }
    }
}
