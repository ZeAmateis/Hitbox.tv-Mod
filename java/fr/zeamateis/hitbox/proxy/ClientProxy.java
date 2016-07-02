package fr.zeamateis.hitbox.proxy;

import org.lwjgl.input.Keyboard;

import fr.zeamateis.hitbox.client.keybinding.KeyBindingEvent;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;

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