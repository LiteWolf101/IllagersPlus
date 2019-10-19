package com.litewolf101.illagers_plus.world.pieces;

import com.litewolf101.illagers_plus.IllagersPlus;
import com.litewolf101.illagers_plus.utils.IllagerPlusLootTable;
import com.litewolf101.illagers_plus.world.StructureRegistry;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.List;
import java.util.Random;

public class IllagerTowerPieces {
    private static final BlockPos STRUCTURE_OFFSET = new BlockPos(0, 0, 0);
    private static final ResourceLocation TOWER_F1 = new ResourceLocation(IllagersPlus.MOD_ID, "illager_tower_f1");
    private static final ResourceLocation TOWER_F2_A = new ResourceLocation(IllagersPlus.MOD_ID, "illager_tower_f2_a");
    private static final ResourceLocation TOWER_F2_B = new ResourceLocation(IllagersPlus.MOD_ID, "illager_tower_f2_b");
    private static final ResourceLocation TOWER_F3_A = new ResourceLocation(IllagersPlus.MOD_ID, "illager_tower_f3_a");
    private static final ResourceLocation TOWER_F3_B = new ResourceLocation(IllagersPlus.MOD_ID, "illager_tower_f3_b");
    private static final ResourceLocation TOWER_F3_C = new ResourceLocation(IllagersPlus.MOD_ID, "illager_tower_f3_c");
    private static final ResourceLocation TOWER_F4_A = new ResourceLocation(IllagersPlus.MOD_ID, "illager_tower_f4_a");
    private static final ResourceLocation TOWER_F4_B = new ResourceLocation(IllagersPlus.MOD_ID, "illager_tower_f4_b");
    private static final ResourceLocation TOWER_F5 = new ResourceLocation(IllagersPlus.MOD_ID, "illager_tower_f5");
    private static final ResourceLocation TOWER_F6 = new ResourceLocation(IllagersPlus.MOD_ID, "illager_tower_f6");

    public static void addParts(TemplateManager templateManagerIn, BlockPos startingPos, Rotation rotation, List<StructurePiece> components, SharedSeedRandom rand, NoFeatureConfig noFeatureConfig) {
        //components.add(new Piece(templateManagerIn, TOWER_F1, startingPos, rotation));
        if (rand.nextInt(10) < 5) {
            //components.add(new IllagerTowerPieces.Piece(templateManagerIn, TOWER_F2_A, startingPos.up(12), rotation));
            //components.add(new IllagerTowerPieces.Piece(templateManagerIn, TOWER_F3_A, startingPos.up(24), rotation));
            //components.add(new IllagerTowerPieces.Piece(templateManagerIn, TOWER_F4_A, startingPos.up(36), rotation));
        } else {
            //components.add(new IllagerTowerPieces.Piece(templateManagerIn, TOWER_F2_B, startingPos.up(12), rotation));
            if (rand.nextInt(20) < 10) {
                //components.add(new IllagerTowerPieces.Piece(templateManagerIn, TOWER_F3_B, startingPos.up(24), rotation));
            } else {
                //components.add(new IllagerTowerPieces.Piece(templateManagerIn, TOWER_F3_C, startingPos.up(24), rotation));
            }
            //components.add(new IllagerTowerPieces.Piece(templateManagerIn, TOWER_F4_B, startingPos.up(36), rotation));
        }
        //components.add(new IllagerTowerPieces.Piece(templateManagerIn, TOWER_F5, startingPos.up(48), rotation));
        //components.add(new IllagerTowerPieces.Piece(templateManagerIn, TOWER_F6, startingPos.up(58), rotation));
    }

    /*public static class Piece extends TemplateStructurePiece {
        private final ResourceLocation templateRL;
        private final Rotation rotation;

        public Piece (TemplateManager templateManager, ResourceLocation location, BlockPos pos, Rotation rotation) {
            super(StructureRegistry.ILLAGER_TOWER_PIECE, 0);
            this.templatePosition = pos;
            this.rotation = rotation;
            this.templateRL = location;
            this.initPlacementData(templateManager);
        }

        public Piece(TemplateManager templateManager, CompoundNBT nbt) {
            super(StructureRegistry.ILLAGER_TOWER_PIECE, 0);
            this.templateRL = new ResourceLocation(nbt.getString("Template"));
            this.rotation = Rotation.valueOf(nbt.getString("Rot"));
            this.initPlacementData(templateManager);
        }

        @Override
        protected void readAdditional(CompoundNBT tagCompound) {
            super.readAdditional(tagCompound);
            tagCompound.putString("Template", this.templateRL.toString());
            tagCompound.putString("Rot", this.rotation.name());
        }

        private void initPlacementData(TemplateManager templateManager) {
            Template template = templateManager.getTemplateDefaulted(this.templateRL);
            PlacementSettings settings = (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK).setCenterOffset(STRUCTURE_OFFSET);
            this.setup(template, this.templatePosition, settings);

        }

        @Override
        protected void handleDataMarker(String function, BlockPos pos, IWorld worldIn, Random rand, MutableBoundingBox sbb) {
            if ("archer_tower".equals(function)) {
                LockableLootTileEntity.setLootTable(worldIn, rand, pos.down(), IllagerPlusLootTable.ILLAGER_ARCHER_TOWER);
            }
        }

        @Override
        public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos p_74875_4_) {
            int i = 256;
            int j = 0;
            BlockPos blockpos = this.templatePosition.add(this.template.getSize().getX() - 1, 0, this.template.getSize().getZ() - 1);

            for(BlockPos blockpos1 : BlockPos.getAllInBoxMutable(this.templatePosition, blockpos)) {
                int k = worldIn.getHeight(Heightmap.Type.WORLD_SURFACE_WG, blockpos1.getX(), blockpos1.getZ());
                j += k;
                i = Math.min(i, k);
            }

            j = j / (this.template.getSize().getX() * this.template.getSize().getZ());
            int l = j;
            this.templatePosition = new BlockPos(this.templatePosition.getX(), l, this.templatePosition.getZ());
            this.boundingBox.minY = l;
            this.boundingBox.maxY = l + 90;
            return super.addComponentParts(worldIn, randomIn, structureBoundingBoxIn, p_74875_4_);
        }
    }*/

}
