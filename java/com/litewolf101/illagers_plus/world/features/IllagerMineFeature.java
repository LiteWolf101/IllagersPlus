package com.litewolf101.illagers_plus.world.features;

import com.litewolf101.illagers_plus.IllagersPlus;
import com.litewolf101.illagers_plus.init.EntityInit;
import com.litewolf101.illagers_plus.objects.entity.EntityArcher;
import com.litewolf101.illagers_plus.objects.entity.EntityMiner;
import com.litewolf101.illagers_plus.utils.IllagerPlusLootTable;
import com.mojang.datafixers.Dynamic;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.PillagerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.state.properties.StructureMode;
import net.minecraft.tileentity.BarrelTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTables;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Function;

public class IllagerMineFeature extends Feature<NoFeatureConfig> {
    public static final MinecraftServer worldServer = ServerLifecycleHooks.getCurrentServer();
    public IllagerMineFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactory, boolean doBlockNotify) {
        super(configFactory, doBlockNotify);
    }

    public static boolean canReplace(IWorld world, BlockPos pos)
    {
        Material material = world.getBlockState(pos).getMaterial();
        // we think it's replaceable if it's air / liquid / snow, plants, or leaves
        return material.isSolid() || material == Material.PLANTS || material == Material.LEAVES || material == Material.ORGANIC || material != Material.AIR;
    }

    public static boolean canSpawnHere(IWorld world, BlockPos posAboveGround, int width, int depth, int height)
    {
        // check all the corners to see which ones are replaceable
        boolean corner1Air = canReplace(world, posAboveGround);
        boolean corner2Air = canReplace(world, posAboveGround.add(width, 0, 0));
        boolean corner4Air = canReplace(world, posAboveGround.add(0, 0, depth));
        boolean corner3Air = canReplace(world, posAboveGround.add(width, 0, depth));

        // if Y > 20 and all corners pass the test, it's okay to spawn the structure
        return posAboveGround.getY() > height && corner1Air && corner2Air && corner3Air && corner4Air;
    }

    private static boolean canSpawnClockwise90(IWorld world, BlockPos posAboveGround, int width, int depth, int height)
    {
        // check all the corners to see which ones are replaceable
        boolean corner1Air = canReplace(world, posAboveGround);
        boolean corner2Air = canReplace(world, posAboveGround.add(depth, 0, 0));
        boolean corner4Air = canReplace(world, posAboveGround.add(0, 0, width));
        boolean corner3Air = canReplace(world, posAboveGround.add(depth, 0, width));

        // if Y > 20 and all corners pass the test, it's okay to spawn the structure
        return posAboveGround.getY() > height && corner1Air && corner2Air && corner3Air && corner4Air;
    }

    private static boolean canSpawnClockwise180(IWorld world, BlockPos posAboveGround, int width, int depth, int height)
    {
        // check all the corners to see which ones are replaceable
        boolean corner1Air = canReplace(world, posAboveGround);
        boolean corner2Air = canReplace(world, posAboveGround.add(width, 0,  0));
        boolean corner4Air = canReplace(world, posAboveGround.add( 0, 0, depth));
        boolean corner3Air = canReplace(world, posAboveGround.add(width, 0, depth));

        // if Y > 20 and all corners pass the test, it's okay to spawn the structure
        return posAboveGround.getY() > height && corner1Air && corner2Air && corner3Air && corner4Air;
    }

    private static boolean canSpawnClockwise270(IWorld world, BlockPos posAboveGround, int width, int depth, int height)
    {
        // check all the corners to see which ones are replaceable
        boolean corner1Air = canReplace(world, posAboveGround);
        boolean corner2Air = canReplace(world, posAboveGround.add(depth, 0,  0));
        boolean corner4Air = canReplace(world, posAboveGround.add(0, 0, width));
        boolean corner3Air = canReplace(world, posAboveGround.add(depth, 0, width));;

        // if Y > 20 and all corners pass the test, it's okay to spawn the structure
        return posAboveGround.getY() > height && corner1Air && corner2Air && corner3Air && corner4Air;
    }

    @Nullable
    @Override
    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        int width = 14;
        int height = 16;
        int depth = 14;
        MinecraftServer mcServer = worldIn.getWorld().getServer();
        TemplateManager templateManager = mcServer.getWorld(worldIn.getDimension().getType()).getStructureTemplateManager();
        Template template = templateManager.getTemplate(new ResourceLocation(IllagersPlus.MOD_ID, "mine_top"));
        Template template2 = templateManager.getTemplate(new ResourceLocation(IllagersPlus.MOD_ID, "mine_middle"));
        Template template3 = templateManager.getTemplate(new ResourceLocation(IllagersPlus.MOD_ID, "mine_split"));

        if (template == null) {
            System.out.println("Could not find structure at " + new ResourceLocation(IllagersPlus.MOD_ID, "structures/mine_top"));
            return false;
        }
        if (template2 == null) {
            System.out.println("Could not find structure at " + new ResourceLocation(IllagersPlus.MOD_ID, "structures/mine_middle"));
            return false;
        }
        if (template3 == null) {
            System.out.println("Could not find structure at " + new ResourceLocation(IllagersPlus.MOD_ID, "structures/mine_split"));
            return false;
        }

        if (rand.nextInt(50) > 1) {
            return false;
        }

        BlockPos worldHeight = worldIn.getHeight(Heightmap.Type.WORLD_SURFACE, pos);
        if (worldHeight.getY() >= 68) {
            return false;
        } else {
            PlacementSettings placementSettings = (new PlacementSettings()).setMirror(Mirror.NONE).setRotation(Rotation.NONE).setIgnoreEntities(false).setChunk(null);

            if (template.addBlocksToWorld(worldIn, pos, placementSettings, 2)) {
                for (Template.BlockInfo template$blockinfo : template.func_215381_a(pos, placementSettings, Blocks.STRUCTURE_BLOCK)) {
                    if (template$blockinfo.nbt != null) {
                        StructureMode structuremode = StructureMode.valueOf(template$blockinfo.nbt.getString("mode"));
                        if (structuremode == StructureMode.DATA) {
                            this.handleDataMarker(template$blockinfo.nbt.getString("metadata"), template$blockinfo.pos, worldIn, rand, template.func_215388_b(placementSettings, pos));
                        }
                    }
                }
            }
            if (template2.addBlocksToWorld(worldIn, pos.add(1, -7, 1), placementSettings, 2)) {
                for (Template.BlockInfo template$blockinfo : template2.func_215381_a(pos, placementSettings, Blocks.STRUCTURE_BLOCK)) {
                    if (template$blockinfo.nbt != null) {
                        StructureMode structuremode = StructureMode.valueOf(template$blockinfo.nbt.getString("mode"));
                        if (structuremode == StructureMode.DATA) {
                            this.handleDataMarker(template$blockinfo.nbt.getString("metadata"), template$blockinfo.pos, worldIn, rand, template2.func_215388_b(placementSettings, pos));
                        }
                    }
                }
            }
            if (template3.addBlocksToWorld(worldIn, pos.add(1, -14, 1), placementSettings, 2)) {
                for (Template.BlockInfo template$blockinfo : template3.func_215381_a(pos.add(1, -14, 1), placementSettings, Blocks.STRUCTURE_BLOCK)) {
                    if (template$blockinfo.nbt != null) {
                        StructureMode structuremode = StructureMode.valueOf(template$blockinfo.nbt.getString("mode"));
                        if (structuremode == StructureMode.DATA) {
                            this.handleDataMarker(template$blockinfo.nbt.getString("metadata"), template$blockinfo.pos, worldIn, rand, template3.func_215388_b(placementSettings, pos.add(1, -14, 1)));
                        }
                    }
                }
            }

            // Test
            if(canSpawnHere(worldIn, pos, width, depth, height))
            {
                template.addBlocksToWorldChunk(worldIn, pos, placementSettings);
                template2.addBlocksToWorldChunk(worldIn, pos.add(1, -7, 1), placementSettings);
                template3.addBlocksToWorldChunk(worldIn, pos.add(1, -14, 1), placementSettings);
                System.out.println(pos);
                //placeMiddlePiece(worldIn, rand, pos.add(1, -7, 1), placementSettings);
                return true;
            }
        }
        return false;
    }

    public void placeMiddlePiece(IWorld worldIn, Random rand, BlockPos pos, PlacementSettings placementSettings) {
        int width = 9;
        int height = 7;
        int depth = 9;
        MinecraftServer mcServer = worldIn.getWorld().getServer();
        TemplateManager templateManager = mcServer.getWorld(worldIn.getDimension().getType()).getStructureTemplateManager();
        Template template = templateManager.getTemplate(new ResourceLocation(IllagersPlus.MOD_ID, "mine_middle"));

        if (template == null) {
            System.out.println("Could not find structure at " + new ResourceLocation(IllagersPlus.MOD_ID, "structures/mine_middle"));
        }
        if (template.addBlocksToWorld(worldIn, pos, placementSettings, 2)) {
            for (Template.BlockInfo template$blockinfo : template.func_215381_a(pos, placementSettings, Blocks.STRUCTURE_BLOCK)) {
                if (template$blockinfo.nbt != null) {
                    StructureMode structuremode = StructureMode.valueOf(template$blockinfo.nbt.getString("mode"));
                    if (structuremode == StructureMode.DATA) {
                        this.handleDataMarker(template$blockinfo.nbt.getString("metadata"), template$blockinfo.pos, worldIn, rand, template.func_215388_b(placementSettings, pos));
                    }
                }
            }
        }
        template.addBlocksToWorldChunk(worldIn, pos, placementSettings);
    }

    protected void handleDataMarker(String function, BlockPos pos, IWorld worldIn, Random rand, MutableBoundingBox sbb) {
        if ("spawnMiner".equals(function)) {
            EntityMiner miner = EntityInit.MINER.create(worldIn.getWorld());
            miner.enablePersistence();
            miner.moveToBlockPosAndAngles(pos, 0.0F, 0.0F);
            miner.setHomePosAndDistance(pos, 7);
            miner.onInitialSpawn(worldIn, worldIn.getDifficultyForLocation(new BlockPos(miner)), SpawnReason.STRUCTURE, (ILivingEntityData)null, (CompoundNBT)null);
            worldIn.addEntity(miner);
        }
        if ("spawnRaider".equals(function)) {
            PillagerEntity pillager = EntityType.PILLAGER.create(worldIn.getWorld());
            pillager.enablePersistence();
            pillager.moveToBlockPosAndAngles(pos, 0.0F, 0.0F);
            pillager.onInitialSpawn(worldIn, worldIn.getDifficultyForLocation(new BlockPos(pillager)), SpawnReason.STRUCTURE, (ILivingEntityData)null, (CompoundNBT)null);
            worldIn.addEntity(pillager);
        }
        if ("lootMine".equals(function)) {
            TileEntity tileEntity = worldIn.getTileEntity(pos.down());
            if (tileEntity instanceof BarrelTileEntity) {
                ((BarrelTileEntity) tileEntity).setLootTable(LootTables.CHESTS_PILLAGER_OUTPOST, worldIn.getSeed());
            }
        }
        worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
    }
}
