package com.stand.listener;

import com.stand.utility.Common;
import com.stand.utility.ListUtil;
import de.leonhard.storage.Yaml;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlayerAntiBuildEvent implements Listener {

	@EventHandler
	public void onBuild(final BlockPlaceEvent event) {
		final Yaml yaml = new Yaml("language", "plugins/PlayerModification");
		final Player player = event.getPlayer();

		if (ListUtil.getAntiBuildPlayer(player) && !player.hasPermission("PlayerModification.allowbuild")) {
			event.setBuild(false);
			Common.sendMessage(player , yaml.getString("Anti-build-message"));
		}
	}
}
