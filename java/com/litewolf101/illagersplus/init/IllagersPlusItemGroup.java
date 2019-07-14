package com.litewolf101.illagersplus.init;

import com.litewolf101.illagersplus.IllagersPlus;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.litewolf101.illagersplus.init.EntityInit.FURANTUR;

public class IllagersPlusItemGroup extends ItemGroup {
    public IllagersPlusItemGroup() {
        super("illagersPlus");
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(SpawnEggItem.getEgg(FURANTUR));
    }

    @Override
    public boolean hasSearchBar() {
        return true;
    }

    @Override
    public int getSearchbarWidth() {
        return 89;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public ResourceLocation getBackgroundImage() {
        return new ResourceLocation(IllagersPlus.MOD_ID, "textures/gui/tab_items.png");
    }

    @Override
    public ResourceLocation getTabsImage() {
        return new ResourceLocation(IllagersPlus.MOD_ID, "textures/gui/tab_icons.png");
    }
}
