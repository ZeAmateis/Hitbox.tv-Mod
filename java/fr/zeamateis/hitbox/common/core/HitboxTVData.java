package fr.zeamateis.hitbox.common.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import fr.zeamateis.hitbox.common.utils.Utils;

public class HitboxTVData {
	static URL totalViewsUrl, statusUrl, stramingTitle, stramingKey, followers;
	static String hitboxUsername, hitboxPassword, hitboxChannel;
	static Object obj;

	static boolean isTokenEnabled;

	static int totalFollowers = 0, lastCheck = 0;
	static String lastFollower = "";

	public HitboxTVData tokenLogin(boolean enabled) {
		isTokenEnabled = enabled;
		return this;
	}

	public boolean isTokenLoginEnabled() {
		return isTokenEnabled;
	}

	public boolean isHitboxChannelNameCorrect(String channelName) {
		try {
			URL channelURL = new URL(Utils.hitboxAPI + "media/status/" + channelName);

			obj = Utils.getParser().parse(Utils.getUrls(channelURL).readLine());

			if (obj.toString().contains("[]")) {
				return false;
			}

		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public String getHitboxStreamKey() {
		if (this.isTokenLoginEnabled()) {
			try {
				stramingKey = new URL(Utils.hitboxAPI + "mediakey/" + hitboxUsername + "?authToken=" + HitboxTVToken.getHitboxToken());

				JsonReader reader = new JsonReader(new InputStreamReader(stramingKey.openStream()));
				JsonParser parser = new JsonParser();

				obj = parser.parse(reader);

				JsonObject jsonObject = (JsonObject) obj;

				JsonElement streaming_key = jsonObject.get("streamKey");
				reader.setLenient(true);

				reader.close();

				return streaming_key.getAsString();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public String setHitboxPassword(String password) {
		return hitboxPassword = password;
	}

	public String getHitboxPassword() {
		return hitboxPassword;
	}

	public String setHitboxUsername(String username) {
		return hitboxUsername = username;
	}

	public String getHitboxUsername() {
		return hitboxUsername;
	}

	public static String getHitboxChannel() {
		return hitboxChannel;
	}

	public static void setHitboxChannel(String hitboxChan) {
		hitboxChannel = hitboxChan;
	}

	public String getHitboxStreamingTitle() {
		if (getIsStreaming() == true) {
			try {
				stramingTitle = new URL(Utils.hitboxAPI + "media/live/" + getHitboxChannelName());

				JsonReader reader = new JsonReader(new InputStreamReader(stramingTitle.openStream()));
				JsonParser parser = new JsonParser();

				obj = parser.parse(reader);

				JsonObject jsonObject = (JsonObject) obj;
				reader.setLenient(true);

				JsonArray media_title = jsonObject.get("livestream").getAsJsonArray();

				return media_title.get(0).getAsJsonObject().get("media_status").getAsString();
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		return "not streaming";
	}

	public String getHitboxStreamingGame() {
		if (getIsStreaming() == true) {
			try {
				stramingTitle = new URL(Utils.hitboxAPI + "media/live/" + getHitboxChannelName());

				obj = Utils.getParser().parse(Utils.getUrls(stramingTitle).readLine());
				JsonObject jsonObject = (JsonObject) obj;

				JsonArray media_status = jsonObject.get("livestream").getAsJsonArray();

				return media_status.get(0).getAsJsonObject().get("category_name").getAsString();
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		return "not streaming";
	}

	public String getHitboxChannelName() {
		try {
			stramingTitle = new URL(Utils.hitboxAPI + "media/live/" + getHitboxChannel());

			obj = Utils.getParser().parse(Utils.getUrls(stramingTitle).readLine());
			JsonObject jsonObject = (JsonObject) obj;

			JsonArray media_status = jsonObject.get("livestream").getAsJsonArray();

			return media_status.get(0).getAsJsonObject().get("media_user_name").getAsString();
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "null";
	}

	public String getHitboxSreamingViews() {

		if (getIsStreaming() == true) {
			try {
				statusUrl = new URL(Utils.hitboxAPI + "media/status/" + getHitboxChannelName());

				obj = Utils.getParser().parse(Utils.getUrls(statusUrl).readLine());
				JsonObject jsonObject = (JsonObject) obj;

				JsonElement viewsCount = jsonObject.get("media_views");

				return viewsCount.toString();
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		return "not streaming";
	}

	public String getHitboxFollowers() {
		try {
			followers = new URL(Utils.hitboxAPI + "media/stream/" + getHitboxChannelName().toLowerCase());

			obj = Utils.getParser().parse(Utils.getUrls(followers).readLine());
			JsonObject jsonObject = (JsonObject) obj;

			JsonObject followersObj = jsonObject.get("livestream").getAsJsonArray().get(0).getAsJsonObject().get("channel").getAsJsonObject();

			return followersObj.get("followers").getAsString();
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public String getHitboxTotalViews() {
		try {
			totalViewsUrl = new URL(Utils.hitboxAPI + "media/views/" + getHitboxChannelName().toLowerCase());

			obj = Utils.getParser().parse(Utils.getUrls(totalViewsUrl).readLine());
			JsonObject jsonObject = (JsonObject) obj;

			JsonElement totalViews = jsonObject.get("total_live_views");

			return totalViews.toString();
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public boolean getIsStreaming() {
		try {
			statusUrl = new URL(Utils.hitboxAPI + "media/status/" + getHitboxChannelName());

			obj = Utils.getParser().parse(Utils.getUrls(statusUrl).readLine());
			JsonObject jsonObject = (JsonObject) obj;

			JsonElement isStreaming = jsonObject.get("media_is_live");

			if (isStreaming.getAsString().equals("1"))
				return true;
			else
				return false;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public String getFollowersHistory() {

		HitboxTVData config = new HitboxTVData();
		HitboxTVToken token = new HitboxTVToken();

		if (config.isTokenLoginEnabled() == true) {
			try {
				followers = new URL(Utils.hitboxAPI + "followerstats/" + config.getHitboxUsername() + "?authToken=" + token.getHitboxToken());

				obj = Utils.getParser().parse(new BufferedReader(new InputStreamReader(followers.openStream(), "UTF-8")).readLine());

				JsonObject jsonObject = (JsonObject) obj;

				JsonElement followersHistory = jsonObject.get("followers");

				return followersHistory.toString();
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return "desactivated, verify if token is enabled";
	}
}