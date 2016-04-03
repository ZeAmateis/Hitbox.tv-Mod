package fr.zeamateis.hitbox.proxy;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import fr.zeamateis.hitbox.client.keybinding.KeyBindingEvent;
import net.minecraft.client.settings.KeyBinding;

public class ClientProxy extends CommonProxy {
	public static KeyBinding keyBindHitBoxConfig;

	@Override
	public void init() {
		this.registerKeyBinding();
	}

	private void registerKeyBinding() {
		FMLCommonHandler.instance().bus().register(new KeyBindingEvent());
		keyBindHitBoxConfig = new KeyBinding("hitboxMenu.key", Keyboard.KEY_H, "key.categories.hitbox");
		ClientRegistry.registerKeyBinding(keyBindHitBoxConfig);
	}
}