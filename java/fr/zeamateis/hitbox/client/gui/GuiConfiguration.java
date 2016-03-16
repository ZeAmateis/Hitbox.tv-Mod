package fr.zeamateis.hitbox.client.gui;

import cpw.mods.fml.client.config.GuiConfig;
import fr.zeamateis.hitbox.common.core.HitboxTVCore;
import fr.zeamateis.hitbox.common.utils.Utils;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

public class GuiConfiguration extends GuiConfig {

	public GuiConfiguration(GuiScreen parent) {
		super(parent, new ConfigElement(HitboxTVCore.hitboxConfiguration.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), Utils.MODID, false, false, I18n.format("config.hitbox"));
	}
}