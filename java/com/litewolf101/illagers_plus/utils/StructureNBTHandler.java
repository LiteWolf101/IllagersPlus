package com.litewolf101.illagers_plus.utils;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import net.minecraft.block.Block;
import net.minecraft.nbt.*;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.storage.loot.conditions.BlockStateProperty;

import java.io.*;
import java.util.*;

import static net.minecraftforge.registries.ForgeRegistries.BLOCKS;

public class StructureNBTHandler {
    /** FOR TESTING PURPOSES ONLY */
    public static void readFile(String structureName) {
        String locationName = "/src/main/resources/data/illagers_plus/structures/" + structureName + ".nbt";
        String basePath = new File("").getAbsolutePath();
        File basePath1 = new File(basePath).getParentFile();
        String path = new File(basePath1.getAbsolutePath() + locationName).getAbsolutePath();
        if (path != null) {
            try {
                DataInputStream datainputstream = new DataInputStream(new FileInputStream(path));
                CompoundNBT nbt = CompressedStreamTools.readCompressed(datainputstream);
                try {
                    parseNBTIntoValues(nbt);
                } finally {
                    datainputstream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void parseNBTIntoValues(CompoundNBT nbt) {
        Table<Block, BlockStateProperties, Integer> mappedBlocks = HashBasedTable.create();

        ListNBT size = nbt.getList("size", 3);
        ListNBT palette = nbt.getList("palette", 10);
        ListNBT entities = nbt.getList("entities", 10);
        ListNBT blocks = nbt.getList("blocks", 10);
        //ListNBT entities = nbt.getList("entities", entityListSize);
        System.out.println("Version:[" + nbt.getInt("DataVersion") + "]");
        System.out.println("Size[" + size.getInt(0) + "," + size.getInt(1) + "," + size.getInt(2) + "]");
        System.out.println("Palette:");
        for (int i = 0; i <= palette.size() - 1; i++) {
            System.out.println(palette.getCompound(i).toString());
        }
        System.out.println("Entities:");
        for (int j = 0; j <= entities.size() - 1; j++) {
            System.out.println(entities.getCompound(j).toString());
        }
        System.out.println("Blocks:");
        for (int k = 0; k <= blocks.size() - 1; k++) {
            System.out.println(blocks.getCompound(k).toString());
        }
        System.out.println("<Special Data>");
        for (int i = 0; i <= palette.size() - 1; i++) {
            CompoundNBT pal = palette.getCompound(i);
            String blockName = pal.get("Name").toString().replace("minecraft:", "").replace("\"", "");
            Block block = BLOCKS.getValue(new ResourceLocation(blockName));

            //mappedBlocks.put(block, , i);
        }
        System.out.println("Block Properties:");
        for (int i = 0; i <= palette.size() - 1; i++) {
            CompoundNBT pal = palette.getCompound(i);
            if (pal.get("Properties") != null) {
                System.out.println("[" + i + "] has values:");
                String rawProperties = pal.get("Properties").toString().replace("\"", "\\" + "\"").trim();
                System.out.println(rawProperties);
                /*JsonObject propertiesAsJson = new JsonParser().parse(rawProperties).getAsJsonObject();
                propertiesAsJson.entrySet().forEach((basicProperty) -> {
                    String propertyType = basicProperty.getKey();
                    String propertyValue = basicProperty.getValue().toString();
                    System.out.println(propertyType + "<:>" + propertyValue);
                });*/
            } else {
                System.out.println("[" + i + "]");
            }
        }
        System.out.println("Block Positions:");
        for (int i = 0; i <= blocks.size() - 1; i++) {
            CompoundNBT blk = blocks.getCompound(i);
            ListNBT posList = blk.getList("pos", 3);
            System.out.println(posList);
        }
        //System.out.println(BLOCKSTATE);
        System.out.println("Final Objective");
        for (int i = 0; i <= blocks.size() - 1; i++) {
            CompoundNBT blk = blocks.getCompound(i);
            ListNBT posList = blk.getList("pos", 3);
            BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(posList.getInt(0), posList.getInt(1), posList.getInt(2));


            System.out.println(posList);
        }
    }

    private static void getProperties(CompoundNBT nbt) {
        Map<BlockStateProperty, String> props = new HashMap<BlockStateProperty, String>();

        ListNBT palette = nbt.getList("palette", 10);
        for (int i = 0; i <= palette.size() - 1; i++) {
            CompoundNBT pal = palette.getCompound(i);
            if (pal.get("Properties") != null) {
                ListNBT properties = pal.getList("Properties", 10);
                for (int j = 0; j <= properties.size(); j++) {
                    String property = properties.get(j).toString();
                    System.out.println(property);
                }
                //JsonUtils.ImmutableMapTypeAdapter.
                //props.put(getPropertyFromString())
            }
        }
        //BlockState states = new BlockState(Blocks.PUMPKIN, map);
    }

    //Dumb idea, however, BlockstateProperties aren't enums, so im gonna have to suffer
    private static BlockStateProperty getPropertyFromString(String propertyName) {
        Object o = null;
        if (propertyName.equals(BlockStateProperties.ATTACHED.getName())) {
            o = BlockStateProperties.ATTACHED;
        }
        if (propertyName.equals(BlockStateProperties.BOTTOM.getName())) {
            o = BlockStateProperties.BOTTOM;
        }
        return (BlockStateProperty) o;
    }
}
