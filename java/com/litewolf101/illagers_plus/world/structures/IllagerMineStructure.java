package com.litewolf101.illagers_plus.world.structures;

import com.google.common.collect.Lists;
import com.litewolf101.illagers_plus.world.pieces.IllagerMinePieces;
import com.litewolf101.illagers_plus.world.structureConfig.IllagerMineConfig;
import com.litewolf101.illagers_plus.world.test.MockPillagerOutpostConfig;
import com.litewolf101.illagers_plus.world.test.MockPillagerOutpostPieces;
import com.mojang.datafixers.Dynamic;
import net.minecraft.entity.EntityType;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.MarginedStructureStart;
import net.minecraft.world.gen.feature.structure.ScatteredStructure;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class IllagerMineStructure extends ScatteredStructure<IllagerMineConfig> {
    private static final List<Biome.SpawnListEntry> field_214558_a = Lists.newArrayList(new Biome.SpawnListEntry(EntityType.PILLAGER, 1, 1, 1));

    public IllagerMineStructure(Function<Dynamic<?>, ? extends IllagerMineConfig> p_i51470_1_) {
        super(p_i51470_1_);
    }

    public String getStructureName() {
        return "Illager_Mine";
    }

    public int getSize() {
        return 8;
    }

    public List<Biome.SpawnListEntry> getSpawnList() {
        return field_214558_a;
    }

    protected ChunkPos getStartPositionForPosition(ChunkGenerator<?> chunkGenerator, Random random, int x, int z, int spacingOffsetsX, int spacingOffsetsZ) {
        int i = 40;
        int j = 20;
        int k = x + i * spacingOffsetsX;
        int l = z + i * spacingOffsetsZ;
        int i1 = k < 0 ? k - i + 1 : k;
        int j1 = l < 0 ? l - i + 1 : l;
        int k1 = i1 / i;
        int l1 = j1 / i;
        ((SharedSeedRandom)random).setLargeFeatureSeedWithSalt(chunkGenerator.getSeed(), k1, l1, 10387319);
        k1 = k1 * i;
        l1 = l1 * i;
        k1 = k1 + (random.nextInt(i - j) + random.nextInt(i - j)) / 2;
        l1 = l1 + (random.nextInt(i - j) + random.nextInt(i - j)) / 2;
        return new ChunkPos(k1, l1);
    }

    public boolean hasStartAt(ChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ) {
        ChunkPos chunkpos = this.getStartPositionForPosition(chunkGen, rand, chunkPosX, chunkPosZ, 0, 0);
        if (chunkPosX == chunkpos.x && chunkPosZ == chunkpos.z) {

            return true;
        } else {
            return false;
        }
    }

    public IStartFactory getStartFactory() {
        return IllagerMineStructure.Start::new;
    }

    protected int getSeedModifier() {
        return 129929813;
    }

    public static class Start extends MarginedStructureStart {
        public Start(Structure<?> feature, int chunkX, int chunkZ, Biome biome, MutableBoundingBox boundingbox, int p_i50497_6_, long seed) {
            super(feature, chunkX, chunkZ, biome, boundingbox, p_i50497_6_, seed);
        }

        public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn) {
            BlockPos blockpos = new BlockPos(chunkX * 16, 90, chunkZ * 16);
            System.out.println(blockpos);
            IllagerMinePieces.generateMine(generator, templateManagerIn, blockpos, this.components, this.rand);
            this.recalculateStructureSize();
        }
    }
}
