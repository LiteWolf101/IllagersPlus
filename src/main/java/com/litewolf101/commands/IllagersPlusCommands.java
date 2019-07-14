package com.litewolf101.commands;

import com.google.common.collect.Maps;
import com.litewolf101.IllagersPlus;
import com.litewolf101.utils.StructureHandler;
import com.litewolf101.world.WorldGenCustomStructures;
import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.WorldServer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.litewolf101.world.WorldGenCustomStructures.*;

public class IllagersPlusCommands extends CommandBase {
    private final String name = "illagersplus";
    private final List<String> commands = new ArrayList<String>();
    public static final List<String> structureNames = new ArrayList<String>();
    private final Map<String, StructureHandler> returnStructure = Maps.<String, StructureHandler>newHashMap();

    public IllagersPlusCommands() {
        commands.add("spawnStructure");
        commands.add("data");
        commands.add("help");

        structureNames.add(ILLAGER_TOWER_F1.structureName);
        addToMap(ILLAGER_TOWER_F1);
        structureNames.add(ILLAGER_TOWER_F2_A.structureName);
        addToMap(ILLAGER_TOWER_F2_A);
        structureNames.add(ILLAGER_TOWER_F2_B.structureName);
        addToMap(ILLAGER_TOWER_F2_B);
        structureNames.add(ILLAGER_TOWER_F3_A.structureName);
        addToMap(ILLAGER_TOWER_F3_A);
        structureNames.add(ILLAGER_TOWER_F3_B.structureName);
        addToMap(ILLAGER_TOWER_F3_B);
        structureNames.add(ILLAGER_TOWER_F3_C.structureName);
        addToMap(ILLAGER_TOWER_F3_C);
        structureNames.add(ILLAGER_TOWER_F4_A.structureName);
        addToMap(ILLAGER_TOWER_F4_A);
        structureNames.add(ILLAGER_TOWER_F4_B.structureName);
        addToMap(ILLAGER_TOWER_F4_B);
        structureNames.add(ILLAGER_TOWER_F5.structureName);
        addToMap(ILLAGER_TOWER_F5);
        structureNames.add(ILLAGER_TOWER_F6.structureName);
        addToMap(ILLAGER_TOWER_F6);
        structureNames.add(ILLAGER_ARCHER_TOWER.structureName);
        addToMap(ILLAGER_ARCHER_TOWER);
        structureNames.add(ILLAGER_CENTRE.structureName);
        addToMap(ILLAGER_CENTRE);
        structureNames.add(ILLAGER_FARM.structureName);
        addToMap(ILLAGER_FARM);
        structureNames.add(ILLAGER_WELL.structureName);
        addToMap(ILLAGER_WELL);
        structureNames.add(ILLAGER_DUMMY_1.structureName);
        addToMap(ILLAGER_DUMMY_1);
        structureNames.add(ILLAGER_DUMMY_2.structureName);
        addToMap(ILLAGER_DUMMY_2);
        structureNames.add(ANIMAL_PEN.structureName);
        addToMap(ANIMAL_PEN);
        structureNames.add(UNDYING_TOTEM.structureName);
        addToMap(UNDYING_TOTEM);
        structureNames.add(FIREWORKS_DISPLAY.structureName);
        addToMap(FIREWORKS_DISPLAY);
        structureNames.add(FLOWER_BED.structureName);
        addToMap(FLOWER_BED);
        structureNames.add(ILLAGER_STORAGE.structureName);
        addToMap(ILLAGER_STORAGE);
        structureNames.add(LOG_PILE.structureName);
        addToMap(LOG_PILE);
    }

    public void addToMap(StructureHandler handler) {
        if (handler != null) {
            this.returnStructure.put(handler.structureName, handler);
        }
    }

