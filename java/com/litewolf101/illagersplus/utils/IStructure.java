package com.litewolf101.illagersplus.utils;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mirror;
import net.minecraft.world.ServerMultiWorld;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraftforge.common.extensions.IForgeWorldServer;
import net.minecraftforge.fml.loading.FMLCommonLaunchHandler;
import net.minecraftforge.fml.loading.FMLServerLaunchProvider;
import net.minecraftforge.userdev.FMLDevServerLaunchProvider;

import static net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor.STRUCTURE_BLOCK;

public interface IStructure {
    public static final PlacementSettings settings = (new PlacementSettings()).setChunk(null).setIgnoreEntities(false).setMirror(Mirror.NONE).addProcessor(STRUCTURE_BLOCK);
}
