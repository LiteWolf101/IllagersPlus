package com.litewolf101.illagers_plus.world.test;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.PillagerOutpostConfig;

public class MockPillagerOutpostConfig implements IFeatureConfig {
    public final double probability;

    public MockPillagerOutpostConfig(double probability) {
        this.probability = probability;
    }

    public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
        return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(ops.createString("probability"), ops.createDouble(this.probability))));
    }

    public static <T> MockPillagerOutpostConfig deserialize(Dynamic<T> p_214642_0_) {
        float f = p_214642_0_.get("probability").asFloat(0.0F);
        return new MockPillagerOutpostConfig((double)f);
    }
}
