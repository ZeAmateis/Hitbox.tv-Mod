package fr.zeamateis.hitbox.client.gui;

import fr.zeamateis.hitbox.common.core.HitboxTVCore;
import fr.zeamateis.hitbox.common.utils.Utils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiHitboxTVInfo extends GuiScreen {

	String streamingViewers, channelTotalViews, gamePlayed, followers, liveTitle;
	boolean isStreaming;

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	public void initGui() {
		this.buttonList.clear();
		this.isStreaming = HitboxTVCore.getData().getIsStreaming();
		this.liveTitle = HitboxTVCore.getData().getHitboxStreamingTitle();
		this.streamingViewers = I18n.format("live.actualViewers") + " : " + HitboxTVCore.getData().getHitboxSreamingViews().replace("\"", "");
		this.channelTotalViews = I18n.format("channel.totalViews") + " : " + HitboxTVCore.getData().getHitboxTotalViews().replace("\"", "");
		this.followers = I18n.format("channel.followers") + " : " + HitboxTVCore.getData().getHitboxFollowers().replace("\"", "");
		this.gamePlayed = HitboxTVCore.getData().getHitboxStreamingGame();
	}

	protected void actionPerformed(GuiButton button) {
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
		this.drawDefaultBackground();

		this.mc.getTextureManager().bindTexture(new ResourceLocation(Utils.MODID, "textures/gui/background/hitbox_data.png"));
		int i = (this.width - 248) / 2;
		int j = (this.height - 166) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, 248, 166);

		String channelOffline = EnumChatFormatting.UNDERLINE + HitboxTVCore.getData().getHitboxChannelName() + " " + I18n.format("live.offline");
		String channelOnline = EnumChatFormatting.UNDERLINE + HitboxTVCore.getData().getHitboxChannelName() + " " + I18n.format("live.online");

		if (this.isStreaming) {
			// Channel Name
			this.fontRendererObj.drawString(EnumChatFormatting.GREEN + channelOnline, this.width / 2 - this.fontRendererObj.getStringWidth(channelOnline) / 2, this.height / 2 - 78, -1);

			// Live Title
			// TODO Split title
			this.fontRendererObj.drawString(EnumChatFormatting.UNDERLINE + liveTitle, this.width / 2 - this.fontRendererObj.getStringWidth(liveTitle) / 2, this.height / 2 - 50, -1);

			// Game Played
			this.fontRendererObj.drawString(EnumChatFormatting.ITALIC + gamePlayed, this.width / 2 - this.fontRendererObj.getStringWidth(gamePlayed) / 2, this.height / 2 - 65, -1);

			// Total Viewers on live
			this.fontRendererObj.drawString(streamingViewers, this.width / 2 - 118, this.height / 2 - 25, -1);
		} else {
			this.fontRendererObj.drawString(EnumChatFormatting.RED + channelOffline, this.width / 2 - this.fontRendererObj.getStringWidth(channelOffline) / 2, this.height / 2 - 78, -1);
		}

		// Get Total Channel Views
		this.fontRendererObj.drawString(EnumChatFormatting.UNDERLINE + channelTotalViews, this.width / 2 - 118, this.height - 52, -1);
		// Get channel followers
		this.fontRendererObj.drawString(EnumChatFormatting.UNDERLINE + followers, this.width / 2 - 118, this.height - 70, -1);

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	/**
	 * Returns true if this GUI should pause the game when it is displayed in
	 * single-player
	 */
	public boolean doesGuiPauseGame() {
		return false;
	}

}
