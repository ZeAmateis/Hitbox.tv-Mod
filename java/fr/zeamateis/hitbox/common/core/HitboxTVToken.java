package fr.zeamateis.hitbox.common.core;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cpw.mods.fml.client.FMLClientHandler;
import fr.zeamateis.hitbox.common.utils.Utils;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class HitboxTVToken {
	private static String hitboxToken, tokenSave;
	private static boolean isLogginCorrect;

	public String generateToken() {
		HttpURLConnection connection = null;
		StringBuffer response = new StringBuffer();
		HitboxTVData config = new HitboxTVData();

		if (config.isTokenLoginEnabled() == true) {
			try {
				URL url = new URL(Utils.hitboxAPI + "auth/token");
				connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("POST");

				String urlParameters = "login=" + config.getHitboxUsername() + "&pass=" + config.getHitboxPassword() + "&app=desktop";

				// System.out.println(urlParameters + "\n"); // DEBUG LINE -
				// SHOW THE PASSWORD GIVEN BY THE USER ON THE CONSOLE

				connection.setDoOutput(true);
				DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
				wr.writeBytes(urlParameters);
				wr.flush();
				wr.close();

				BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				while ((line = rd.readLine()) != null) {
					response.append(line);
				}
				rd.close();
				String authToken = response.toString();

				int token1 = authToken.indexOf("authToken\":\"");
				int startToken = token1 + 12;

				int endToken = authToken.indexOf("\"", startToken);

				tokenSave = authToken.substring(startToken, endToken);
				this.setLogginCorrect(true);
			} catch (IOException e) {
				// If credentials are wrong
				this.setLogginCorrect(false);
				System.out.println("Wrong credentials. Try again");
				FMLClientHandler.instance().getClient().thePlayer.addChatMessage(new ChatComponentText("(" + EnumChatFormatting.GREEN + "HitboxTV" + EnumChatFormatting.RESET + ") " + I18n.format("commands.hitbox.login.failed.credentials")));

				// System.exit(0);
			} catch (Exception e) {

				e.printStackTrace();

			} finally {

				if (connection != null) {
					connection.disconnect();
				}
			}
			// System.out.println(tokenSave);
			return tokenSave;
		}

		// System.out.println("token desactivated"); // DEBUG LINE
		return tokenSave;
	}

	public static String getHitboxToken() {
		return tokenSave;
	}

	public static boolean isLogginCorrect() {
		return isLogginCorrect;
	}

	public static void setLogginCorrect(boolean isLogginCorrect) {
		HitboxTVToken.isLogginCorrect = isLogginCorrect;
	}
}
