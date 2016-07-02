package fr.zeamateis.hitbox.common.core;

import java.io.File;

import fr.zeamateis.hitbox.common.command.CommandChatHitbox;
import fr.zeamateis.hitbox.common.configuration.ConfigurationManager;
import fr.zeamateis.hitbox.common.utils.Utils;
import fr.zeamateis.hitbox.proxy.CommonProxy;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = Utils.MODID, version = Utils.VERSION, name = Utils.NAME, guiFactory = Utils.GUICONFIG)
public class HitboxTVCore {
	@SidedProxy(clientSide = "fr.zeamateis.hitbox.proxy.ClientProxy", serverSide = "fr.zeamateis.hitbox.proxy.CommonProxy")
	public static CommonProxy proxy;

	@Instance(Utils.MODID)
	private static HitboxTVCore instance;

	public static File chatLogsDir;

	public HitboxTVCore getInstance() {
		return instance;
	}

	private static HitboxTVData data = new HitboxTVData();
	private static HitboxTVToken token = new HitboxTVToken();

	public static Configuration hitboxConfiguration;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		if (event.getSide().isClient()) {
			try {
				chatLogsDir = new File("./config/hitboxtv/chat-logs");
				chatLogsDir.mkdir();
			} catch (Exception e) {
				System.err.println("Error while creating the chat-logs dir for Hitbox.TV Mod");
				e.printStackTrace();
			}
			hitboxConfiguration = new Configuration(new File(event.getModConfigurationDirectory() + "/hitboxtv/config.cfg"));
			ConfigurationManager.sync(hitboxConfiguration);
		}
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init();
		ClientCommandHandler.instance.registerCommand(new CommandChatHitbox());
		this.registerEvent(this);
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
		if (eventArgs.getModID().equals(Utils.MODID))
			ConfigurationManager.sync(hitboxConfiguration);
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandChatHitbox());
	}

	private void registerEvent(Object event) {
		FMLCommonHandler.instance().bus().register(event);
		MinecraftForge.EVENT_BUS.register(event);
	}

	public static HitboxTVData getData() {
		return data;
	}

	public static HitboxTVToken getToken() {
		return token;
	}
}