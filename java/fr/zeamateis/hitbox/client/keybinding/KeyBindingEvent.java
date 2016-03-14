package fr.zeamateis.hitbox.client.keybinding;

import fr.zeamateis.hitbox.client.gui.GuiHitboxTVLogin;
import fr.zeamateis.hitbox.proxy.ClientProxy;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class KeyBindingEvent {

	@SubscribeEvent
	public void onEvent(KeyInputEvent event) {
		if (ClientProxy.keyBindHitBoxConfig.isPressed()) {
			FMLClientHandler.instance().getClient().displayGuiScreen(new GuiHitboxTVLogin(FMLClientHandler.instance().getClient().currentScreen));
		}
	}
}