package fr.zeamateis.hitbox.common.configuration;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.Configuration;

public class ConfigurationManager {

	public static boolean chatHistoric;

	private static void loadConfig(Configuration hitboxConfiguration) {
		chatHistoric = hitboxConfiguration.getBoolean("chatHistoric", Configuration.CATEGORY_GENERAL, false, I18n.format("config.hitbox.chatHistoric"));
	}

	public static void sync(Configuration hitboxConfiguration) {
		loadConfig(hitboxConfiguration);

		if (hitboxConfiguration.hasChanged())
			hitboxConfiguration.save();
	}
}