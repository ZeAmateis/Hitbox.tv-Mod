package fr.zeamateis.hitbox.common.core;

import fr.zeamateis.hitbox.client.chat.TchatReceiverCore;
import fr.zeamateis.hitbox.client.chat.TchatSenderCore;
import fr.zeamateis.hitbox.common.utils.Utils;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.fml.client.FMLClientHandler;

public class HitboxTVLogin {

	private static String username;
	private static String password;

	private static TchatReceiverCore chatReceiver;
	private static TchatSenderCore chatSender;

	private static boolean isLogged;

	public static void init() {
		try {
			String line;
			if ((line = HitboxTVCore.getData().getHitboxChannel().toLowerCase()) != null && HitboxTVCore.getToken().isLogginCorrect()) {
				setChatReceiver(new TchatReceiverCore(line, Utils.getIP(), FMLClientHandler.instance().getClient().thePlayer));
				setChatSender(new TchatSenderCore(line, Utils.getIP(), FMLClientHandler.instance().getClient().thePlayer));
				setLogged(true);
				FMLClientHandler.instance().getClient().thePlayer.addChatMessage(new ChatComponentTranslation("commands.hitbox.login.success"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String user) {
		username = user;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String pass) {
		password = pass;
	}

	public static TchatReceiverCore getChatReceiver() {
		return chatReceiver;
	}

	public static void setChatReceiver(TchatReceiverCore receiver) {
		chatReceiver = receiver;
	}

	public static TchatSenderCore getChatSender() {
		return chatSender;
	}

	public static void setChatSender(TchatSenderCore sender) {
		chatSender = sender;
	}

	public static boolean isLogged() {
		return isLogged;
	}

	public static void setLogged(boolean logged) {
		isLogged = logged;
	}
}