    public StructureHandler getStructureFromName(String name) {
        for (Map.Entry<String, StructureHandler> entry : this.returnStructure.entrySet()) {
            if (name.equals(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUsage(ICommandSender iCommandSender) {
        return "Incorrect, incomplete or unknown command. Use " + TextFormatting.GREEN + "/illagersplus help " + TextFormatting.RED + "for additional info.";
    }

    @Override
    public void execute(MinecraftServer minecraftServer, ICommandSender sender, String[] strings) throws CommandException {
        if (!(sender instanceof EntityPlayerMP)) {
            throw new WrongUsageException("message.command.onlyInGame");
        }
        EntityPlayerMP player = (EntityPlayerMP) sender;
        WorldServer world = player.getServerWorld();

        if (strings.length == 0) {
            throw new WrongUsageException(getUsage(sender));
        } else if (strings[0].equals("help")) {
            sender.sendMessage(new TextComponentString(
                    TextFormatting.BLUE + "-----Illagers+ Info Page(1)-----" + "\n" +
                    TextFormatting.GRAY + "/illagersplus help" + TextFormatting.RESET + "\n"
                    + TextFormatting.RED + "Displays this screen." + TextFormatting.RESET + "\n" + "\n"

                    + TextFormatting.GRAY + "/illagersplus spawnStructure <x> <y> <z> <illagersplus_structure_name> <rotateNone|rotate90|rotate180|rotate270>" + TextFormatting.RESET + "\n"
                    + TextFormatting.RED + "Spawns an Illagers+ structure at the given location." + TextFormatting.RESET + "\n" + "\n"

                    + TextFormatting.GRAY + "/illagersplus data" + TextFormatting.RESET + "\n"
                    + TextFormatting.RED + "Displays the mod name, current version of mod and current version of minecraft." + TextFormatting.RESET + "\n"));

        } else if (strings[0].equals("data")) {
            sender.sendMessage(new TextComponentString(TextFormatting.GRAY + "\u2605" + "MOD: " + TextFormatting.RESET + IllagersPlus.MOD_NAME + "\n"
                    + TextFormatting.GRAY + "\u2605" + "VERSION: " + TextFormatting.RESET + IllagersPlus.VERSION + "\n"
                    + TextFormatting.GRAY + "\u2605" + "MINECRAFT VERSION: " + TextFormatting.RESET + IllagersPlus.ACCEPTED_MINECRAFT_VERSIONS));
        } else if (strings[0].equals("spawnStructure")) {
            //int F2_RAND = ((EntityPlayerMP) sender).world.rand.nextInt(10);
            //int F3_RAND = ((EntityPlayerMP) sender).world.rand.nextInt(10);
            if (strings.length < 6) {
                throw new WrongUsageException("/illagersplus spawnStructure <x> <y> <z> <illagersplus_structure_name> <rotateNone|rotate90|rotate180|rotate270>");
            } else {
                BlockPos blockpos = parseBlockPos(sender, strings, 1, false);

                if (structureNames.contains(strings[4])) {
                    if ("rotateNone".equals(strings[5])) {
                        getStructureFromName(strings[4]).generateStructureRotNone(world, blockpos);
                    } else if ("rotate90".equals(strings[5])) {
                        getStructureFromName(strings[4]).generateStructureRot90(world, blockpos);
                    } else if ("rotate180".equals(strings[5])) {
                        getStructureFromName(strings[4]).generateStructureRot180(world, blockpos);
                    } else if ("rotate270".equals(strings[5])) {
                        getStructureFromName(strings[4]).generateStructureRot270(world, blockpos);
                    }
                } else throw new WrongUsageException("\"" + strings[4] + "\" is not a structure of Illagers+");
            }
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args[0].equals("spawnStructure")) {
            if (args.length > 1 && args.length <= 4) {
                return getTabCompletionCoordinate(args, 0, targetPos);
            }else if (args.length == 5) {
                return structureNames;
            } else
            {
                return args.length == 6 ? getListOfStringsMatchingLastWord(args, new String[] {"rotateNone", "rotate90", "rotate180", "rotate270"}) : Collections.emptyList();
            }
        } else {
            return args.length == 1 ? commands : Collections.<String>emptyList();
        }
    }
}
