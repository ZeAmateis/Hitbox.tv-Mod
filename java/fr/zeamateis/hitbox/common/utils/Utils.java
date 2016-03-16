package fr.zeamateis.hitbox.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import org.json.JSONArray;

import com.google.gson.JsonParser;

public class Utils {
	private static JsonParser parser = new JsonParser();

	public static String hitboxAPI = "http://api.hitbox.tv/";
	public static String hitboxImgAPI = "http://edge.sf.hitbox.tv/";

	public static final String MODID = "hitboxmod", NAME = "Hitbox.tv Mod", VERSION = "0.0.2", GUICONFIG = "fr.zeamateis.hitbox.client.gui.GuiFactory";

	public static BufferedReader getUrls(URL urlStream) {
		try {
			return new BufferedReader(new InputStreamReader(urlStream.openStream(), "UTF-8"));
		}

		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static JsonParser getParser() {
		return parser;
	}

	public static void setParser(JsonParser parser) {
		Utils.parser = parser;
	}

	public static String readUrl(String urlString) {
		BufferedReader reader = null;
		try {
			URL url = new URL(urlString);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuffer buffer = new StringBuffer();
			int read;
			char[] chars = new char[1024];
			while ((read = reader.read(chars)) != -1) {
				buffer.append(chars, 0, read);
			}
			reader.close();
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getIP() {
		JSONArray arr = new JSONArray(readUrl("http://hitbox.tv/api/chat/servers.json?redis=true"));
		return arr.getJSONObject(0).getString("server_ip");
	}

	public static String getID(String IP) {
		String connectionID = readUrl("http://" + IP + "/socket.io/1/");
		String ID = connectionID.substring(0, connectionID.indexOf(":"));
		System.out.println(ID);
		return ID;
	}
}