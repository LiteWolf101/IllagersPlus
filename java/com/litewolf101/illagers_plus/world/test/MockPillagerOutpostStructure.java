package com.litewolf101.illagers_plus.world.test;

import com.google.common.collect.Lists;
import com.mojang.datafixers.Dynamic;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.*;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class MockPillagerOutpostStructure extends ScatteredStructure<MockPillagerOutpostConfig> {
    private static final List<Biome.SpawnListEntry> field_214558_a = Lists.newArrayList(new Biome.SpawnListEntry(EntityType.PILLAGER, 1, 1, 1));

    public MockPillagerOutpostStructure(Function<Dynamic<?>, ? extends MockPillagerOutpostConfig> p_i51470_1_) {
        super(p_i51470_1_);
    }

    public String getStructureName() {
        return "Mock_Pillager_Outpost";
    }

    public int getSize() {
        return 3;
    }

    public List<Biome.SpawnListEntry> getSpawnList() {
        return field_214558_a;
    }

    public boolean hasStartAt(ChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ) {
        ChunkPos chunkpos = this.getStartPositionForPosition(chunkGen, rand, chunkPosX, chunkPosZ, 0, 0);
        if (chunkPosX == chunkpos.x && chunkPosZ == chunkpos.z) {
            int i = chunkPosX >> 4;
            int j = chunkPosZ >> 4;
            rand.setSeed((long)(i ^ j << 4) ^ chunkGen.getSeed());
            rand.nextInt();
            if (rand.nextInt(5) != 0) {
                return false;
            }

            Biome biome = chunkGen.getBiomeProvider().getBiome(new BlockPos((chunkPosX << 4) + 9, 0, (chunkPosZ << 4) + 9));
            if (chunkGen.hasStructure(biome, Feature.PILLAGER_OUTPOST)) {
                for(int k = chunkPosX - 10; k <= chunkPosX + 10; ++k) {
                    for(int l = chunkPosZ - 10; l <= chunkPosZ + 10; ++l) {
                        if (Feature.VILLAGE.hasStartAt(chunkGen, rand, k, l)) {
                            return false;
                        }
                    }
                }

                return true;
            }
        }

        return false;
    }

    public Structure.IStartFactory getStartFactory() {
        return MockPillagerOutpostStructure.Start::new;
    }

    protected int getSeedModifier() {
        return 165745296;
    }

    public static class Start extends MarginedStructureStart {
        public Start(Structure<?> feature, int chunkX, int chunkZ, Biome biome, MutableBoundingBox boundingbox, int p_i50497_6_, long seed) {
            super(feature, chunkX, chunkZ, biome, boundingbox, p_i50497_6_, seed);
        }

        public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn) {
            BlockPos blockpos = new BlockPos(chunkX * 16, 90, chunkZ * 16);
            System.out.println(blockpos);
            MockPillagerOutpostPieces.func_215139_a(generator, templateManagerIn, blockpos, this.components, this.rand);
            this.recalculateStructureSize();
        }
    }
}
