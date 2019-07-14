package com.litewolf101.world;

import com.google.common.collect.Maps;
import com.litewolf101.IllagersPlus;
import com.litewolf101.objects.entities.*;
import com.litewolf101.utils.IllagerPlusLootTable;
import com.litewolf101.utils.IllagersPlusConfig;
import com.litewolf101.utils.StructureHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntityIllusionIllager;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.*;

public class WorldGenCustomStructures implements IWorldGenerator {
    private final Map<String, AxisAlignedBB> StructureMap = Maps.<String, AxisAlignedBB>newHashMap();

    //Init Structures
    public static final StructureHandler ILLAGER_TOWER_F1 = new StructureHandler("illager_tower_f1");
    public static final StructureHandler ILLAGER_TOWER_F2_A = new StructureHandler("illager_tower_f2_a");
    public static final StructureHandler ILLAGER_TOWER_F2_B = new StructureHandler("illager_tower_f2_b");
    public static final StructureHandler ILLAGER_TOWER_F3_A = new StructureHandler("illager_tower_f3_a");
    public static final StructureHandler ILLAGER_TOWER_F3_B = new StructureHandler("illager_tower_f3_b");
    public static final StructureHandler ILLAGER_TOWER_F3_C = new StructureHandler("illager_tower_f3_c");
    public static final StructureHandler ILLAGER_TOWER_F4_A = new StructureHandler("illager_tower_f4_a");
    public static final StructureHandler ILLAGER_TOWER_F4_B = new StructureHandler("illager_tower_f4_b");
    public static final StructureHandler ILLAGER_TOWER_F5 = new StructureHandler("illager_tower_f5");
    public static final StructureHandler ILLAGER_TOWER_F6 = new StructureHandler("illager_tower_f6");

    public static final StructureHandler ILLAGER_ARCHER_TOWER = new StructureHandler("illager_archer_tower");

    public static final StructureHandler ILLAGER_CENTRE = new StructureHandler("illager_centre");
    public static final StructureHandler ANIMAL_PEN = new StructureHandler("animal_pen");
    public static final StructureHandler FIREWORKS_DISPLAY = new StructureHandler("fireworks_display");
    public static final StructureHandler FLOWER_BED = new StructureHandler("flower_bed");
    public static final StructureHandler ILLAGER_DUMMY_1 = new StructureHandler("illager_dummy_1");
    public static final StructureHandler ILLAGER_DUMMY_2 = new StructureHandler("illager_dummy_2");
    public static final StructureHandler ILLAGER_FARM = new StructureHandler("illager_farm");
    public static final StructureHandler ILLAGER_STORAGE = new StructureHandler("illager_storage");
    public static final StructureHandler ILLAGER_WELL = new StructureHandler("illager_well");
    public static final StructureHandler LOG_PILE = new StructureHandler("log_pile");
    public static final StructureHandler UNDYING_TOTEM = new StructureHandler("undying_totem");

    public void addStructureBB (StructureHandler handler, AxisAlignedBB bb) {
        AxisAlignedBB adjustForRotationalBB = bb.offset(-biggestStructureSize(bb), 0, -biggestStructureSize(bb)).grow(2*biggestStructureSize(bb), 0, 2*biggestStructureSize(bb));
        if (handler != null) {
            this.StructureMap.put(handler.structureName, adjustForRotationalBB);
        }
    }

    public double biggestStructureSize (AxisAlignedBB bb) {
        double x = bb.maxX;
        double z = bb.maxZ;
        return x >= z ? x : z;
    }

