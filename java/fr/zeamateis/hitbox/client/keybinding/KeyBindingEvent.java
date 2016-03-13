package fr.zeamateis.hitbox.client.keybinding;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.zeamateis.hitbox.client.gui.GuiHitboxTVLogin;
import fr.zeamateis.hitbox.proxy.ClientProxy;

@SideOnly(Side.CLIENT)
public class KeyBindingEvent {

	@SubscribeEvent
	public void onEvent(KeyInputEvent event) {
		if (ClientProxy.keyBindHitBoxConfig.isPressed()) {
			FMLClientHandler.instance().getClient().displayGuiScreen(new GuiHitboxTVLogin(FMLClientHandler.instance().getClient().currentScreen));
		}
	}
}