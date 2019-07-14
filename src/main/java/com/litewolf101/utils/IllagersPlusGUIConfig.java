package com.litewolf101.utils;

import com.litewolf101.IllagersPlus;
import com.litewolf101.proxy.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.DefaultGuiFactory;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.DummyConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.litewolf101.utils.IllagersPlusConfig.CATEGORY_GENERAL;

public class IllagersPlusGUIConfig extends DefaultGuiFactory {

    public IllagersPlusGUIConfig() {
        super(IllagersPlus.MOD_ID, IllagersPlus.MOD_NAME);
    }

    @Override
    public GuiScreen createConfigGui(GuiScreen parentScreen)
    {
        return new GuiConfig(parentScreen, getConfigElements(), modid, true, true, title);
    }

    @SuppressWarnings("deprecation")
    private static List<IConfigElement> getConfigElements()
    {
        List<IConfigElement> list = new ArrayList<IConfigElement>();

        List<IConfigElement> general = new ConfigElement(IllagersPlusConfig.config.getCategory(CATEGORY_GENERAL.toLowerCase())).getChildElements();

        list.add(new DummyConfigElement.DummyCategoryElement(I18n.translateToLocal("gui.config.category.general.title"), "gui.config.category.general.name", general));


        return list;
    }
}