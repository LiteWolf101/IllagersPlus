package com.litewolf101.illagersplus.utils;

import net.minecraft.nbt.*;

import java.io.*;

public class StructureNBTHandler {
    public static void readFile(String structureName, int blockPaletteListSize/*, int entityListSize*/) {
        String locationName = "/src/main/resources/data/illagers_plus/structures/" + structureName + ".nbt";
        String basePath = new File("").getAbsolutePath();
        File basePath1 = new File(basePath).getParentFile();
        String path = new File(basePath1.getAbsolutePath() + locationName).getAbsolutePath();
        if (path != null) {
            try {
                DataInputStream datainputstream = new DataInputStream(new FileInputStream(path));
                CompoundNBT nbt = CompressedStreamTools.readCompressed(datainputstream);
                try {
                    parseNBTIntoValues(nbt, blockPaletteListSize/*, entityListSize*/);
                } finally {
                    datainputstream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void parseNBTIntoValues(CompoundNBT nbt, int blockPaletteListSize /*,int entityListSize*/) {
        ListNBT size = nbt.getList("size", 3);
        ListNBT palette = nbt.getList("palette", 10);
        //ListNBT entities = nbt.getList("entities", entityListSize);
        for (int i = 0; i <= palette.size(); i++) {
            System.out.println(palette.getCompound(i).toString());
        }
        System.out.println("Version:[" + nbt.getInt("DataVersion") + "]");
        System.out.println("Size[" + size.getInt(1) + "," + size.getInt(2) + "," + size.getInt(3) + "]");

    }
}
