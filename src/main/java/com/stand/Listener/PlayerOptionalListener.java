package com.stand.Listener;

import com.stand.PluginCollection;
import com.stand.Utility.Common;
import com.stand.Utility.PlayerUtil;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerOptionalListener implements Listener {

	@EventHandler
	public void onCombat(final EntityDamageByEntityEvent event) {

		// Condition #1 - PVP without projectile
		if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
			final Player damager = (Player) event.getDamager();


			if (PluginCollection.getPvpPlayer(damager) && !PlayerUtil.hasPerm(damager , "PlayerModification.PvpBypass")) {
				event.setCancelled(true);
				damager.sendMessage(Common.colorize("&cThis world isn't allowed to pvp!"));
			}

		// Condition #2 - PVP with projectile
		} else if (event.getDamager() instanceof Projectile && event.getEntity() instanceof Player) {

			final Projectile projectile = (Projectile) event.getDamager();

			if (projectile.getShooter() instanceof Player && event.getEntity() instanceof Player) {

				final Player damager = (Player) projectile.getShooter();

				if (PluginCollection.getPvpPlayer(damager) && !PlayerUtil.hasPerm(damager , "PlayerModification.PvpBypass")) {
					event.setCancelled(true);
					damager.sendMessage(Common.colorize("&cThis world isn't allowed to pvp!"));
				}

			}
		}
	}


}
