package com.stand.Utility;

import org.bukkit.Bukkit;
import org.bukkit.permissions.Permissible;

public class PlayerUtil {

	public static boolean hasPerm(final Permissible sender, final String permission) {
		Valid.checkNotNull(sender, "cannot call hasPerm for null sender!");

		if (permission == null) {
			Bukkit.getConsoleSender().sendMessage(Common.colorize("&4[Error]&c Error code: UPU-l11 please contact developer!"));

			new Throwable().printStackTrace();

			return true;
		}

		return sender.hasPermission(permission);
	}

}
