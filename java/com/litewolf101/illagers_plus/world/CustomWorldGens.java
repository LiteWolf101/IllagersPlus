package com.litewolf101.illagers_plus.world;

import com.litewolf101.illagers_plus.IllagersPlus;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.chunk.AbstractChunkProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.ArrayList;
import java.util.Random;

public class CustomWorldGens /*implements IWorldGenerator*/ {
    //TODO Remove this class after full structure port
    /*public static final ResourceLocation ILLAGER_ARCHER_TOWER = new ResourceLocation(IllagersPlus.MOD_ID, "illager_archer_tower");

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, ChunkGenerator chunkGenerator, AbstractChunkProvider chunkProvider) {
        generateIllagerArcherTower(world, random, chunkX, chunkZ, IllagersPlusConfig.illager_archer_tower_freq 50, Blocks.GRASS);
    }

    private void generateIllagerArcherTower(World world, Random random, int chunkX, int chunkZ, int chance, Block topBlock){
        ArrayList<Biome> SpawnableBiomes = new ArrayList<Biome>();
        SpawnableBiomes.add(Biomes.PLAINS);
        SpawnableBiomes.add(Biomes.FOREST);
        SpawnableBiomes.add(Biomes.SNOWY_TAIGA);
        SpawnableBiomes.add(Biomes.TAIGA);
        SpawnableBiomes.add(Biomes.SAVANNA);
        SpawnableBiomes.add(Biomes.DESERT);
        SpawnableBiomes.add(Biomes.SNOWY_TUNDRA);
        SpawnableBiomes.add(Biomes.DARK_FOREST);

        if (!(world instanceof ServerWorld))
            return;
        ServerWorld serverWorld = (ServerWorld) world;


        int x = chunkX * 16;
        int z = chunkZ * 16;
        int y = calculateGenerationHeight(world, x, z, topBlock);
        BlockPos pos = new BlockPos(x,y,z);

        if(world.getWorldType() != WorldType.FLAT){
            if (SpawnableBiomes.contains(world.getBiome(pos))){
                if(random.nextInt(chance) == 0){
                    if (compareFloor(world, pos, 14, 14, ILLAGER_ARCHER_TOWER.getStructureRotation(), topBlock)) {
                    MinecraftServer server = serverWorld.getServer();
                    Template template;
                    template = serverWorld.getStructureTemplateManager().getTemplate(ILLAGER_ARCHER_TOWER);

                    PlacementSettings settings = new PlacementSettings();

                    BlockPos size = template.getSize();
                    for (int x1 = 0; x < size.getX(); x++)
                        for (int y1 = 0; y < size.getY(); y++)
                            for (int z1 = 0; z < size.getZ(); z++) {
                                template.addBlocksToWorld(world, pos, settings);
                            }
                    }
                }
            }
        }
    }

    private static int calculateGenerationHeight(World world, int x, int z, Block topBlock){
        int y = world.getHeight();
        boolean foundGround = false;

        while(!foundGround && y-- >= world.getSeaLevel() + 1){
            Block block = world.getBlockState(new BlockPos(x,y,z)).getBlock();
            foundGround = block == topBlock;
        }
        return y;
    }
    //Handles the rotation of the Illager Tower and applies loot and entity appropriately.
    private void HandleIllagerTower (World world, Random random, BlockPos pos) {

        Random random1 = new Random();
        int F2_RAND = random.nextInt(10);
        int F3_RAND = random1.nextInt(10);



        //F1
        ILLAGER_TOWER_F1.generateStructureRotRandom(world, random, pos);

        //Rot NONE
        if (ILLAGER_TOWER_F1.getStructureRotation() == Rotation.NONE) {
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(9, 1, 26));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(9, 1, 27));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(9, 2, 26));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(9, 2, 27));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(22, 1, 26));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(22, 1, 27));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(22, 2, 26));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(22, 2, 27));

            spawnVindicator(world, pos.add(13, 1, 7));
            spawnVindicator(world, pos.add(18, 1, 7));
            spawnFurantur(world, pos.add(11, 1, 13));
            spawnFurantur(world, pos.add(20, 1, 13));
            spawnEnchanter(world, pos.add(15.5, 1, 22));
            spawnArcher(world, pos.add(14.5, 1, 22));
            spawnArcher(world, pos.add(16.5, 1, 22));

            if (F2_RAND <= 5) {
                ILLAGER_TOWER_F2_A.generateStructureRotNone(world, pos.up(12));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(26, 13, 26));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(25, 13, 26));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(23, 13, 26));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(22, 13, 26));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(26, 14, 26));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(25, 14, 26));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(23, 14, 26));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(22, 14, 26));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(26, 15, 26));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(25, 15, 26));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(23, 15, 26));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(22, 15, 26));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(26, 16, 26));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(25, 16, 26));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(23, 16, 26));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(22, 16, 26));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RESEARCHER, pos.add(26, 13, 5));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RESEARCHER, pos.add(20, 13, 7));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RESEARCHER, pos.add(20, 13, 8));

                spawnHoarder(world, pos.add(23, 13, 7.5));
                spawnNecromancer(world, pos.add(24, 13, 23));
                spawnEvoker(world, pos.add(13, 13, 9));
                spawnArcher(world,pos.add(17, 18, 9));

                ILLAGER_TOWER_F3_A.generateStructureRotNone(world, pos.up(24));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_TREES, pos.add(23, 25, 16));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(11, 25, 7));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_TNT_TRAP, pos.add(24, 30, 24));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(15, 30, 18));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(15, 30, 19));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(18, 30, 15));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(19, 30, 15));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(8, 30, 7));

                spawnVindicator(world, pos.add(13, 25, 7));
                spawnEnchanter(world, pos.add(10, 25, 17));
                spawnVindicator(world, pos.add(13,30,14));
                spawnVindicator(world, pos.add(14,30,13));

                ILLAGER_TOWER_F4_A.generateStructureRotNone(world, pos.up(36));

            } else {
                ILLAGER_TOWER_F2_B.generateStructureRotNone(world, pos.up(12));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(15, 13, 5));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(16, 13, 5));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(11, 13, 13));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(12, 13, 13));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(20, 13, 13));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(19, 13, 13));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(5, 18, 15));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(26, 18, 15));

                spawnVindicator(world, pos.add(5, 13, 5));
                spawnVindicator(world, pos.add(26, 13, 5));
                spawnVindicator(world, pos.add(5, 13, 26));
                spawnVindicator(world, pos.add(26, 13, 26));
                spawnArcher(world, pos.add(5, 18, 5));
                spawnArcher(world, pos.add(26, 18, 5));
                spawnArcher(world, pos.add(5, 18, 26));
                spawnArcher(world, pos.add(26, 18, 26));

                if (F3_RAND <= 5) {
                    ILLAGER_TOWER_F3_B.generateStructureRotNone(world, pos.up(24));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_FARMS, pos.add(5, 25, 10));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_FARMS, pos.add(5, 26, 10));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_FARMS, pos.add(10, 25, 8));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_FARMS, pos.add(10, 26, 8));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_MECHANICS, pos.add(6, 26, 21));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_ARMORY, pos.add(24, 25, 26));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_ARMORY, pos.add(23, 25, 26));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_ARMORY, pos.add(26, 25, 24));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_ARMORY, pos.add(26, 25, 23));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_BREWERY, pos.add(22, 25, 5));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_BREWERY, pos.add(23, 25, 5));
                } else {
                    ILLAGER_TOWER_F3_C.generateStructureRotNone(world, pos.up(24));
                }
                ILLAGER_TOWER_F4_B.generateStructureRotNone(world, pos.up(36));
            }
            ILLAGER_TOWER_F5.generateStructureRotNone(world, pos.up(48));
            setLoot(world, random, LootTableList.CHESTS_SIMPLE_DUNGEON, pos.add(15, 51, 8));
            setLoot(world, random, LootTableList.CHESTS_SIMPLE_DUNGEON, pos.add(16, 51, 8));

            spawnIllusioner(world, pos.add(15.5, 49, 15.5));
            spawnArcher(world, pos.add(14, 51, 9));
            spawnArcher(world, pos.add(17, 51, 9));

            ILLAGER_TOWER_F6.generateStructureRotNone(world, pos.up(58));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RARE, pos.add(13, 59, 18));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RARE, pos.add(13, 59, 19));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RARE, pos.add(13, 60, 18));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RARE, pos.add(13, 60, 19));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RARE, pos.add(15, 59, 21));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RARE, pos.add(16, 59, 21));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RARE, pos.add(18, 59, 18));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RARE, pos.add(18, 59, 19));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RARE, pos.add(18, 60, 18));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RARE, pos.add(18, 60, 19));

            spawnIllagerKing(world, pos.add(15.5, 60, 14.5));
        }
    }

    //Generates an Illager Centre.
     //@param classes Biome class
     //@param topBlock The Block to spawn on

    private void generateIllagerCentre(World world, Random random, int chunkX, int chunkZ, int chance, Block topBlock, Class<?>... classes){
        ArrayList<Class<?>> classesList = new ArrayList<Class<?>>(Arrays.asList(classes));

        int x = chunkX * 16;
        int z = chunkZ * 16;
        int y = calculateGenerationHeight(world, x, z, topBlock);
        BlockPos pos = new BlockPos(x,y,z);

        Class<?> biome = world.provider.getBiomeForCoords(pos).getClass();

        if(world.getWorldType() != WorldType.FLAT){
            if (classesList.contains(biome)){
                if(random.nextInt(chance) == 0){
                    if (compareFloor(world, pos, 9, 10, ILLAGER_CENTRE.getStructureRotation(), topBlock)) {
                        ILLAGER_CENTRE.generateStructureRotNone(world, pos);
                        if (ILLAGER_CENTRE.getStructureRotation() == Rotation.NONE) {
                            setLoot(world, random, LootTableList.CHESTS_SIMPLE_DUNGEON, pos.add(15, 1, 9));
                            setLoot(world, random, LootTableList.CHESTS_SIMPLE_DUNGEON, pos.add(15, 1, 10));

                            setLoot(world, random, LootTableList.CHESTS_SIMPLE_DUNGEON, pos.add(9, 1, 16));
                            setLoot(world, random, LootTableList.CHESTS_SIMPLE_DUNGEON, pos.add(8, 1, 16));

                            setLoot(world, random, LootTableList.CHESTS_SIMPLE_DUNGEON, pos.add(2, 1, 9));
                            setLoot(world, random, LootTableList.CHESTS_SIMPLE_DUNGEON, pos.add(2, 1, 10));

                            setLoot(world, random, IllagerPlusLootTable.ILLAGER_CENTRE, pos.add(9, 9, 9));
                            setLoot(world, random, IllagerPlusLootTable.ILLAGER_CENTRE, pos.add(9, 9, 10));
                            setLoot(world, random, IllagerPlusLootTable.ILLAGER_CENTRE, pos.add(8, 9, 9));
                            setLoot(world, random, IllagerPlusLootTable.ILLAGER_CENTRE, pos.add(8, 9, 10));
                            setLoot(world, random, IllagerPlusLootTable.ILLAGER_CENTRE, pos.add(9, 10, 9));
                            setLoot(world, random, IllagerPlusLootTable.ILLAGER_CENTRE, pos.add(9, 10, 10));
                            setLoot(world, random, IllagerPlusLootTable.ILLAGER_CENTRE, pos.add(8, 10, 9));
                            setLoot(world, random, IllagerPlusLootTable.ILLAGER_CENTRE, pos.add(8, 10, 10));

                            spawnFurantur(world, pos.add(7, 1, 8));
                            spawnFurantur(world, pos.add(7, 1, 11));
                            spawnFurantur(world, pos.add(10, 1, 8));
                            spawnFurantur(world, pos.add(10, 1, 11));
                            spawnEvoker(world, pos.add(8.5, 1, 9.5));

                            spawnVindicator(world, pos.add(5, 9, 6));
                            spawnVindicator(world, pos.add(12, 9, 6));
                            spawnVindicator(world, pos.add(5, 9, 13));
                            spawnVindicator(world, pos.add(12, 9, 13));

                        } else if (ILLAGER_CENTRE.getStructureRotation() == Rotation.CLOCKWISE_90) {
                            setLoot(world, random, LootTableList.CHESTS_SIMPLE_DUNGEON, pos.add(-9, 1, 15));
                            setLoot(world, random, LootTableList.CHESTS_SIMPLE_DUNGEON, pos.add(-10, 1, 15));

                            setLoot(world, random, LootTableList.CHESTS_SIMPLE_DUNGEON, pos.add(-16, 1, 9));
                            setLoot(world, random, LootTableList.CHESTS_SIMPLE_DUNGEON, pos.add(-16, 1, 8));

                            setLoot(world, random, LootTableList.CHESTS_SIMPLE_DUNGEON, pos.add(-9, 1, 2));
                            setLoot(world, random, LootTableList.CHESTS_SIMPLE_DUNGEON, pos.add(-10, 1, 2));

                            setLoot(world, random, IllagerPlusLootTable.ILLAGER_CENTRE, pos.add(-9, 9, 9));
                            setLoot(world, random, IllagerPlusLootTable.ILLAGER_CENTRE, pos.add(-10, 9, 9));
                            setLoot(world, random, IllagerPlusLootTable.ILLAGER_CENTRE, pos.add(-9, 9, 8));
                            setLoot(world, random, IllagerPlusLootTable.ILLAGER_CENTRE, pos.add(-10, 9, 8));
                            setLoot(world, random, IllagerPlusLootTable.ILLAGER_CENTRE, pos.add(-9, 10, 9));
                            setLoot(world, random, IllagerPlusLootTable.ILLAGER_CENTRE, pos.add(-10, 10, 9));
                            setLoot(world, random, IllagerPlusLootTable.ILLAGER_CENTRE, pos.add(-9, 10, 8));
                            setLoot(world, random, IllagerPlusLootTable.ILLAGER_CENTRE, pos.add(-10, 10, 8));

                            spawnFurantur(world, pos.add(-8, 1, 7));
                            spawnFurantur(world, pos.add(-11, 1, 7));
                            spawnFurantur(world, pos.add(-8, 1, 10));
                            spawnFurantur(world, pos.add(-11, 1, 10));
                            spawnEvoker(world, pos.add(-9.5, 1, 8.5));

                            spawnVindicator(world, pos.add(-6, 9, 5));
                            spawnVindicator(world, pos.add(-6, 9, 12));
                            spawnVindicator(world, pos.add(-13, 9, 5));
                            spawnVindicator(world, pos.add(-13, 9, 12));

                        } else if (ILLAGER_CENTRE.getStructureRotation() == Rotation.CLOCKWISE_180) {
                            setLoot(world, random, LootTableList.CHESTS_SIMPLE_DUNGEON, pos.add(-15, 1, -9));
                            setLoot(world, random, LootTableList.CHESTS_SIMPLE_DUNGEON, pos.add(-15, 1, -10));

                            setLoot(world, random, LootTableList.CHESTS_SIMPLE_DUNGEON, pos.add(-9, 1, -16));
                            setLoot(world, random, LootTableList.CHESTS_SIMPLE_DUNGEON, pos.add(-8, 1, -16));

                            setLoot(world, random, LootTableList.CHESTS_SIMPLE_DUNGEON, pos.add(-2, 1, -9));
                            setLoot(world, random, LootTableList.CHESTS_SIMPLE_DUNGEON, pos.add(-2, 1, -10));

                            setLoot(world, random, IllagerPlusLootTable.ILLAGER_CENTRE, pos.add(-9, 9, -9));
                            setLoot(world, random, IllagerPlusLootTable.ILLAGER_CENTRE, pos.add(-9, 9, -10));
                            setLoot(world, random, IllagerPlusLootTable.ILLAGER_CENTRE, pos.add(-8, 9, -9));
                            setLoot(world, random, IllagerPlusLootTable.ILLAGER_CENTRE, pos.add(-8, 9, -10));
                            setLoot(world, random, IllagerPlusLootTable.ILLAGER_CENTRE, pos.add(-9, 10, -9));
                            setLoot(world, random, IllagerPlusLootTable.ILLAGER_CENTRE, pos.add(-9, 10, -10));
                            setLoot(world, random, IllagerPlusLootTable.ILLAGER_CENTRE, pos.add(-8, 10, -9));
                            setLoot(world, random, IllagerPlusLootTable.ILLAGER_CENTRE, pos.add(-8, 10, -10));

                            spawnFurantur(world, pos.add(-7, 1, -8));
                            spawnFurantur(world, pos.add(-7, 1, -11));
                            spawnFurantur(world, pos.add(-10, 1, -8));
                            spawnFurantur(world, pos.add(-10, 1, -11));
                            spawnEvoker(world, pos.add(-8.5, 1, -9.5));

                            spawnVindicator(world, pos.add(-5, 9, -6));
                            spawnVindicator(world, pos.add(-12, 9, -6));
                            spawnVindicator(world, pos.add(-5, 9, -13));
                            spawnVindicator(world, pos.add(-12, 9, -13));

                        } else if (ILLAGER_CENTRE.getStructureRotation() == Rotation.COUNTERCLOCKWISE_90) {
                            setLoot(world, random, LootTableList.CHESTS_SIMPLE_DUNGEON, pos.add(9, 1, -15));
                            setLoot(world, random, LootTableList.CHESTS_SIMPLE_DUNGEON, pos.add(10, 1, -15));

                            setLoot(world, random, LootTableList.CHESTS_SIMPLE_DUNGEON, pos.add(16, 1, -9));
                            setLoot(world, random, LootTableList.CHESTS_SIMPLE_DUNGEON, pos.add(16, 1, -8));

                            setLoot(world, random, LootTableList.CHESTS_SIMPLE_DUNGEON, pos.add(9, 1, -2));
                            setLoot(world, random, LootTableList.CHESTS_SIMPLE_DUNGEON, pos.add(10, 1, -2));

                            setLoot(world, random, IllagerPlusLootTable.ILLAGER_CENTRE, pos.add(9, 9, -9));
                            setLoot(world, random, IllagerPlusLootTable.ILLAGER_CENTRE, pos.add(10, 9, -9));
                            setLoot(world, random, IllagerPlusLootTable.ILLAGER_CENTRE, pos.add(9, 9, -8));
                            setLoot(world, random, IllagerPlusLootTable.ILLAGER_CENTRE, pos.add(10, 9, -8));
                            setLoot(world, random, IllagerPlusLootTable.ILLAGER_CENTRE, pos.add(9, 10, -9));
                            setLoot(world, random, IllagerPlusLootTable.ILLAGER_CENTRE, pos.add(10, 10, -9));
                            setLoot(world, random, IllagerPlusLootTable.ILLAGER_CENTRE, pos.add(9, 10, -8));
                            setLoot(world, random, IllagerPlusLootTable.ILLAGER_CENTRE, pos.add(10, 10, -8));

                            spawnFurantur(world, pos.add(8, 1, -7));
                            spawnFurantur(world, pos.add(11, 1, -7));
                            spawnFurantur(world, pos.add(8, 1, -10));
                            spawnFurantur(world, pos.add(11, 1, -10));
                            spawnEvoker(world, pos.add(9.5, 1, -8.5));

                            spawnVindicator(world, pos.add(6, 9, -5));
                            spawnVindicator(world, pos.add(6, 9, -12));
                            spawnVindicator(world, pos.add(13, 9, -5));
                            spawnVindicator(world, pos.add(13, 9, -12));

                        }

                        createIllagerCentreDecor(ILLAGER_FARM, 6, 2, 6, world, random, pos, topBlock);
                        createIllagerCentreDecor(ANIMAL_PEN, 4, 5, 4, world, random, pos, topBlock);
                        createIllagerCentreDecor(FIREWORKS_DISPLAY, 3, 2, 4, world, random, pos, topBlock);
                        createIllagerCentreDecor(FLOWER_BED, 6, 2, 3, world, random, pos, topBlock);
                        createIllagerCentreDecor(ILLAGER_DUMMY_1, 3, 3, 1, world, random, pos, topBlock);
                        createIllagerCentreDecor(ILLAGER_DUMMY_2, 3, 3, 2, world, random, pos, topBlock);
                        createIllagerCentreDecor(ILLAGER_STORAGE, 4, 4, 4, world, random, pos, topBlock);
                        createIllagerCentreDecor(ILLAGER_WELL, 4, 14, 4, world, random, pos, topBlock);
                        createIllagerCentreDecor(LOG_PILE, 3, 3, 3, world, random, pos, topBlock);
                        createIllagerCentreDecor(UNDYING_TOTEM, 9, 11, 3, world, random, pos, topBlock);
                    }
                }
            }
        }
    }

    private void createIllagerCentreDecor (StructureHandler handler, double xSize, double ySize, double zSize, World world, Random random, BlockPos pos, Block topBlock) {
        int x = random.nextInt(120) - 60;
        int z = random.nextInt(120) - 60;
        BlockPos decorpos = new BlockPos(pos.getX() + x, calculateGenerationHeight(world, pos.getX() + x, pos.getZ() + z, topBlock), pos.getZ() + z);
        AxisAlignedBB avoidanceBB = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 28, pos.getY() + 29, pos.getZ() + 29);

        this.addStructureBB(handler, new AxisAlignedBB(decorpos).grow(xSize, ySize, zSize));

        if (!getStructureBB(handler).intersects(avoidanceBB)) {
            if (world.getBlockState(decorpos.down()).getMaterial() != Material.WATER) {
                if (handler.structureName.equals("illager_well")) {
                    handler.generateStructureRotRandom(world, random, decorpos.down(9));
                } else if (handler.structureName.equals("animal_pen") || handler.structureName.equals("illager_farm") || handler.structureName.equals("fireworks_display")){
                    handler.generateStructureRotRandom(world, random, decorpos);
                } else {
                    handler.generateStructureRotRandom(world, random, decorpos.up());
                }
            }
        }
    }*/
}
