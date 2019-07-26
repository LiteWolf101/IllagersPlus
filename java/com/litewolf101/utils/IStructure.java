package com.litewolf101.utils;

import net.minecraft.util.Mirror;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraftforge.fml.common.FMLCommonHandler;

public interface IStructure {
    public static final WorldServer worldServer = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(DimensionType.OVERWORLD.getId());
    public static final PlacementSettings settings = (new PlacementSettings()).setChunk(null).setIgnoreEntities(false).setIgnoreStructureBlock(false).setMirror(Mirror.NONE);

}
