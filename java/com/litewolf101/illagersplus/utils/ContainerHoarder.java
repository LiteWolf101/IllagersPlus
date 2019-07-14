package com.litewolf101.illagersplus.utils;

import net.minecraft.inventory.Inventory;

public class ContainerHoarder extends Inventory {

    public ContainerHoarder(int p_i50397_1_) {
        super(p_i50397_1_);
    }

    /*public ContainerHoarder(String title, boolean customName, int slotCount) {
        super(title, customName, slotCount);
    }*/

    @Override
    public int getSizeInventory() {
        return 10;
    }
}
