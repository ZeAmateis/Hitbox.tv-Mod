package fr.zeamateis.hitbox.client.chat;

import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_10;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import fr.zeamateis.hitbox.common.core.HitboxTVCore;
import fr.zeamateis.hitbox.common.utils.Utils;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentTranslation;

public class TchatSenderCore extends WebSocketClient {

	String channel;
	boolean isConnexionClosed;
	ICommandSender sender;

	public TchatSenderCore(String channel, String IP, ICommandSender sender) throws Exception {
		super(new URI("ws://" + IP + "/socket.io/1/websocket/" + Utils.getID(IP)), new Draft_10());
		this.channel = channel;
		this.sender = sender;
		if (HitboxTVCore.getData().isHitboxChannelNameCorrect(channel)) {
			connectBlocking();
			joinChannel(channel.toLowerCase());
		}
	}

	public void onOpen(ServerHandshake handshakedata) {

	}

	public void disconnect() {
		try {
			this.closeBlocking();
			this.close();
			this.setConnexionClosed(true);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static boolean isBuffer(JSONObject obj) {
		if (obj.getJSONObject("params").optBoolean("buffer"))
			return true;
		else
			return false;
	}

	public static String getMethod(JSONObject obj) {
		try {
			String method = obj.getString("method");
			return method;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getDirectMessage(JSONObject obj) {
		String text = "";

		if (obj.getString("method").equals("directMsg")) {
			text = obj.getJSONObject("params").getString("text");
		}

		return text.replace("&amp;", "&");
	}

	public static String getName(JSONObject obj) {
		String name = "";
		name = obj.getJSONObject("params").optString("from");

		return name;
	}

	public void onMessage(String message) {
		if (message.equals("2::")) {
			this.send("2::");
		} else if (message.startsWith("5:::")) {
			JSONObject obj = new JSONObject(new JSONObject(message.substring(message.indexOf("{"))).getJSONArray("args").get(0).toString());

			if (this.getMethod(obj).equals("directMsg")) {
				this.sender.addChatMessage(new ChatComponentTranslation("commands.hitbox.whisper.sendYou", this.getName(obj), this.getDirectMessage(obj)));
			}
		}
	}

	public void onClose(int code, String reason, boolean remote) {
		System.out.println("Closed with code: " + code);
	}

	public void onError(Exception e) {
		e.printStackTrace();
	}

	public void joinChannel(String channel) {
		this.send("5:::{\"name\":\"message\",\"args\":[{\"method\":\"joinChannel\",\"params\":{\"channel\":\"" + channel + "\",\"name\":\"" + HitboxTVCore.getData().getHitboxUsername() + "\",\"token\":\"" + HitboxTVCore.getToken().getHitboxToken() + "\",\"isAdmin\":false,\"notify\":true}}]}");
		System.out.println("Channel Joined.");
	}

	public void sendMessage(String message) {
		this.send("5:::{\"name\":\"message\",\"args\":[{\"method\":\"chatMsg\",\"params\":{\"channel\":\"" + this.channel + "\",\"name\":\"" + HitboxTVCore.getData().getHitboxUsername() + "\",\"nameColor\":\"FF0000\",\"text\":\"" + message + "\"}}]}");
		System.out.println("Message sent.");
	}

	public void sendPrivateMessage(String name, String message) {
		this.send("5:::{\"name\":\"message\",\"args\":[{\"method\":\"directMsg\",\"params\":{\"channel\":\"" + this.channel + "\",\"from\":\"" + HitboxTVCore.getData().getHitboxUsername() + "\",\"to\":\"" + name + "\",\"nameColor\":\"FA5858\",\"text\":\"" + message + "\"}}]}");
	}

	public void banUser(String name) {
		this.send("5:::{\"name\":\"message\",\"args\":[{\"method\":\"banUser\",\"params\":{\"channel\":\"" + this.channel + "\",\"name\":\"" + name + "\"}}]}");
	}

	public void banIPUser(String name) {
		this.send("5:::{\"name\":\"message\",\"args\":[{\"method\":\"banUser\",\"params\":{\"channel\":\"" + this.channel + "\",\"name\":\"" + name + "\"}}]}");
	}

	public void unbanUser(String name) {
		this.send("5:::{\"name\":\"message\",\"args\":[{\"method\":\"banUser\",\"params\":{\"channel\":\"" + this.channel + "\",\"name\":\"" + name + "\",\"token\":\"" + HitboxTVCore.getToken().getHitboxToken() + "\",\"banIP\":true}}]}");
	}

	public void timeoutUser(String name, int time) {
		this.send("5:::{\"name\":\"message\",\"args\":[{\"method\":\"kickUser\",\"params\":{\"channel\":\"" + this.channel + "\",\"name\":\"" + name + "\",\"token\":\"" + HitboxTVCore.getToken().getHitboxToken() + "\",\"timeout\":" + time + "}}]}");
	}

	public void addModerator(String moderatorName) {
		this.send("5:::{\"name\":\"message\",\"args\":[{\"method\":\"makeMod\",\"params\":{\"channel\":\"" + this.channel + "\",\"name\":\"" + moderatorName + "\",\"token\":\"" + HitboxTVCore.getToken().getHitboxToken() + "\"}}]}");
	}

	public void removeModerator(String moderatorName) {
		this.send("5:::{\"name\":\"message\",\"args\":[{\"method\":\"removeMod\",\"params\":{\"channel\":\"" + this.channel + "\",\"name\":\"" + moderatorName + "\",\"token\":\"" + HitboxTVCore.getToken().getHitboxToken() + "\"}}]}");
	}

	public void setSlowMode(int time) {
		this.send("5:::{\"name\":\"message\",\"args\":[{\"method\":\"slowMode\",\"params\":{\"channel\":\"" + this.channel + "\",\"time\":\"" + time + "\"}}]}");
	}

	public void setSubscriberMode(boolean enabled) {
		this.send("5:::{\"name\":\"message\",\"args\":[{\"method\":\"slowMode\",\"params\":{\"channel\":\"" + this.channel + "\",\"subscriber\":" + enabled + ",\"rate\":0}}]}");
	}

	public boolean isConnexionClosed() {
		return isConnexionClosed;
	}

	public void setConnexionClosed(boolean isConnexionClosed) {
		this.isConnexionClosed = isConnexionClosed;
	}

}