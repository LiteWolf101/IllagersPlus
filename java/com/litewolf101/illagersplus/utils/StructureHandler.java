package com.litewolf101.illagersplus.utils;

import com.litewolf101.illagersplus.IllagersPlus;
import com.mojang.datafixers.DataFixer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.AbstractChunkProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraftforge.fml.common.IWorldGenerator;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.Random;

public class StructureHandler implements IStructure {
    public String structureName;

    public StructureHandler(String name) {
        this.structureName = name;
    }

    public String getStructureName (StructureHandler handler){
        return handler.structureName;
    }

    public void generateStructureRotRandom(World world, TemplateManager manager, Random random, BlockPos pos){
        ResourceLocation location = new ResourceLocation(IllagersPlus.MOD_ID, structureName);
        Template template = manager.getTemplate(location);
        if (template != null){
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