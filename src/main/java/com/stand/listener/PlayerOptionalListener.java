package com.stand.listener;

import com.stand.enums.Locale;
import com.stand.utility.ListUtil;
import com.stand.utility.PlayerUtil;
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

			if (ListUtil.getPvpPlayer(damager) && !PlayerUtil.hasPerm(damager , "PlayerModification.PvpBypass")) {
				event.setCancelled(true);
				Locale.PVP_DISABLED.sendMessage(damager);
			}

		// Condition #2 - PVP with projectile
		} else if (event.getDamager() instanceof Projectile && event.getEntity() instanceof Player) {

			final Projectile projectile = (Projectile) event.getDamager();

			if (projectile.getShooter() instanceof Player && event.getEntity() instanceof Player) {

				final Player damager = (Player) projectile.getShooter();

				if (ListUtil.getPvpPlayer(damager) && !PlayerUtil.hasPerm(damager , "PlayerModification.PvpBypass")) {
					event.setCancelled(true);
					Locale.PVP_DISABLED.sendMessage(damager);
				}

			}

		}
	}
}
