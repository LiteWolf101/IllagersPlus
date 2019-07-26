package com.litewolf101.utils;

import com.litewolf101.IllagersPlus;
import net.minecraft.block.state.IBlockState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.Random;

public class StructureHandler extends WorldGenerator implements IStructure {
    public String structureName;

    public StructureHandler(String name) {
        this.structureName = name;
    }

    public String getStructureName (StructureHandler handler){
        return handler.structureName;
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        generateStructureRotNone(worldIn, position);
        generateStructureRot90(worldIn, position);
        generateStructureRot180(worldIn, position);
        generateStructureRot270(worldIn, position);
        generateStructureRotRandom(worldIn, rand, position);
        return true;
    }

    public void generateStructureRotNone(World world, BlockPos pos){
        MinecraftServer mcServer = world.getMinecraftServer();
        TemplateManager manager = worldServer.getStructureTemplateManager();
        ResourceLocation location = new ResourceLocation(IllagersPlus.MOD_ID, structureName);
        Template template = manager.get(mcServer, location);
        if (template != null){
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state, 3);
            template.addBlocksToWorldChunk(world, pos, settings.setRotation(Rotation.NONE));
        }
    }

    public void generateStructureRot90(World world, BlockPos pos){
        MinecraftServer mcServer = world.getMinecraftServer();
        TemplateManager manager = worldServer.getStructureTemplateManager();
        ResourceLocation location = new ResourceLocation(IllagersPlus.MOD_ID, structureName);
        Template template = manager.get(mcServer, location);
        if (template != null){
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state, 3);
            template.addBlocksToWorldChunk(world, pos, settings.setRotation(Rotation.CLOCKWISE_90));
        }
    }

    public void generateStructureRot180(World world, BlockPos pos){
        MinecraftServer mcServer = world.getMinecraftServer();
        TemplateManager manager = worldServer.getStructureTemplateManager();
        ResourceLocation location = new ResourceLocation(IllagersPlus.MOD_ID, structureName);
        Template template = manager.get(mcServer, location);
        if (template != null){
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state, 3);
            template.addBlocksToWorldChunk(world, pos, settings.setRotation(Rotation.CLOCKWISE_180));
        }
    }

    public void generateStructureRot270(World world, BlockPos pos){
        MinecraftServer mcServer = world.getMinecraftServer();
        TemplateManager manager = worldServer.getStructureTemplateManager();
        ResourceLocation location = new ResourceLocation(IllagersPlus.MOD_ID, structureName);
        Template template = manager.get(mcServer, location);
        if (template != null){
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state, 3);
            template.addBlocksToWorldChunk(world, pos, settings.setRotation(Rotation.COUNTERCLOCKWISE_90));
        }
    }

    public void generateStructureRotRandom(World world, Random random, BlockPos pos){
        MinecraftServer mcServer = world.getMinecraftServer();
        TemplateManager manager = worldServer.getStructureTemplateManager();
        ResourceLocation location = new ResourceLocation(IllagersPlus.MOD_ID, structureName);
        Template template = manager.get(mcServer, location);
        if (template != null){
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state, 3);
            template.addBlocksToWorldChunk(world, pos, settings.setRotation(randomRotation(random)));
        }
    }

    public Rotation randomRotation(Random random) {
        Rotation rotation = Rotation.NONE;
        int rand = random.nextInt(3);
        if (rand == 1) {
            rotation = Rotation.CLOCKWISE_90;
        } else if (rand == 2) {
            rotation = Rotation.CLOCKWISE_180;
        } else if (rand == 3) {
            rotation = Rotation.COUNTERCLOCKWISE_90;
        }
        return rotation;
    }

    public Rotation getStructureRotation() {
        return settings.getRotation();
    }

    public enum RotationType {
        NONE,
        DEGREES90,
        DEGREES180,
        DEGREES270,
        RANDOM
        //Rotations are in clockwise fashion
    }
}