    public AxisAlignedBB getStructureBB (StructureHandler handler){
        for (Map.Entry<String, AxisAlignedBB> entry : this.StructureMap.entrySet()) {
            if (handler.structureName.equals(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        switch (world.provider.getDimension()){
            case 0:
                generateIllagerTower(world, random, chunkX, chunkZ, IllagersPlusConfig.illager_tower_freq, Blocks.GRASS, Biomes.PLAINS.getBiomeClass());
                generateIllagerTower(world, random, chunkX, chunkZ, IllagersPlusConfig.illager_tower_freq, Blocks.GRASS, Biomes.ROOFED_FOREST.getBiomeClass());
                generateIllagerCentre(world, random, chunkX, chunkZ, IllagersPlusConfig.illager_centre_freq, Blocks.GRASS, Biomes.PLAINS.getBiomeClass());
                generateIllagerArcherTower(world, random, chunkX, chunkZ, IllagersPlusConfig.illager_archer_tower_freq, Blocks.GRASS, Biomes.PLAINS.getBiomeClass());
                generateIllagerArcherTower(world, random, chunkX, chunkZ, IllagersPlusConfig.illager_archer_tower_freq, Blocks.GRASS, Biomes.FOREST.getBiomeClass());
                generateIllagerArcherTower(world, random, chunkX, chunkZ, IllagersPlusConfig.illager_archer_tower_freq, Blocks.GRASS, Biomes.SAVANNA.getBiomeClass());
                generateIllagerArcherTower(world, random, chunkX, chunkZ, IllagersPlusConfig.illager_archer_tower_freq, Blocks.GRASS, Biomes.TAIGA.getBiomeClass());
                generateIllagerArcherTower(world, random, chunkX, chunkZ, IllagersPlusConfig.illager_archer_tower_freq, Blocks.SAND, Biomes.DESERT.getBiomeClass());
                break;
            case 1:
                break;
            case -1:
                break;
        }
    }

    /**Generates the structure in a specific biome on a specific block.
     *@param classes Biome class
     *@param topBlock The Block to spawn on
     */
    private void generateStructure(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, int chance, Block topBlock, Class<?>... classes){
        ArrayList<Class<?>> classesList = new ArrayList<Class<?>>(Arrays.asList(classes));

        int x = chunkX * 16;
        int z = chunkZ * 16;
        int y = calculateGenerationHeight(world, x, z, topBlock);
        BlockPos pos = new BlockPos(x,y,z);

        Class<?> biome = world.provider.getBiomeForCoords(pos).getClass();

        if(world.getWorldType() != WorldType.FLAT){
            if (classesList.contains(biome)){
                if(random.nextInt(chance) == 0){
                    generator.generate(world, random, pos);
                }
            }
        }
    }

    /**Generates the structure in a specific biome on a specific block, with a specific type of rotation.
     *@param rotationType The type of rotation for the structure
     *@param classes Biome class
     *@param topBlock The Block to spawn on
     */
    private void generateStructure(StructureHandler structure, StructureHandler.RotationType rotationType, World world, Random random, int chunkX, int chunkZ, int chance, Block topBlock, Class<?>... classes){
        ArrayList<Class<?>> classesList = new ArrayList<Class<?>>(Arrays.asList(classes));

        int x = chunkX * 16;
        int z = chunkZ * 16;
        int y = calculateGenerationHeight(world, x, z, topBlock);
        BlockPos pos = new BlockPos(x,y,z);

        Class<?> biome = world.provider.getBiomeForCoords(pos).getClass();

        if(world.getWorldType() != WorldType.FLAT){
            if (classesList.contains(biome)){
                if(random.nextInt(chance) == 0){
                    if (rotationType == StructureHandler.RotationType.NONE) {
                        structure.generateStructureRotNone(world, pos);
                    } else if (rotationType == StructureHandler.RotationType.DEGREES90) {
                        structure.generateStructureRot90(world, pos);
                    } else if (rotationType == StructureHandler.RotationType.DEGREES180) {
                        structure.generateStructureRot180(world, pos);
                    } else if (rotationType == StructureHandler.RotationType.DEGREES270) {
                        structure.generateStructureRot270(world, pos);
                    } else if (rotationType == StructureHandler.RotationType.RANDOM) {
                        structure.generateStructureRotRandom(world, random, pos);
                    }
                }
            }
        }
    }

    /**Generates the structure on a specific block regardless of biome.
     *@param topBlock The Block to spawn on
     */
    private void generateStructure(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, int chance, Block topBlock){
        int x = chunkX * 16;
        int z = chunkZ * 16;
        int y = calculateGenerationHeight(world, x, z, topBlock);
        BlockPos pos = new BlockPos(x,y,z);

        if(world.getWorldType() != WorldType.FLAT){
            if(random.nextInt(chance) == 0){
                generator.generate(world, random, pos);
            }
        }
    }

    /**Generates the structure on a specific block, with a specific type of rotation regardless of biome.
     *@param rotationType The type of rotation for the structure
     *@param topBlock The Block to spawn on
     */
    private void generateStructure(StructureHandler structure, StructureHandler.RotationType rotationType, World world, Random random, int chunkX, int chunkZ, int chance, Block topBlock){
        int x = chunkX * 16;
        int z = chunkZ * 16;
        int y = calculateGenerationHeight(world, x, z, topBlock);
        BlockPos pos = new BlockPos(x,y,z);

        if(world.getWorldType() != WorldType.FLAT){
            if(random.nextInt(chance) == 0){
                if (rotationType == StructureHandler.RotationType.NONE) {
                    structure.generateStructureRotNone(world, pos);
                } else if (rotationType == StructureHandler.RotationType.DEGREES90) {
                    structure.generateStructureRot90(world, pos);
                } else if (rotationType == StructureHandler.RotationType.DEGREES180) {
                    structure.generateStructureRot180(world, pos);
                } else if (rotationType == StructureHandler.RotationType.DEGREES270) {
                    structure.generateStructureRot270(world, pos);
                } else if (rotationType == StructureHandler.RotationType.RANDOM) {
                    structure.generateStructureRotRandom(world, random, pos);
                }
            }
        }
    }

    /**Generates an Illager Tower.
     *@param classes Biome class
     *@param topBlock The Block to spawn on
     */
    private void generateIllagerTower(World world, Random random, int chunkX, int chunkZ, int chance, Block topBlock, Class<?>... classes){
        ArrayList<Class<?>> classesList = new ArrayList<Class<?>>(Arrays.asList(classes));

        int x = chunkX * 16;
        int z = chunkZ * 16;
        int y = calculateGenerationHeight(world, x, z, topBlock);
        BlockPos pos = new BlockPos(x,y,z);

        Class<?> biome = world.provider.getBiomeForCoords(pos).getClass();

        if(world.getWorldType() != WorldType.FLAT){
            if (classesList.contains(biome)){
                if(random.nextInt(chance) == 0){
                    if (compareFloor(world, pos, 24, 24, ILLAGER_TOWER_F1.getStructureRotation(), topBlock)) {
                        HandleIllagerTower(world, random, pos);
                    }
                }
            }
        }
    }

    private void spawnArcher(World world, BlockPos pos) {
        EntityArcher archer = new EntityArcher(world);
        archer.enablePersistence();
        archer.moveToBlockPosAndAngles(pos, 0.0F, 0.0F);
        archer.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(archer)), (IEntityLivingData)null);
        world.spawnEntity(archer);
    }

    private void spawnEnchanter(World world, BlockPos pos) {
        EntityEnchanter enchanter = new EntityEnchanter(world);
        enchanter.enablePersistence();
        enchanter.moveToBlockPosAndAngles(pos, 0.0F, 0.0F);
        enchanter.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(enchanter)), (IEntityLivingData)null);
        world.spawnEntity(enchanter);
    }

    private void spawnFurantur(World world, BlockPos pos) {
        EntityFurantur furantur = new EntityFurantur(world);
        furantur.enablePersistence();
        furantur.moveToBlockPosAndAngles(pos, 0.0F, 0.0F);
        furantur.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(furantur)), (IEntityLivingData)null);
        world.spawnEntity(furantur);
    }

    private void spawnHoarder(World world, BlockPos pos) {
        EntityHoarder hoarder = new EntityHoarder(world);
        hoarder.enablePersistence();
        hoarder.moveToBlockPosAndAngles(pos, 0.0F, 0.0F);
        hoarder.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(hoarder)), (IEntityLivingData)null);
        world.spawnEntity(hoarder);
    }

    private void spawnIllagerKing(World world, BlockPos pos) {
        EntityIllagerKing king = new EntityIllagerKing(world);
        king.enablePersistence();
        king.moveToBlockPosAndAngles(pos, 0.0F, 0.0F);
        king.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(king)), (IEntityLivingData)null);
        world.spawnEntity(king);
    }

    private void spawnNecromancer(World world, BlockPos pos) {
        EntityNecromancer necromancer = new EntityNecromancer(world);
        necromancer.enablePersistence();
        necromancer.moveToBlockPosAndAngles(pos, 0.0F, 0.0F);
        necromancer.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(necromancer)), (IEntityLivingData)null);
        world.spawnEntity(necromancer);
    }

    private void spawnVindicator(World world, BlockPos pos) {
        EntityVindicator vindicator = new EntityVindicator(world);
        vindicator.enablePersistence();
        vindicator.moveToBlockPosAndAngles(pos, 0.0F, 0.0F);
        vindicator.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(vindicator)), (IEntityLivingData)null);
        world.spawnEntity(vindicator);
    }

    private void spawnEvoker(World world, BlockPos pos) {
        EntityEvoker evoker = new EntityEvoker(world);
        evoker.enablePersistence();
        evoker.moveToBlockPosAndAngles(pos, 0.0F, 0.0F);
        evoker.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(evoker)), (IEntityLivingData)null);
        world.spawnEntity(evoker);
    }

    private void spawnIllusioner(World world, BlockPos pos) {
        EntityIllusionIllager illusioner = new EntityIllusionIllager(world);
        illusioner.enablePersistence();
        illusioner.moveToBlockPosAndAngles(pos, 0.0F, 0.0F);
        illusioner.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(illusioner)), (IEntityLivingData)null);
        world.spawnEntity(illusioner);
    }

    /**Generates an Illager Archer Tower.
     *@param classes Biome class
     *@param topBlock The Block to spawn on
     */
    private void generateIllagerArcherTower(World world, Random random, int chunkX, int chunkZ, int chance, Block topBlock, Class<?>... classes){
        ArrayList<Class<?>> classesList = new ArrayList<Class<?>>(Arrays.asList(classes));

        int x = chunkX * 16;
        int z = chunkZ * 16;
        int y = calculateGenerationHeight(world, x, z, topBlock);
        BlockPos pos = new BlockPos(x,y,z);

        Class<?> biome = world.provider.getBiomeForCoords(pos).getClass();

        if(world.getWorldType() != WorldType.FLAT){
            if (classesList.contains(biome)){
                if(random.nextInt(chance) == 0){
                    if (compareFloor(world, pos, 14, 14, ILLAGER_ARCHER_TOWER.getStructureRotation(), topBlock)) {
                        ILLAGER_ARCHER_TOWER.generateStructureRotRandom(world, random, pos.up());
                        if (ILLAGER_ARCHER_TOWER.getStructureRotation() == Rotation.NONE) {
                            setLoot(world, random, IllagerPlusLootTable.ILLAGER_ARCHER_TOWER, pos.add(7, 9, 5));
                            spawnArcher(world, pos.add(4.5, 9, 4.5));
                            spawnArcher(world, pos.add(4.5, 9, 8.5));
                            spawnArcher(world, pos.add(8.5, 9, 8.5));
                            spawnArcher(world, pos.add(8.5, 9, 4.5));
                        } else if (ILLAGER_ARCHER_TOWER.getStructureRotation() == Rotation.CLOCKWISE_90) {
                            setLoot(world, random, IllagerPlusLootTable.ILLAGER_ARCHER_TOWER, pos.add(-5, 9, 7));
                            spawnArcher(world, pos.add(-4.5, 9, 4.5));
                            spawnArcher(world, pos.add(-8.5, 9, 4.5));
                            spawnArcher(world, pos.add(-8.5, 9, 8.5));
                            spawnArcher(world, pos.add(-4.5, 9, 8.5));
                        } else if (ILLAGER_ARCHER_TOWER.getStructureRotation() == Rotation.CLOCKWISE_180) {
                            setLoot(world, random, IllagerPlusLootTable.ILLAGER_ARCHER_TOWER, pos.add(-7, 9, -5));
                            spawnArcher(world, pos.add(-4.5, 9, -4.5));
                            spawnArcher(world, pos.add(-4.5, 9, -8.5));
                            spawnArcher(world, pos.add(-8.5, 9, -8.5));
                            spawnArcher(world, pos.add(-8.5, 9, -4.5));
                        } else if (ILLAGER_ARCHER_TOWER.getStructureRotation() == Rotation.COUNTERCLOCKWISE_90) {
                            setLoot(world, random, IllagerPlusLootTable.ILLAGER_ARCHER_TOWER, pos.add(5, 9, -7));
                            spawnArcher(world, pos.add(4.5, 9, -4.5));
                            spawnArcher(world, pos.add(8.5, 9, -4.5));
                            spawnArcher(world, pos.add(8.5, 9, -8.5));
                            spawnArcher(world, pos.add(4.5, 9, -8.5));
                        }
                    }
                }
            }
        }
    }

    private boolean compareFloor(World world, BlockPos pos, int xSize, int zSize, Rotation rotation, Block block) {
        int checkNum = 0;
        if (rotation == Rotation.NONE) {
            for (int checkX = pos.getX(); checkX <= pos.getX() + xSize; checkX++) {
                for (int checkZ = pos.getZ(); checkZ <= pos.getZ() + zSize; checkZ++) {
                    if (world.getBlockState(new BlockPos(checkX, pos.getY(), checkZ)).getBlock() == block || world.getBlockState(new BlockPos(checkX, pos.getY(), checkZ)).getBlock() instanceof BlockBush) {
                        ++checkNum;
                    }
                }
            }
        } else if (rotation == Rotation.CLOCKWISE_90) {
            for (int checkX = pos.getX(); checkX >= pos.getX() - xSize; checkX--) {
                for (int checkZ = pos.getZ(); checkZ <= pos.getZ() + zSize; checkZ++) {
                    if (world.getBlockState(new BlockPos(checkX, pos.getY(), checkZ)).getBlock() == block || world.getBlockState(new BlockPos(checkX, pos.getY(), checkZ)).getBlock() instanceof BlockBush) {
                        ++checkNum;
                    }
                }
            }
        } else if (rotation == Rotation.CLOCKWISE_180) {
            for (int checkX = pos.getX(); checkX >= pos.getX() - xSize; checkX--) {
                for (int checkZ = pos.getZ(); checkZ >= pos.getZ() - zSize; checkZ--) {
                    if (world.getBlockState(new BlockPos(checkX, pos.getY(), checkZ)).getBlock() == block || world.getBlockState(new BlockPos(checkX, pos.getY(), checkZ)).getBlock() instanceof BlockBush) {
                        ++checkNum;
                    }
                }
            }
        } if (rotation == Rotation.COUNTERCLOCKWISE_90) {
            for (int checkX = pos.getX(); checkX <= pos.getX() + xSize; checkX++) {
                for (int checkZ = pos.getZ(); checkZ >= pos.getZ() - zSize; checkZ--) {
                    if (world.getBlockState(new BlockPos(checkX, pos.getY(), checkZ)).getBlock() == block || world.getBlockState(new BlockPos(checkX, pos.getY(), checkZ)).getBlock() instanceof BlockBush) {
                        ++checkNum;
                    }
                }
            }
        }
        //System.out.println(checkNum + ":" + xSize*zSize + " @ " + pos + ":" + rotation);
        return checkNum >= xSize * zSize;
    }

    /**Handles the rotation of the Illager Tower and applies loot and entity appropriately.*/
    private void HandleIllagerTower (World world, Random random, BlockPos pos) {
        //TODO Cleanup code. Method too long
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

        //Rot 90
        if (ILLAGER_TOWER_F1.getStructureRotation() == Rotation.CLOCKWISE_90) {
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(-26, 1, 9));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(-27, 1, 9));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(-26, 2, 9));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(-27, 2, 9));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(-26, 1, 22));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(-27, 1, 22));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(-26, 2, 22));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(-27, 2, 22));

            spawnVindicator(world, pos.add(-7, 1, 13));
            spawnVindicator(world, pos.add(-7, 1, 18));
            spawnFurantur(world, pos.add(-13, 1, 11));
            spawnFurantur(world, pos.add(-13, 1, 20));
            spawnEnchanter(world, pos.add(-22, 1, 15.5));
            spawnArcher(world, pos.add(-22, 1, 14.5));
            spawnArcher(world, pos.add(-22, 1, 16.5));

            if (F2_RAND <= 5) {
                ILLAGER_TOWER_F2_A.generateStructureRot90(world, pos.up(12));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-26, 13, 26));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-26, 13, 25));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-26, 13, 23));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-26, 13, 22));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-26, 14, 26));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-26, 14, 25));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-26, 14, 23));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-26, 14, 22));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-26, 15, 26));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-26, 15, 25));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-26, 15, 23));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-26, 15, 22));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-26, 16, 26));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-26, 16, 25));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-26, 16, 23));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-26, 16, 22));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RESEARCHER, pos.add(-5, 13, 26));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RESEARCHER, pos.add(-7, 13, 20));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RESEARCHER, pos.add(-8, 13, 20));

                spawnHoarder(world, pos.add(-7.5, 13, 23));
                spawnNecromancer(world, pos.add(-23, 13, 24));
                spawnEvoker(world, pos.add(-9, 13, 13));
                spawnArcher(world,pos.add(-9, 18, 17));

                ILLAGER_TOWER_F3_A.generateStructureRot90(world, pos.up(24));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_TREES, pos.add(-16, 25, 23));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-7, 25, 11));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_TNT_TRAP, pos.add(-24, 30, 24));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(-18, 30, 15));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(-19, 30, 15));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(-15, 30, 18));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(-15, 30, 19));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-7, 30, 8));

                spawnVindicator(world, pos.add(-7, 25, 13));
                spawnEnchanter(world, pos.add(-17, 25, 10));
                spawnVindicator(world, pos.add(-14,30,13));
                spawnVindicator(world, pos.add(-13,30,14));

                ILLAGER_TOWER_F4_A.generateStructureRot90(world, pos.up(36));
            } else {
                ILLAGER_TOWER_F2_B.generateStructureRot90(world, pos.up(12));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(-5, 13, 15));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(-5, 13, 16));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(-13, 13, 11));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-13, 13, 12));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-13, 13, 20));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(-13, 13, 19));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-15, 18, 5));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-15, 18, 26));

                spawnVindicator(world, pos.add(-5, 13, 5));
                spawnVindicator(world, pos.add(-5, 13, 26));
                spawnVindicator(world, pos.add(-26, 13, 5));
                spawnVindicator(world, pos.add(-26, 13, 26));
                spawnArcher(world, pos.add(-5, 18, 5));
                spawnArcher(world, pos.add(-5, 18, 26));
                spawnArcher(world, pos.add(-26, 18, 5));
                spawnArcher(world, pos.add(-26, 18, 26));

                if (F3_RAND <= 5) {
                    ILLAGER_TOWER_F3_B.generateStructureRot90(world, pos.up(24));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_FARMS, pos.add(-10, 25, 5));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_FARMS, pos.add(-10, 26, 5));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_FARMS, pos.add(-8, 25, 10));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_FARMS, pos.add(-8, 26, 10));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_MECHANICS, pos.add(-21, 26, 6));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_ARMORY, pos.add(-26, 25, 24));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_ARMORY, pos.add(-26, 25, 23));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_ARMORY, pos.add(-24, 25, 26));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_ARMORY, pos.add(-23, 25, 26));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_BREWERY, pos.add(-5, 25, 22));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_BREWERY, pos.add(-5, 25, 23));

                } else {
                    ILLAGER_TOWER_F3_C.generateStructureRot90(world, pos.up(24));
                }
                ILLAGER_TOWER_F4_B.generateStructureRot90(world, pos.up(36));
            }
            ILLAGER_TOWER_F5.generateStructureRot90(world, pos.up(48));
            setLoot(world, random, LootTableList.CHESTS_SIMPLE_DUNGEON, pos.add(-8, 51, 15));
            setLoot(world, random, LootTableList.CHESTS_SIMPLE_DUNGEON, pos.add(-8, 51, 16));

            spawnIllusioner(world, pos.add(-15.5, 49, 15.5));
            spawnArcher(world, pos.add(-9, 51, 14));
            spawnArcher(world, pos.add(-9, 51, 17));

            ILLAGER_TOWER_F6.generateStructureRot90(world, pos.up(58));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RARE, pos.add(-18, 59, 13));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RARE, pos.add(-19, 59, 13));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RARE, pos.add(-18, 60, 13));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RARE, pos.add(-19, 60, 13));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RARE, pos.add(-21, 59, 15));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RARE, pos.add(-21, 59, 16));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RARE, pos.add(-18, 59, 18));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RARE, pos.add(-19, 59, 18));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RARE, pos.add(-18, 60, 18));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RARE, pos.add(-19, 60, 18));

            spawnIllagerKing(world, pos.add(-14.5, 60, 15.5));
        }

        //Rot 180
        if (ILLAGER_TOWER_F1.getStructureRotation() == Rotation.CLOCKWISE_180) {
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(-9, 1, -26));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(-9, 1, -27));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(-9, 2, -26));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(-9, 2, -27));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(-22, 1, -26));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(-22, 1, -27));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(-22, 2, -26));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(-22, 2, -27));

            spawnVindicator(world, pos.add(-13, 1, -7));
            spawnVindicator(world, pos.add(-18, 1, -7));
            spawnFurantur(world, pos.add(-11, 1, -13));
            spawnFurantur(world, pos.add(-20, 1, -13));
            spawnEnchanter(world, pos.add(-15.5, 1, -22));
            spawnArcher(world, pos.add(-14.5, 1, -22));
            spawnArcher(world, pos.add(-16.5, 1, -22));

            if (F2_RAND <= 5) {
                ILLAGER_TOWER_F2_A.generateStructureRot180(world, pos.up(12));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-26, 13, -26));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-25, 13, -26));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-23, 13, -26));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-22, 13, -26));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-26, 14, -26));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-25, 14, -26));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-23, 14, -26));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-22, 14, -26));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-26, 15, -26));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-25, 15, -26));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-23, 15, -26));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-22, 15, -26));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-26, 16, -26));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-25, 16, -26));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-23, 16, -26));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-22, 16, -26));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RESEARCHER, pos.add(-26, 13, -5));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RESEARCHER, pos.add(-20, 13, -7));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RESEARCHER, pos.add(-20, 13, -8));

                spawnHoarder(world, pos.add(-23, 13, -7.5));
                spawnNecromancer(world, pos.add(-24, 13, -23));
                spawnEvoker(world, pos.add(-13, 13, -9));
                spawnArcher(world,pos.add(-17, 18, -9));

                ILLAGER_TOWER_F3_A.generateStructureRot180(world, pos.up(24));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_TREES, pos.add(-23, 25, -16));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-11, 25, -7));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_TNT_TRAP, pos.add(-24, 30, -24));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(-15, 30, -18));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(-15, 30, -19));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(-18, 30, -15));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(-19, 30, -15));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-8, 30, -7));

                spawnVindicator(world, pos.add(-13, 25, -7));
                spawnEnchanter(world, pos.add(-10, 25, -17));
                spawnVindicator(world, pos.add(-13,30,-14));
                spawnVindicator(world, pos.add(-14,30,-13));

                ILLAGER_TOWER_F4_A.generateStructureRot180(world, pos.up(36));

            } else {
                ILLAGER_TOWER_F2_B.generateStructureRot180(world, pos.up(12));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(-15, 13, -5));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(-16, 13, -5));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(-11, 13, -13));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-12, 13, -13));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-20, 13, -13));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(-19, 13, -13));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-5, 18, -15));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(-26, 18, -15));

                spawnVindicator(world, pos.add(-5, 13, -5));
                spawnVindicator(world, pos.add(-26, 13, -5));
                spawnVindicator(world, pos.add(-5, 13, -26));
                spawnVindicator(world, pos.add(-26, 13, -26));
                spawnArcher(world, pos.add(-5, 18, -5));
                spawnArcher(world, pos.add(-26, 18, -5));
                spawnArcher(world, pos.add(-5, 18, -26));
                spawnArcher(world, pos.add(-26, 18, -26));

                if (F3_RAND <= 5) {
                    ILLAGER_TOWER_F3_B.generateStructureRot180(world, pos.up(24));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_FARMS, pos.add(-5, 25, -10));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_FARMS, pos.add(-5, 26, -10));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_FARMS, pos.add(-10, 25, -8));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_FARMS, pos.add(-10, 26, -8));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_MECHANICS, pos.add(-6, 26, -21));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_ARMORY, pos.add(-24, 25, -26));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_ARMORY, pos.add(-23, 25, -26));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_ARMORY, pos.add(-26, 25, -24));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_ARMORY, pos.add(-26, 25, -23));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_BREWERY, pos.add(-22, 25, -5));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_BREWERY, pos.add(-23, 25, -5));

                } else {
                    ILLAGER_TOWER_F3_C.generateStructureRot180(world, pos.up(24));
                }
                ILLAGER_TOWER_F4_B.generateStructureRot180(world, pos.up(36));
            }
            ILLAGER_TOWER_F5.generateStructureRot180(world, pos.up(48));
            setLoot(world, random, LootTableList.CHESTS_SIMPLE_DUNGEON, pos.add(-15, 51, -8));
            setLoot(world, random, LootTableList.CHESTS_SIMPLE_DUNGEON, pos.add(-16, 51, -8));

            spawnIllusioner(world, pos.add(-15.5, 49, -15.5));
            spawnArcher(world, pos.add(-14, 51, -9));
            spawnArcher(world, pos.add(-17, 51, -9));

            ILLAGER_TOWER_F6.generateStructureRot180(world, pos.up(58));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RARE, pos.add(-13, 59, -18));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RARE, pos.add(-13, 59, -19));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RARE, pos.add(-13, 60, -18));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RARE, pos.add(-13, 60, -19));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RARE, pos.add(-15, 59, -21));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RARE, pos.add(-16, 59, -21));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RARE, pos.add(-18, 59, -18));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RARE, pos.add(-18, 59, -19));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RARE, pos.add(-18, 60, -18));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RARE, pos.add(-18, 60, -19));

            spawnIllagerKing(world, pos.add(-15.5, 60, -14.5));
        }

        //Rot 270
        if (ILLAGER_TOWER_F1.getStructureRotation() == Rotation.COUNTERCLOCKWISE_90) {
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(26, 1, -9));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(27, 1, -9));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(26, 2, -9));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(27, 2, -9));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(26, 1, -22));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(27, 1, -22));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(26, 2, -22));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(27, 2, -22));

            spawnVindicator(world, pos.add(7, 1, -13));
            spawnVindicator(world, pos.add(7, 1, -18));
            spawnFurantur(world, pos.add(13, 1, -11));
            spawnFurantur(world, pos.add(13, 1, -20));
            spawnEnchanter(world, pos.add(22, 1, -15.5));
            spawnArcher(world, pos.add(22, 1, -14.5));
            spawnArcher(world, pos.add(22, 1, -16.5));

            if (F2_RAND <= 5) {
                ILLAGER_TOWER_F2_A.generateStructureRot270(world, pos.up(12));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(26, 13, -26));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(26, 13, -25));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(26, 13, -23));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(26, 13, -22));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(26, 14, -26));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(26, 14, -25));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(26, 14, -23));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(26, 14, -22));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(26, 15, -26));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(26, 15, -25));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(26, 15, -23));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(26, 15, -22));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(26, 16, -26));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(26, 16, -25));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(26, 16, -23));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(26, 16, -22));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RESEARCHER, pos.add(5, 13, -26));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RESEARCHER, pos.add(7, 13, -20));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RESEARCHER, pos.add(8, 13, -20));

                spawnHoarder(world, pos.add(7.5, 13, -23));
                spawnNecromancer(world, pos.add(23, 13, -24));
                spawnEvoker(world, pos.add(9, 13, -13));
                spawnArcher(world,pos.add(9, 18, -17));

                ILLAGER_TOWER_F3_A.generateStructureRot270(world, pos.up(24));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_TREES, pos.add(16, 25, -23));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(7, 25, -11));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_TNT_TRAP, pos.add(24, 30, -24));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(18, 30, -15));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(19, 30, -15));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(15, 30, -18));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(15, 30, -19));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(7, 30, -8));

                spawnVindicator(world, pos.add(7, 25, -13));
                spawnEnchanter(world, pos.add(17, 25, -10));
                spawnVindicator(world, pos.add(14,30,-13));
                spawnVindicator(world, pos.add(13,30,-14));

                ILLAGER_TOWER_F4_A.generateStructureRot270(world, pos.up(36));
            } else {
                ILLAGER_TOWER_F2_B.generateStructureRot270(world, pos.up(12));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(5, 13, -15));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(5, 13, -16));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(13, 13, -11));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(13, 13, -12));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(13, 13, -20));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_COMMON, pos.add(13, 13, -19));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(15, 18, -5));
                setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_UNCOMMON, pos.add(15, 18, -26));

                spawnVindicator(world, pos.add(5, 13, -5));
                spawnVindicator(world, pos.add(5, 13, -26));
                spawnVindicator(world, pos.add(26, 13, -5));
                spawnVindicator(world, pos.add(26, 13, -26));
                spawnArcher(world, pos.add(5, 18, -5));
                spawnArcher(world, pos.add(5, 18, -26));
                spawnArcher(world, pos.add(26, 18, -5));
                spawnArcher(world, pos.add(26, 18, -26));

                if (F3_RAND <= 5) {
                    ILLAGER_TOWER_F3_B.generateStructureRot270(world, pos.up(24));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_FARMS, pos.add(10, 25, -5));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_FARMS, pos.add(10, 26, -5));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_FARMS, pos.add(8, 25, -10));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_FARMS, pos.add(8, 26, -10));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_MECHANICS, pos.add(21, 26, -6));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_ARMORY, pos.add(26, 25, -24));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_ARMORY, pos.add(26, 25, -23));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_ARMORY, pos.add(24, 25, -26));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_ARMORY, pos.add(23, 25, -26));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_BREWERY, pos.add(5, 25, -22));
                    setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_ROOM_BREWERY, pos.add(5, 25, -23));

                } else {
                    ILLAGER_TOWER_F3_C.generateStructureRot270(world, pos.up(24));
                }
                ILLAGER_TOWER_F4_B.generateStructureRot270(world, pos.up(36));
            }
            ILLAGER_TOWER_F5.generateStructureRot270(world, pos.up(48));
            setLoot(world, random, LootTableList.CHESTS_SIMPLE_DUNGEON, pos.add(8, 51, -15));
            setLoot(world, random, LootTableList.CHESTS_SIMPLE_DUNGEON, pos.add(8, 51, -16));

            spawnIllusioner(world, pos.add(15.5, 49, -15.5));
            spawnArcher(world, pos.add(9, 51, -14));
            spawnArcher(world, pos.add(9, 51, -17));

            ILLAGER_TOWER_F6.generateStructureRot270(world, pos.up(58));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RARE, pos.add(18, 59, -13));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RARE, pos.add(19, 59, -13));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RARE, pos.add(18, 60, -13));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RARE, pos.add(19, 60, -13));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RARE, pos.add(21, 59, -15));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RARE, pos.add(21, 59, -16));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RARE, pos.add(18, 59, -18));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RARE, pos.add(19, 59, -18));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RARE, pos.add(18, 60, -18));
            setLoot(world, random, IllagerPlusLootTable.ILLAGER_TOWER_RARE, pos.add(19, 60, -18));

            spawnIllagerKing(world, pos.add(14.5, 60, -15.5));
        }
    }

    /**Generates an Illager Centre.
     *@param classes Biome class
     *@param topBlock The Block to spawn on
     */
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
    }

    /**Generates loot for a specific block.
     *@param lootTable the loot table to use
     *@param pos The position of the Chest block
     */
    private void setLoot (World world, Random random, ResourceLocation lootTable, BlockPos pos) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TileEntityChest) {
            ((TileEntityChest) tileEntity).setLootTable(lootTable, random.nextLong());
        } else {
            System.err.println("Unable to generate loot:[" + lootTable + "] at " + pos);
        }
    }

    /**Finds the topmost block position for the specified block.
     *@param topBlock The Block to locate
     *@return int Returns the y position of the block
     */
    private static int calculateGenerationHeight(World world, int x, int z, Block topBlock){
        int y = world.getHeight();
        boolean foundGround = false;

        while(!foundGround && y-- >= world.getSeaLevel() + 1){
            Block block = world.getBlockState(new BlockPos(x,y,z)).getBlock();
            foundGround = block == topBlock;
        }
        return y;
    }
}
