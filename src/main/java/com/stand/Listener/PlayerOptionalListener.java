package com.stand.Listener;

import com.stand.PluginCollection;
import com.stand.Utility.Common;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerOptionalListener implements Listener {

	@EventHandler
	public void onCombat(final EntityDamageByEntityEvent event) {
		final Player player = (Player) event.getDamager();

		if(PluginCollection.getPvpPlayer(player)) {
			if (event.getDamager() instanceof Player) {
				if(event.getEntity() instanceof Player) {
					event.setCancelled(true);
					player.sendMessage(Common.colorize("&cThis world isn't allowed to pvp!"));
				}
			}
		}
	}
}
