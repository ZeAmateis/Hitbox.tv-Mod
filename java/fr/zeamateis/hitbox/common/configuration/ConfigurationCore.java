package fr.zeamateis.hitbox.common.configuration;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ConfigurationCore {

	public static void preInitConfig(FMLPreInitializationEvent event) {
		Configuration cfg = new Configuration(new File(event.getModConfigurationDirectory() + "/hitboxtv/config.cfg"));
		try {
			cfg.load();
			ConfigurationManager.load(cfg);
		} catch (Exception ex) {
			event.getModLog().fatal("Failed to load configuration");
		} finally {
			if (cfg.hasChanged()) {
				cfg.save();
			}
		}
	}

	public void initConfig(FMLInitializationEvent event) {

	}
}
