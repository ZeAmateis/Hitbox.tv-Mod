package fr.zeamateis.hitbox.common.command;

import java.util.Arrays;
import java.util.List;

import fr.zeamateis.hitbox.common.core.HitboxTVCore;
import fr.zeamateis.hitbox.common.core.HitboxTVLogin;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public class CommandChatHitbox extends CommandBase {

	public List getAliases() {
		return Arrays.asList(new String[] { "h", "hb" });
	}

	/**
	 * Returns true if the given command sender is allowed to use this command.
	 */
	public boolean canCommandSenderUse(ICommandSender p_71519_1_) {
		return super.canCommandSenderUse(p_71519_1_);
	}

	/**
	 * Get the name of the command
	 */
	public String getName() {
		return "hitbox";
	}

	/**
	 * Return the required permission level for this command.
	 */
	public int getRequiredPermissionLevel() {
		return 0;
	}

	public String getCommandUsage(ICommandSender sender) {
		return "commands.hitbox.msg.usage";
	}

	/**
	 * Called when a CommandSender executes this command
	 */
	public void execute(ICommandSender sender, String[] args) throws CommandException {
		if (args.length <= 0) {
			throw new WrongUsageException("commands.hitbox.msg.usage", new Object[0]);
		} else if (args[0].equals("disconnect") && HitboxTVLogin.isLogged() && (!HitboxTVLogin.getChatReceiver().isClosed() && !HitboxTVLogin.getChatSender().isClosed())) {
			try {
				HitboxTVLogin.getChatReceiver().disconnect();
				HitboxTVLogin.getChatSender().disconnect();
				HitboxTVLogin.setLogged(false);
				sender.addChatMessage(new ChatComponentTranslation("commands.hitbox.exit.success"));
			} catch (Exception e) {
				e.printStackTrace();
				throw new WrongUsageException("commands.hitbox.exit.usage", new Object[0]);
			}
		} else if (args[0].equals("msg")) {
			IChatComponent ichatcomponent = getChatComponentFromNthArg(sender, args, 1, !(sender instanceof EntityPlayer));
			try {
				if (args[1].length() != 0) {
					HitboxTVLogin.getChatSender().sendMessage(ichatcomponent.createCopy().getUnformattedText());
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new WrongUsageException("commands.hitbox.msg.usage", new Object[0]);
			}
		} else if (args[0].equals("pm") && HitboxTVLogin.isLogged()) {
			IChatComponent ichatcomponent = getChatComponentFromNthArg(sender, args, 2, !(sender instanceof EntityPlayer));
			try {
				if (args[1].length() != 0) {
					HitboxTVLogin.getChatSender().sendPrivateMessage(args[1], ichatcomponent.createCopy().getUnformattedText());
					sender.addChatMessage(new ChatComponentTranslation("commands.hitbox.whisper.confirm", args[1]));
				}
			} catch (Exception e) {
				throw new WrongUsageException("commands.hitbox.whisper.usage", new Object[0]);
			}
		} else if (args[0].equals("timeout") && HitboxTVLogin.isLogged()) {
			try {
				if (args[1].length() != 0) {
					HitboxTVLogin.getChatSender().timeoutUser(args[1], Integer.parseInt(args[2]));
					sender.addChatMessage(new ChatComponentTranslation("commands.hitbox.timeout.confirm", args[1], args[2]));
				}
			} catch (Exception e) {
				throw new WrongUsageException("commands.hitbox.timeout.usage", new Object[0]);
			}
		} else if (args[0].equals("ban") && HitboxTVLogin.isLogged()) {
			try {
				if (args[1].length() != 0) {
					HitboxTVLogin.getChatSender().banUser(args[1]);
					sender.addChatMessage(new ChatComponentTranslation("commands.hitbox.ban.confirm", args[1]));
				}
			} catch (Exception e) {
				throw new WrongUsageException("commands.hitbox.ban.usage", new Object[0]);
			}
		} else if (args[0].equals("ban-ip") && HitboxTVLogin.isLogged()) {
			try {
				if (args[1].length() != 0) {
					HitboxTVLogin.getChatSender().banIPUser(args[1]);
					sender.addChatMessage(new ChatComponentTranslation("commands.hitbox.ban-ip.confirm", args[1]));
				}
			} catch (Exception e) {
				throw new WrongUsageException("commands.hitbox.ban.usage", new Object[0]);
			}
		} else if (args[0].equals("unban") && HitboxTVLogin.isLogged()) {
			try {
				if (args[1].length() != 0) {
					HitboxTVLogin.getChatSender().unbanUser(args[1]);
					sender.addChatMessage(new ChatComponentTranslation("commands.hitbox.unban.confirm", args[1]));
				}
			} catch (Exception e) {
				throw new WrongUsageException("commands.hitbox.unban.usage", new Object[0]);
			}
		} else if (args[0].equals("add-modo") && HitboxTVLogin.isLogged()) {
			try {
				if (args[1].length() != 0) {
					HitboxTVLogin.getChatSender().addModerator(args[1]);
					sender.addChatMessage(new ChatComponentTranslation("commands.hitbox.add-modo.confirm", args[1]));
				}
			} catch (Exception e) {
				throw new WrongUsageException("commands.hitbox.add-modo.usage", new Object[0]);
			}
		} else if (args[0].equals("rm-modo") && HitboxTVLogin.isLogged()) {
			try {
				if (args[1].length() != 0) {
					HitboxTVLogin.getChatSender().removeModerator(args[1]);
					sender.addChatMessage(new ChatComponentTranslation("commands.hitbox.rm-modo.confirm", args[1]));
				}
			} catch (Exception e) {
				throw new WrongUsageException("commands.hitbox.rm-modo.usage", new Object[0]);
			}
		} else if (args[0].equals("slowmode") && HitboxTVLogin.isLogged()) {
			try {
				if (args[1].length() != 0 && !args[1].equals("off")) {
					HitboxTVLogin.getChatSender().setSlowMode(Integer.parseInt(args[1]));
					sender.addChatMessage(new ChatComponentTranslation("commands.hitbox.slowmode.confirm", args[1]));
				} else if (args[1].length() != 0 && args[1].equals("off")) {
					HitboxTVLogin.getChatSender().setSlowMode(Integer.parseInt("0"));
					sender.addChatMessage(new ChatComponentTranslation("commands.hitbox.slowmode-off.confirm", args[1]));
				}
			} catch (Exception e) {
				throw new WrongUsageException("commands.hitbox.slowmode.usage", new Object[0]);
			}
		} else if (args[0].equals("submode") && HitboxTVLogin.isLogged()) {
			try {
				if (args[1].length() != 0 && args[1].equals("on")) {
					HitboxTVLogin.getChatSender().setSubscriberMode(true);
					sender.addChatMessage(new ChatComponentTranslation("commands.hitbox.submode-on.confirm", args[1]));
				} else if (args[1].length() != 0 && args[1].equals("off")) {
					HitboxTVLogin.getChatSender().setSubscriberMode(false);
					sender.addChatMessage(new ChatComponentTranslation("commands.hitbox.submode-off.confirm", args[1]));
				}
			} catch (Exception e) {
				throw new WrongUsageException("commands.hitbox.submode.usage", new Object[0]);
			}
		} else if (!HitboxTVLogin.isLogged() || HitboxTVCore.getData().getHitboxChannel() == null) {
			throw new WrongUsageException("commands.hitbox.login.usage", new Object[0]);
		}

	}

	/**
	 * Return whether the specified command parameter index is a username
	 * parameter.
	 */
	public boolean isUsernameIndex(String[] args, int index) {
		return index == 0;
	}
}