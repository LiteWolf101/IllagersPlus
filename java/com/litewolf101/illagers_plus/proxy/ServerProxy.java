package com.litewolf101.illagers_plus.proxy;

import net.minecraft.world.World;

public class ServerProxy implements IProxy {
    @Override
    public World getClientWorld() {
        throw new IllegalStateException("Only run this code on the Client!");
    }
}
