package com.litewolf101.utils;

import net.minecraft.inventory.InventoryBasic;

public class ContainerHoarder extends InventoryBasic {

    public ContainerHoarder(String title, boolean customName, int slotCount) {
        super(title, customName, slotCount);
    }

    @Override
    public int getSizeInventory() {
        return 10;
    }
}
