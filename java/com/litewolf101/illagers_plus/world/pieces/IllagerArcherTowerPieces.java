package com.litewolf101.illagers_plus.world.pieces;

import com.litewolf101.illagers_plus.init.EntityInit;
import com.litewolf101.illagers_plus.objects.entity.EntityArcher;
import com.litewolf101.illagers_plus.utils.IllagerPlusLootTable;
import com.litewolf101.illagers_plus.world.StructureRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.state.properties.StructureMode;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.List;
import java.util.Random;

import static com.litewolf101.illagers_plus.IllagersPlus.MOD_ID;

public class IllagerArcherTowerPieces {
    private static final ResourceLocation LOCATION = new ResourceLocation(MOD_ID, "illager_archer_tower");

    public static void addTowerPieces(TemplateManager templateManager, BlockPos origin, Rotation rotation, List<StructurePiece> pieces, SharedSeedRandom rand) {
        pieces.add(new IllagerArcherTowerPieces.IllagerArcherTower(templateManager, LOCATION.toString(), origin, rotation));
    }

    public static class IllagerArcherTower extends TemplateStructurePiece {
        private final String templateName;
        private final Rotation rotation;

        public IllagerArcherTower(TemplateManager manager, String name, BlockPos pos, Rotation rotation) {
            this(manager, name, pos, rotation, Mirror.NONE);
        }

        public IllagerArcherTower(TemplateManager manager, String name, BlockPos pos, Rotation rotation, Mirror p_i47356_5_) {
            super(StructureRegistry.IPIAT, 0);
            this.templateName = name;
            this.templatePosition = pos;
            this.rotation = rotation;
            this.loadTemplate(manager);
        }

        public IllagerArcherTower(TemplateManager manager, CompoundNBT nbt) {
            super(StructureRegistry.IPIAT, nbt);
            this.templateName = nbt.getString("Template");
            this.rotation = Rotation.valueOf(nbt.getString("Rot"));
            this.loadTemplate(manager);
        }

        private void loadTemplate(TemplateManager manager) {
            Template template = manager.getTemplateDefaulted(new ResourceLocation(MOD_ID, "illager_archer_tower"));
            PlacementSettings placementsettings = (new PlacementSettings()).setIgnoreEntities(true).setRotation(this.rotation).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK);
            this.setup(template, this.templatePosition, placementsettings);
        }

        protected void readAdditional(CompoundNBT tagCompound) {
            super.readAdditional(tagCompound);
            tagCompound.putString("Template", this.templateName);
            tagCompound.putString("Rot", this.placeSettings.getRotation().name());
        }

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
            int i = 256;
            int j = 0;
            MinecraftServer mcServer = world.getWorld().getServer();
            TemplateManager templateManager = mcServer.getWorld(world.getDimension().getType()).getStructureTemplateManager();
            Template template = templateManager.getTemplate(LOCATION);

            if (template == null) {
                return false;
            }

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
            template.addBlocksToWorldChunk(world, this.templatePosition, this.placeSettings);
            if (template.addBlocksToWorld(world, this.templatePosition, this.placeSettings, 2)) {
                for (Template.BlockInfo template$blockinfo : template.func_215381_a(this.templatePosition, this.placeSettings, Blocks.STRUCTURE_BLOCK)) {
                    if (template$blockinfo.nbt != null) {
                        StructureMode structuremode = StructureMode.valueOf(template$blockinfo.nbt.getString("mode"));
                        if (structuremode == StructureMode.DATA) {
                            this.handleDataMarker(template$blockinfo.nbt.getString("metadata"), template$blockinfo.pos, world, random, template.func_215388_b(this.placeSettings, this.templatePosition));
                        }
                    }
                }
            }
            return true;
        }
    }
}
