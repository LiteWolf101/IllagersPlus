package com.litewolf101.illagers_plus.world.structures;

import com.litewolf101.illagers_plus.world.StructureRegistry;
import com.litewolf101.illagers_plus.world.pieces.IllagerTowerPieces;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.ScatteredStructure;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.Random;

public class IllagerTowerStructure extends ScatteredStructure<NoFeatureConfig> {

    public IllagerTowerStructure() {
        super(NoFeatureConfig::deserialize);
    }

    @Override
    public boolean hasStartAt(ChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ) {
        return true;
    }

    @Override
    public IStartFactory getStartFactory() {
        return IllagerTowerStructure.Start::new;
    }


    @Override
    protected int getSeedModifier() {
        return 14357992;
    }

    @Override
    public String getStructureName() {
        return "illagers_plus:Illager_Tower";
    }

    @Override
    public int getSize() {
        return 8;
    }

    private class Start extends StructureStart {
        public Start(Structure<?> structureIn, int chunkX, int chunkZ, Biome biomeIn, MutableBoundingBox boundsIn, int referenceIn, long seed) {
            super(structureIn, chunkX, chunkZ, biomeIn, boundsIn, referenceIn, seed);
        }

        @Override
        public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn) {
            //NoFeatureConfig nofeatureconfig = (NoFeatureConfig)generator.getStructureConfig(biomeIn, StructureRegistry.ILLAGER_TOWER);
            int i = chunkX * 16;
            int j = chunkZ * 16;
            BlockPos blockpos = new BlockPos(i, 90, j);
            Rotation rotation = Rotation.values()[this.rand.nextInt(Rotation.values().length)]; //TODO: Fix rotation issues
            //IllagerTowerPieces.addParts(templateManagerIn, blockpos, Rotation.NONE, this.components, this.rand, nofeatureconfig);
            this.recalculateStructureSize();
        }
    }
}
