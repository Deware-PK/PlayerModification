package com.stand.listener;

import com.stand.enums.Locale;
import com.stand.utility.ListUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlayerAntiBuildEvent implements Listener {

	@EventHandler
	public void onBuild(final BlockPlaceEvent event) {

		final Player player = event.getPlayer();

		if (ListUtil.getAntiBuildPlayer(player) && !player.hasPermission("PlayerModification.allowbuild")) {
			event.setBuild(false);
			Locale.ANTI_BUILD.sendMessage(player);
		}
	}
}
