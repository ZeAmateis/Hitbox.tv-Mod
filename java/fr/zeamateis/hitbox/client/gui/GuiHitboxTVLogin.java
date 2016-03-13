package fr.zeamateis.hitbox.client.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import fr.zeamateis.hitbox.common.core.HitboxTVCore;
import fr.zeamateis.hitbox.common.core.HitboxTVLogin;
import fr.zeamateis.hitbox.common.utils.Utils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiHitboxTVLogin extends GuiScreen {
	private final GuiScreen parentScreen;
	private GuiTextField username;
	private GuiTextField channelName;
	private GuiTextFieldLogin password;

	public GuiHitboxTVLogin(GuiScreen parentScreen) {
		this.parentScreen = parentScreen;
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	public void updateScreen() {
		this.password.updateCursorCounter();
		this.username.updateCursorCounter();
		this.channelName.updateCursorCounter();

		if (HitboxTVLogin.isLogged()) {
			((GuiButton) this.buttonList.get(1)).enabled = false;
			((GuiButton) this.buttonList.get(2)).enabled = true;
			((GuiButton) this.buttonList.get(3)).enabled = true;
			this.username.setText(HitboxTVCore.getData().getHitboxUsername());
			this.password.setText(HitboxTVCore.getData().getHitboxPassword());
			this.channelName.setText(HitboxTVCore.getData().getHitboxChannel());
		} else if (!this.username.getText().isEmpty() && !this.password.getText().isEmpty() && !this.channelName.getText().isEmpty()) {
			((GuiButton) this.buttonList.get(1)).enabled = true;
			((GuiButton) this.buttonList.get(2)).enabled = false;
			((GuiButton) this.buttonList.get(3)).enabled = false;
		} else {
			((GuiButton) this.buttonList.get(1)).enabled = false;
			((GuiButton) this.buttonList.get(2)).enabled = false;
			((GuiButton) this.buttonList.get(3)).enabled = false;
		}
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		this.buttonList.clear();

		this.buttonList.add(new GuiButton(0, this.width / 2 + 65, this.height / 2 + 75, 50, 20, I18n.format("gui.back")));
		this.buttonList.add(new GuiButton(1, this.width / 2 + 30, this.height / 2 - 5, 70, 20, I18n.format("gui.hitbox.login")));
		this.buttonList.add(new GuiButton(2, this.width / 2 + 30, this.height / 2 + 20, 70, 20, I18n.format("menu.disconnect")));
		this.buttonList.add(new GuiButton(3, this.width / 2 + 30, this.height / 2 + 45, 70, 20, I18n.format("gui.hitbox.channel.info")));

		this.password = new GuiTextFieldLogin(0, this.fontRendererObj, this.width / 2 - 98, this.height / 2 + 32, 100, 20);
		this.password.setEnableBackgroundDrawing(false);
		this.password.setMaxStringLength(100);
		this.password.setText("");

		this.username = new GuiTextField(1, this.fontRendererObj, this.width / 2 - 98, this.height / 2 - 1, 100, 20);
		this.username.setEnableBackgroundDrawing(false);
		this.username.setFocused(true);
		this.username.setMaxStringLength(128);
		this.username.setText("");

		this.channelName = new GuiTextField(2, this.fontRendererObj, this.width / 2 - 98, this.height / 2 + 65, 100, 20);
		this.channelName.setEnableBackgroundDrawing(false);
		this.channelName.setMaxStringLength(128);
		this.channelName.setText("");
	}

	/**
	 * Called when the screen is unloaded. Used to disable keyboard repeat
	 * events
	 */
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	protected void actionPerformed(GuiButton p_146284_1_) {
		switch (p_146284_1_.id) {
		case 0:
			this.mc.displayGuiScreen(this.parentScreen);
			break;
		case 1:
			this.mc.thePlayer.addChatMessage(new ChatComponentTranslation("commands.hitbox.login.try"));
			try {
				HitboxTVCore.getData().setHitboxUsername(this.username.getText());
				HitboxTVCore.getData().setHitboxPassword(this.password.getText());
				HitboxTVCore.getData().setHitboxChannel(this.channelName.getText());

				HitboxTVCore.getData().tokenLogin(true);
				HitboxTVCore.getToken().generateToken();

				HitboxTVLogin.init();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				this.mc.displayGuiScreen(this.parentScreen);
			}
			break;
		case 2:
			try {
				HitboxTVLogin.getChatReceiver().disconnect();
				HitboxTVLogin.getChatSender().disconnect();
				HitboxTVLogin.setLogged(false);
				this.mc.thePlayer.addChatMessage(new ChatComponentTranslation("commands.hitbox.exit.success"));
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				this.mc.displayGuiScreen(this.parentScreen);
			}
			break;
		case 3:
			this.mc.displayGuiScreen(new GuiHitboxTVInfo());
			break;
		}
	}

	/**
	 * Fired when a key is typed (except F11 who toggle full screen). This is
	 * the equivalent of KeyListener.keyTyped(KeyEvent e). Args : character
	 * (character on the key), keyCode (lwjgl Keyboard key code)
	 */
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		this.password.textboxKeyTyped(typedChar, keyCode);
		this.username.textboxKeyTyped(typedChar, keyCode);
		this.channelName.textboxKeyTyped(typedChar, keyCode);
	}

	/**
	 * Called when the mouse is clicked.
	 * 
	 * @throws IOException
	 */
	protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_) throws IOException {
		super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
		this.username.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
		this.password.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
		this.channelName.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		this.drawDefaultBackground();

		this.mc.getTextureManager().bindTexture(new ResourceLocation(Utils.MODID, "textures/gui/background/login.png"));
		int i = (this.width - 248) / 2;
		int j = (this.height - 209) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, 248, 209);

		this.password.drawTextBox();
		this.username.drawTextBox();
		this.channelName.drawTextBox();

		String loggedAs = I18n.format("gui.hitbox.login.logged", HitboxTVCore.getData().getHitboxUsername());
		String notLogged = I18n.format("gui.hitbox.login.notLogged", HitboxTVCore.getData().getHitboxUsername());

		if (HitboxTVLogin.isLogged())
			this.fontRendererObj.drawString(loggedAs, this.width / 2 - this.fontRendererObj.getStringWidth(loggedAs) / 2, this.height / 2 - 20, 0x00AD06);
		else
			this.fontRendererObj.drawString(notLogged, this.width / 2 - this.fontRendererObj.getStringWidth(notLogged) / 2, this.height / 2 - 20, 0xF21111);

		if (!this.username.isFocused() && this.username.getText().length() <= 0)// GuiMainMenu
			this.fontRendererObj.drawString(I18n.format("gui.hitbox.login.username"), this.width / 2 - 97, this.height / 2 - 1, 0xC7C7C7);

		if (!this.password.isFocused() && this.password.getText().length() <= 0)
			this.fontRendererObj.drawString(I18n.format("gui.hitbox.login.password"), this.width / 2 - 97, this.height / 2 + 32, 0xC7C7C7);

		if (!this.channelName.isFocused() && this.channelName.getText().length() <= 0)
			this.fontRendererObj.drawString(I18n.format("gui.hitbox.login.channelName"), this.width / 2 - 97, this.height / 2 + 65, 0xC7C7C7);

		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
	}
}