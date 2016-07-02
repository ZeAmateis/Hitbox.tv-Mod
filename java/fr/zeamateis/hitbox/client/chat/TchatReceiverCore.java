package fr.zeamateis.hitbox.client.chat;

import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_10;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import fr.zeamateis.hitbox.common.configuration.ConfigurationManager;
import fr.zeamateis.hitbox.common.core.HitboxTVCore;
import fr.zeamateis.hitbox.common.utils.Utils;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.TextComponentTranslation;

public class TchatReceiverCore extends WebSocketClient {
	public String channel;
	boolean isConnexionClosed;
	ICommandSender sender;

	DateFormat df = new SimpleDateFormat("MM-dd-yy_HH-mm");
	Date today = Calendar.getInstance().getTime();
	String date = df.format(today);

	public TchatReceiverCore(String channel, String IP, ICommandSender sender) throws Exception {
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

	public void onMessage(String message) {
		if (message.equals("2::")) {
			this.send("2::");
		} else if (message.startsWith("5:::")) {
			JSONObject obj = new JSONObject(new JSONObject(message.substring(message.indexOf("{"))).getJSONArray("args").get(0).toString());

			if (!this.isBuffer(obj)) {
				if (this.getMethod(obj).equals("chatMsg")) {
					this.sender.addChatMessage(new TextComponentTranslation("commands.hitbox.msg", this.getName(obj), this.getMessage(obj)));

					if (ConfigurationManager.chatHistoric == true) {
						try {
							File file = new File(HitboxTVCore.chatLogsDir + File.separator + HitboxTVCore.getData().getHitboxChannelName() + "-" + date + ".htclog");
							if (!file.exists()) {
								file.createNewFile();
							}
							Files.write(file.toPath(), (this.getName(obj) + " : " + this.getMessage(obj) + "\n").getBytes(), StandardOpenOption.APPEND);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
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
		this.send("5:::{\"name\":\"message\",\"args\":[{\"method\":\"joinChannel\",\"params\":{\"channel\":\"" + channel + "\",\"name\":\"name\",\"token\":\"pass\",\"isAdmin\":false}}]}");
		System.out.println("Channel Joined.");
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

	public static boolean isBuffer(JSONObject obj) {
		if (obj.getJSONObject("params").optBoolean("buffer"))
			return true;
		else
			return false;
	}

	public static String getName(JSONObject obj) {
		String name = "";
		name = obj.getJSONObject("params").optString("name");

		return name;
	}

	public static String getMessage(JSONObject obj) {
		String text = "";

		if (obj.getString("method").equals("chatMsg")) {
			text = obj.getJSONObject("params").getString("text");
		}

		if (text.contains("<img src=\"")) {
			text = text.replace(text.substring(0, text.length()), "<Unsupported Emoji>");
		} else if (text.contains("&amp;")) {
			text = text.replace("&amp;", "&");
		}

		return text;
	}

	public boolean isConnexionClosed() {
		return isConnexionClosed;
	}

	public void setConnexionClosed(boolean isConnexionClosed) {
		this.isConnexionClosed = isConnexionClosed;
	}
}
