package fr.zeamateis.hitbox.client.gui;

import java.io.IOException;
import java.net.URL;

import fr.zeamateis.hitbox.common.core.HitboxTVCore;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiHitboxTV extends GuiScreen {

	String streamingViewers, channelTotalViews, gamePlayed, followers;
	boolean isStreaming;
	URL backgroundURL;// GuiIngameMenu

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	public void initGui() {
		this.buttonList.clear();
		this.isStreaming = HitboxTVCore.getData().getIsStreaming();
		streamingViewers = I18n.format("live.actualViewers") + " : " + HitboxTVCore.getData().getHitboxSreamingViews().replace("\"", "");
		channelTotalViews = I18n.format("channel.totalViews") + " : " + HitboxTVCore.getData().getHitboxTotalViews().replace("\"", "");
		followers = I18n.format("channel.followers") + " : " + HitboxTVCore.getData().getHitboxFollowers().replace("\"", "");
		gamePlayed = HitboxTVCore.getData().getHitboxStreamingGame();
	}

	protected void actionPerformed(GuiButton button) throws IOException {
		switch (button.id) {
		case 0:
		case 1:
		}
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		String channelOffline = EnumChatFormatting.UNDERLINE + HitboxTVCore.getData().getHitboxChannelName() + " " + I18n.format("live.offline");
		String channelOnline = EnumChatFormatting.UNDERLINE + HitboxTVCore.getData().getHitboxChannelName() + " " + I18n.format("live.online");

		if (this.isStreaming) {
			// Title
			this.fontRendererObj.drawString(EnumChatFormatting.GREEN + channelOnline, this.width / 2 - this.fontRendererObj.getStringWidth(channelOffline) / 2, 5, -1);
			// Total Viewers on live
			this.fontRendererObj.drawString(streamingViewers, 2, 35, -1);
			// Game Played
			this.fontRendererObj.drawString(EnumChatFormatting.ITALIC + gamePlayed, this.width / 2 - this.fontRendererObj.getStringWidth(gamePlayed) / 2, 20, -1);
		} else {
			this.fontRendererObj.drawString(EnumChatFormatting.RED + channelOffline, this.width / 2 - this.fontRendererObj.getStringWidth(channelOffline) / 2, 5, -1);
		}

		// Get Total Channel Views
		this.fontRendererObj.drawString(EnumChatFormatting.UNDERLINE + channelTotalViews, 2, this.height - 10, -1);
		// Get channel followers
		this.fontRendererObj.drawString(EnumChatFormatting.UNDERLINE + followers, this.width / 2 - this.fontRendererObj.getStringWidth(followers) / 2, this.height - 10, -1);

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

}
