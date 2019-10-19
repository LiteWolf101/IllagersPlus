package com.litewolf101.illagers_plus.world.features;

import com.litewolf101.illagers_plus.IllagersPlus;
import com.litewolf101.illagers_plus.init.EntityInit;
import com.litewolf101.illagers_plus.objects.entity.EntityArcher;
import com.litewolf101.illagers_plus.utils.IllagerPlusLootTable;
import com.mojang.datafixers.Dynamic;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.state.properties.StructureMode;
import net.minecraft.tileentity.LockableLootTileEntity;
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
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Function;

public class IllagerArcherTowerFeature extends Feature<NoFeatureConfig> {
    public static final MinecraftServer worldServer = ServerLifecycleHooks.getCurrentServer();
    public IllagerArcherTowerFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactory, boolean doBlockNotify) {
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
        Template template = templateManager.getTemplate(new ResourceLocation(IllagersPlus.MOD_ID, "illager_archer_tower"));

        if (template == null) {
            System.out.println("Could not find structure at " + new ResourceLocation(IllagersPlus.MOD_ID, "structures/illager_archer_tower"));
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

            // Test
            if(canSpawnHere(worldIn, pos, width, depth, height))
            {
                template.addBlocksToWorldChunk(worldIn, pos, placementSettings);
                return true;
            }
        }
        return false;
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
        worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
    }
}
