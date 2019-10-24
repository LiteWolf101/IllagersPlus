package com.litewolf101.illagers_plus.world.pieces;

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
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.IntegrityProcessor;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.List;

import static com.litewolf101.illagers_plus.IllagersPlus.MOD_ID;

public class IllagerMinePieces {
    public static void generateMine(ChunkGenerator<?> generator, TemplateManager templateManagerIn, BlockPos pos, List<StructurePiece> structures, SharedSeedRandom seedRandom) {
        JigsawManager.func_214889_a(new ResourceLocation(MOD_ID, "illager_mine/base_plates"), 7, IllagerMinePieces.MinePiece::new, generator, templateManagerIn, pos, structures, seedRandom);
    }

    static {
        JigsawManager.field_214891_a.register(new JigsawPattern(new ResourceLocation(MOD_ID, "illager_mine/base_plates"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(new SingleJigsawPiece(MOD_ID + ":illager_mine/mine_top"), 1)), JigsawPattern.PlacementBehaviour.RIGID));
        //JigsawManager.field_214891_a.register(new JigsawPattern(new ResourceLocation("pillager_outpost/towers"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(new ListJigsawPiece(ImmutableList.of(new SingleJigsawPiece("pillager_outpost/watchtower"), new SingleJigsawPiece("pillager_outpost/watchtower_overgrown", ImmutableList.of(new IntegrityProcessor(0.05F))))), 1)), JigsawPattern.PlacementBehaviour.RIGID));
        //JigsawManager.field_214891_a.register(new JigsawPattern(new ResourceLocation(MOD_ID, "illager_mine/feature_plates"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(new SingleJigsawPiece("pillager_outpost/base_plate"), 1)), JigsawPattern.PlacementBehaviour.TERRAIN_MATCHING));
        //JigsawManager.field_214891_a.register(new JigsawPattern(new ResourceLocation(MOD_ID, "illager_mine/features"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(new SingleJigsawPiece(MOD_ID + ":illager_mine/campfire"), 1), Pair.of(new SingleJigsawPiece(MOD_ID + ":illager_mine/ravanger_pen"), 1), Pair.of(new SingleJigsawPiece(MOD_ID + ":illager_mine/tent"), 1), Pair.of(new SingleJigsawPiece(MOD_ID + ":illager_mine/trapped_loot_chest"), 1), Pair.of(EmptyJigsawPiece.INSTANCE, 6)), JigsawPattern.PlacementBehaviour.RIGID));
    }

    public static class MinePiece extends AbstractVillagePiece {
        public MinePiece(TemplateManager p_i50560_1_, JigsawPiece p_i50560_2_, BlockPos p_i50560_3_, int p_i50560_4_, Rotation p_i50560_5_, MutableBoundingBox p_i50560_6_) {
            super(StructureRegistry.IPIM, p_i50560_1_, p_i50560_2_, p_i50560_3_, p_i50560_4_, p_i50560_5_, p_i50560_6_);
        }

        public MinePiece(TemplateManager p_i50561_1_, CompoundNBT p_i50561_2_) {
            super(p_i50561_1_, p_i50561_2_, StructureRegistry.IPIM);
        }
    }
}
