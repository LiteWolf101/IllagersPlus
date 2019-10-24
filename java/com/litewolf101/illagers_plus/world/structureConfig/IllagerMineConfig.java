package com.litewolf101.illagers_plus.world.structureConfig;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class IllagerMineConfig implements IFeatureConfig {
    public final double probability;

    public IllagerMineConfig(double probability) {
        this.probability = probability;
    }

    public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
        return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(ops.createString("probability"), ops.createDouble(this.probability))));
    }

    public static <T> IllagerMineConfig deserialize(Dynamic<T> p_214642_0_) {
        float f = p_214642_0_.get("probability").asFloat(0.0F);
        return new IllagerMineConfig((double)f);
    }
}
