package com.stand.listener;

import com.stand.PluginCollection;
import com.stand.utility.Common;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlayerAntiBuildEvent implements Listener {

	@EventHandler
	public void onBuild(final BlockPlaceEvent event) {
		final Player player = event.getPlayer();

		if (PluginCollection.getAntiBuildPlayer(player) && !player.hasPermission("PlayerModification.allowbuild")) {
			event.setBuild(false);
			Common.sendMessage(player , "&cYou cannot build anything in this world!");
		}
	}
}
