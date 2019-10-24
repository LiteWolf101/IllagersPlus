package com.litewolf101.illagers_plus.world;

import com.litewolf101.illagers_plus.world.pieces.IllagerArcherTowerPieces;
import com.litewolf101.illagers_plus.world.pieces.IllagerMinePieces;
import com.litewolf101.illagers_plus.world.structureConfig.IllagerMineConfig;
import com.litewolf101.illagers_plus.world.structures.IllagerArcherTowerStructure;
import com.litewolf101.illagers_plus.world.structures.IllagerMineStructure;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.*;

import java.util.Locale;

import static com.litewolf101.illagers_plus.IllagersPlus.MOD_ID;

public class StructureRegistry {


    //Just some tests I ran
    /*public static final Structure<MockPillagerOutpostConfig> MOCK_PILLAGER_OUTPOST = registerStructureFeature(MOD_ID + ":mock_pillager_outpost", new MockPillagerOutpostStructure(MockPillagerOutpostConfig::deserialize));
    public static final IStructurePieceType PCP = registerPiece(MockPillagerOutpostPieces.MockPillageOutpost::new, "IPPCP");
    public static final Structure<?> MOCK_PILLAGER_OUTPOST_STRUC = registerStructure("Mock_Pillager_Outpost", StructureRegistry.MOCK_PILLAGER_OUTPOST);

    public static final Structure<NoFeatureConfig> MOCK_WOODLAND_MANSION = registerStructureFeature(MOD_ID + ":mock_woodland_mansion", new MockWoodlandMansionStructure(NoFeatureConfig::deserialize));
    public static final IStructurePieceType WMP = registerPiece(MockWoodlandMansionPieces.MansionTemplate::new, "IPWMP");
    public static final Structure<?> MOCK_WOODLAND_MANSION_STRUC = registerStructure("Mock_Mansion", StructureRegistry.MOCK_WOODLAND_MANSION);*/

    public static final Structure<NoFeatureConfig> ILLAGER_ARCHER_TOWER = registerStructureFeature(MOD_ID + ":illager_archer_tower", new IllagerArcherTowerStructure(NoFeatureConfig::deserialize));
    public static final IStructurePieceType IPIAT = registerPiece(IllagerArcherTowerPieces.IllagerArcherTower::new, "IPIAT");
    public static final Structure<?> ILLAGER_ARCHER_TOWER_STRUC = registerStructure("Illager_Archer_Tower", StructureRegistry.ILLAGER_ARCHER_TOWER);

    public static final Structure<IllagerMineConfig> ILLAGER_MINE = registerStructureFeature(MOD_ID + ":illager_mine", new IllagerMineStructure(IllagerMineConfig::deserialize));
    public static final IStructurePieceType IPIM = registerPiece(IllagerMinePieces.MinePiece::new, "IPIM");
    public static final Structure<?> ILLAGER_MINE_STRUC = registerStructure("Illager_Mine", ILLAGER_MINE);


    private static <C extends IFeatureConfig, F extends Feature<C>> F registerStructureFeature(String key, F value) {
        System.out.println("Registering Custom Structure: " + key);
        return (F)(Registry.<Feature<?>>register(Registry.FEATURE, key, value));
    }

    static IStructurePieceType registerPiece(IStructurePieceType p_214750_0_, String key) {
        return Registry.register(Registry.STRUCTURE_PIECE, key.toLowerCase(Locale.ROOT), p_214750_0_);
    }

    private static Structure<?> registerStructure(String key, Structure<?> p_215141_1_) {
        if (true) return p_215141_1_; // FORGE: Registry replaced with slave map
        return Registry.register(Registry.STRUCTURE_FEATURE, key.toLowerCase(Locale.ROOT), p_215141_1_);
    }
}
