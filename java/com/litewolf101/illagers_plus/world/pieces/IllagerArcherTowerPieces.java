package com.litewolf101.illagers_plus.world.pieces;

import com.litewolf101.illagers_plus.IllagersPlus;
import com.litewolf101.illagers_plus.init.EntityInit;
import com.litewolf101.illagers_plus.objects.entity.EntityArcher;
import com.litewolf101.illagers_plus.utils.IllagerPlusLootTable;
import com.litewolf101.illagers_plus.world.StructureRegistry;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
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

public class IllagerArcherTowerPieces {
    private static final ResourceLocation LOCATION = new ResourceLocation(IllagersPlus.MOD_ID, "illager_archer_tower");

    public static void addTowerPieces(TemplateManager templateManager, BlockPos origin, Rotation rotation, List<StructurePiece> pieces, SharedSeedRandom rand, NoFeatureConfig noFeatureConfig) {
        //pieces.add(new IllagerArcherTowerPieces.Piece(templateManager, LOCATION, origin, rotation));
    }

    /*public static class Piece extends TemplateStructurePiece {
        private final ResourceLocation templateLocation;
        private final Rotation rotation;

        public Piece(TemplateManager templateManager, ResourceLocation templateLocation, BlockPos position, Rotation rotation) {
            super(StructureRegistry.ILLAGER_ARCHER_TOWER_PIECE, 0);
            this.templateLocation = templateLocation;
            this.templatePosition = position;
            this.rotation = rotation;
            this.setup(templateManager);
        }

        public Piece(TemplateManager templateManager, CompoundNBT compound) {
            super(StructureRegistry.ILLAGER_ARCHER_TOWER_PIECE, compound);
            this.templateLocation = new ResourceLocation(compound.getString("Template"));
            this.rotation = Rotation.valueOf(compound.getString("Rot"));
            this.setup(templateManager);
        }

        private void setup(TemplateManager templateManager) {
            Template template = templateManager.getTemplateDefaulted(this.templateLocation);
            PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE).setCenterOffset(new BlockPos(6.5, 0, 6.5)).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK);
            this.setup(template, this.templatePosition, placementsettings);
        }

        @Override
        protected void readAdditional(CompoundNBT compound) {
            super.readAdditional(compound);
            compound.putString("Template", this.templateLocation.toString());
            compound.putString("Rot", this.rotation.name());
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
        public boolean addComponentParts(IWorld world, Random random, MutableBoundingBox bounds, ChunkPos chunkPos) {
            /*int i = 256;
            int j = 0;
            BlockPos blockpos = this.templatePosition.add(this.template.getSize().getX() - 1, 0, this.template.getSize().getZ() - 1);

            for(BlockPos blockpos1 : BlockPos.getAllInBoxMutable(this.templatePosition, blockpos)) {
                int k = world.getHeight(Heightmap.Type.WORLD_SURFACE_WG, blockpos1.getX(), blockpos1.getZ());
                j += k;
                i = Math.min(i, k);
            }

            j = j / (this.template.getSize().getX() * this.template.getSize().getZ());
            int l = j;
            this.templatePosition = new BlockPos(this.templatePosition.getX(), l, this.templatePosition.getZ());
            this.boundingBox.minY = l;
            this.boundingBox.maxY = l + this.template.getSize().getY();
            System.out.println("Size of structure is: " + this.boundingBox);
            System.out.println("Location of structure is: " + this.templatePosition);
            //world.setBlockState(this.templatePosition, Blocks.DIAMOND_BLOCK.getDefaultState(), 3);
            return true;*/
            /*int i = 256;
            int j = 0;
            BlockPos blockpos = this.templatePosition.add(this.template.getSize().getX() - 1, 0, this.template.getSize().getZ() - 1);

            for(BlockPos blockpos1 : BlockPos.getAllInBoxMutable(this.templatePosition, blockpos)) {
                int k = world.getHeight(Heightmap.Type.WORLD_SURFACE_WG, blockpos1.getX(), blockpos1.getZ());
                j += k;
                i = Math.min(i, k);
            }

            j = j / (this.template.getSize().getX() * this.template.getSize().getZ());
            int l = j;
            this.templatePosition = new BlockPos(this.templatePosition.getX(), l, this.templatePosition.getZ());
            this.boundingBox.minY = l;
            this.boundingBox.maxY = l + this.template.getSize().getY();
            System.out.println("Size of structure is: " + this.boundingBox);
            System.out.println("Location of structure is: " + this.templatePosition);
            return super.addComponentParts(world, random, boundingBox, chunkPos);
        }
    }

    /*private static final ResourceLocation field_202592_e = new ResourceLocation(IllagersPlus.MOD_ID, "illager_archer_tower");
    //private static final ResourceLocation field_202592_e = new ResourceLocation("igloo/top");
    private static final ResourceLocation field_202593_f = new ResourceLocation("igloo/middle");
    private static final ResourceLocation field_202594_g = new ResourceLocation("igloo/bottom");
    private static final Map<ResourceLocation, BlockPos> field_207621_d = ImmutableMap.of(field_202592_e, new BlockPos(3, 5, 5), field_202593_f, new BlockPos(1, 3, 1), field_202594_g, new BlockPos(3, 6, 7));
    private static final Map<ResourceLocation, BlockPos> field_207622_e = ImmutableMap.of(field_202592_e, BlockPos.ZERO, field_202593_f, new BlockPos(2, -3, 4), field_202594_g, new BlockPos(0, -3, -2));

    public static void func_207617_a(TemplateManager p_207617_0_, BlockPos p_207617_1_, Rotation p_207617_2_, List<StructurePiece> p_207617_3_, Random p_207617_4_) {
        if (p_207617_4_.nextDouble() < 0.5D) {
            int i = p_207617_4_.nextInt(8) + 4;
            //p_207617_3_.add(new IllagerArcherTowerPieces.Piece(p_207617_0_, field_202594_g, p_207617_1_, p_207617_2_, i * 3));

            for(int j = 0; j < i - 1; ++j) {
                //p_207617_3_.add(new IllagerArcherTowerPieces.Piece(p_207617_0_, field_202593_f, p_207617_1_, p_207617_2_, j * 3));
            }
        }

        //p_207617_3_.add(new IllagerArcherTowerPieces.Piece(p_207617_0_, field_202592_e, p_207617_1_, p_207617_2_, 0));
    }

    public static class Piece extends StructurePiece {
        public Piece(IStructurePieceType p_i51342_1_, int p_i51342_2_) {
            super(StructureRegistry.ILLAGER_ARCHER_TOWER_PIECE, 0);
        }

        @Override
        protected void readAdditional(CompoundNBT tagCompound) {
        }

        @Override
        public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos p_74875_4_) {

            MutableBoundingBox structureboundingbox = this.getBoundingBox();
            BlockPos blockpos = new BlockPos(structureboundingbox.minX, structureboundingbox.minY, structureboundingbox.minZ);
            int y = worldIn.getHeight(Heightmap.Type.WORLD_SURFACE_WG, blockpos.getX(), blockpos.getZ());
            BlockPos adjustedPos = new BlockPos(structureboundingbox.minX, y, structureboundingbox.minZ);
            MinecraftServer minecraftserver = worldIn.getWorld().getServer();
            TemplateManager templatemanager = minecraftserver.getWorld(DimensionType.OVERWORLD).getStructureTemplateManager();
            PlacementSettings placementsettings = (new PlacementSettings()).setRotation(Rotation.NONE).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK).setBoundingBox(structureboundingbox);
            Template template = templatemanager.getTemplate(field_202592_e);
            template.addBlocksToWorldChunk(worldIn, adjustedPos, placementsettings);
            System.out.println(adjustedPos);
            return true;

        }
    }*/
}
