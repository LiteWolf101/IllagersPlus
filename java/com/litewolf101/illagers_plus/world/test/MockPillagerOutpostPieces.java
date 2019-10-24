package com.litewolf101.illagers_plus.world.test;

import com.google.common.collect.ImmutableList;
import com.litewolf101.illagers_plus.world.StructureRegistry;
import com.mojang.datafixers.util.Pair;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.jigsaw.*;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.PillagerOutpostPieces;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.IntegrityProcessor;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.List;

public class MockPillagerOutpostPieces {
    public static void func_215139_a(ChunkGenerator<?> p_215139_0_, TemplateManager templateManagerIn, BlockPos p_215139_2_, List<StructurePiece> p_215139_3_, SharedSeedRandom p_215139_4_) {
        JigsawManager.func_214889_a(new ResourceLocation("pillager_outpost/base_plates"), 7, MockPillagerOutpostPieces.MockPillageOutpost::new, p_215139_0_, templateManagerIn, p_215139_2_, p_215139_3_, p_215139_4_);
    }

    static {
        JigsawManager.field_214891_a.register(new JigsawPattern(new ResourceLocation("pillager_outpost/base_plates"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(new SingleJigsawPiece("pillager_outpost/base_plate"), 1)), JigsawPattern.PlacementBehaviour.RIGID));
        JigsawManager.field_214891_a.register(new JigsawPattern(new ResourceLocation("pillager_outpost/towers"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(new ListJigsawPiece(ImmutableList.of(new SingleJigsawPiece("pillager_outpost/watchtower"), new SingleJigsawPiece("pillager_outpost/watchtower_overgrown", ImmutableList.of(new IntegrityProcessor(0.05F))))), 1)), JigsawPattern.PlacementBehaviour.RIGID));
        JigsawManager.field_214891_a.register(new JigsawPattern(new ResourceLocation("pillager_outpost/feature_plates"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(new SingleJigsawPiece("pillager_outpost/feature_plate"), 1)), JigsawPattern.PlacementBehaviour.TERRAIN_MATCHING));
        JigsawManager.field_214891_a.register(new JigsawPattern(new ResourceLocation("pillager_outpost/features"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(new SingleJigsawPiece("pillager_outpost/feature_cage1"), 1), Pair.of(new SingleJigsawPiece("pillager_outpost/feature_cage2"), 1), Pair.of(new SingleJigsawPiece("pillager_outpost/feature_logs"), 1), Pair.of(new SingleJigsawPiece("pillager_outpost/feature_tent1"), 1), Pair.of(new SingleJigsawPiece("pillager_outpost/feature_tent2"), 1), Pair.of(new SingleJigsawPiece("pillager_outpost/feature_targets"), 1), Pair.of(EmptyJigsawPiece.INSTANCE, 6)), JigsawPattern.PlacementBehaviour.RIGID));
    }

    public static class MockPillageOutpost extends AbstractVillagePiece {
        public MockPillageOutpost(TemplateManager p_i50560_1_, JigsawPiece p_i50560_2_, BlockPos p_i50560_3_, int p_i50560_4_, Rotation p_i50560_5_, MutableBoundingBox p_i50560_6_) {
            super(StructureRegistry.PCP, p_i50560_1_, p_i50560_2_, p_i50560_3_, p_i50560_4_, p_i50560_5_, p_i50560_6_);
        }

        public MockPillageOutpost(TemplateManager p_i50561_1_, CompoundNBT p_i50561_2_) {
            super(p_i50561_1_, p_i50561_2_, StructureRegistry.PCP);
        }
    }
}
