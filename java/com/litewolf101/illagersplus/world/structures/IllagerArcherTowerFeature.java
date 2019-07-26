package com.litewolf101.illagersplus.world.structures;

import com.litewolf101.illagersplus.world.StructureRegistry;
import com.litewolf101.illagersplus.world.pieces.IllagerArcherTowerGenerator;
import com.mojang.datafixers.Dynamic;
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
import java.util.function.Function;

public class IllagerArcherTowerFeature extends ScatteredStructure<NoFeatureConfig> {

    public IllagerArcherTowerFeature() {
        super(NoFeatureConfig::deserialize);
    }

    @Override
    protected int getSeedModifier()
    {
        return 14357618;
    }

    @Override
    public boolean hasStartAt(ChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ) {
        return true;
    }

    @Override
    public IStartFactory getStartFactory() {
        return IllagerArcherTowerStructureStart::new;
    }

    @Override
    public String getStructureName() {
        return "illager-archer-tower";
    }

    @Override
    public int getSize() {
        return 5;
    }

    private class IllagerArcherTowerStructureStart extends StructureStart {
        public IllagerArcherTowerStructureStart(Structure<?> structureIn, int chunkX, int chunkZ, Biome biomeIn, MutableBoundingBox boundsIn, int referenceIn, long seed) {
            super(structureIn, chunkX, chunkZ, biomeIn, boundsIn, referenceIn, seed);
        }

        @Override
        public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn) {
            NoFeatureConfig noFeatureConfig = generator.getStructureConfig(biomeIn, StructureRegistry.ILLAGER_ARCHER_TOWER_FEAT);
            Rotation rotation = Rotation.values()[this.rand.nextInt(Rotation.values().length)];
            BlockPos blockpos = new BlockPos(chunkX * 16, 90, chunkZ * 16);
            IllagerArcherTowerGenerator.addParts(templateManagerIn, blockpos, rotation, this.components, this.rand, noFeatureConfig);
            this.recalculateStructureSize();
        }
    }
}
