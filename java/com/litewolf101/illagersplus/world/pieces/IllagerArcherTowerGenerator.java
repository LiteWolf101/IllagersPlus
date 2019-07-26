package com.litewolf101.illagersplus.world.pieces;

import com.litewolf101.illagersplus.IllagersPlus;
import com.litewolf101.illagersplus.init.EntityInit;
import com.litewolf101.illagersplus.objects.entity.EntityArcher;
import com.litewolf101.illagersplus.utils.IllagerPlusLootTable;
import com.litewolf101.illagersplus.world.StructureRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
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
import net.minecraft.world.gen.feature.template.*;
import net.minecraft.world.storage.loot.LootTables;

import java.util.List;
import java.util.Random;

public class IllagerArcherTowerGenerator {
    private static final BlockPos STRUCTURE_OFFSET = new BlockPos(7, 0, 7);
    private static final ResourceLocation TOWER = new ResourceLocation(IllagersPlus.MOD_ID, "illager_archer_tower");

    public static void addParts(TemplateManager templateManagerIn, BlockPos startingPos, Rotation rotation, List<StructurePiece> components, SharedSeedRandom rand, NoFeatureConfig noFeatureConfig) {
        components.add(new IllagerArcherTowerGenerator.Piece(templateManagerIn, TOWER, startingPos, rotation));
    }

    public static class Piece extends TemplateStructurePiece {
        private final ResourceLocation templateRL;
        private final Rotation rotation;

        public Piece (TemplateManager templateManager, ResourceLocation location, BlockPos pos, Rotation rotation) {
            super(StructureRegistry.ILLAGER_ARCHER_TOWER_PIECE, 0);
            this.templatePosition = pos;
            this.rotation = rotation;
            this.templateRL = location;
            this.initPlacementData(templateManager);
        }

        public Piece(TemplateManager templateManager, CompoundNBT nbt) {
            super(StructureRegistry.ILLAGER_ARCHER_TOWER_PIECE, 0);
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
            if ("spawnArcher".equals(function)) {
                EntityArcher archer = EntityInit.ARCHER.create(worldIn.getWorld());
                archer.enablePersistence();
                archer.moveToBlockPosAndAngles(pos, 0.0F, 0.0F);
                archer.onInitialSpawn(worldIn, worldIn.getDifficultyForLocation(new BlockPos(archer)), SpawnReason.STRUCTURE, (ILivingEntityData)null, (CompoundNBT)null);
                worldIn.addEntity(archer);
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
            return super.addComponentParts(worldIn, randomIn, structureBoundingBoxIn, p_74875_4_);
        }
    }
}
