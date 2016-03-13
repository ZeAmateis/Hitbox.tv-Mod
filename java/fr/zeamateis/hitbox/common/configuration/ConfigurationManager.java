package fr.zeamateis.hitbox.common.configuration;

import net.minecraftforge.common.config.Configuration;

public class ConfigurationManager {

	public static boolean chatHistoric;

	public static void load(Configuration config) {
		chatHistoric = config.getBoolean("chatHistoric", "Chat", false, "Set true to activate an historic of the chat");
	}
}