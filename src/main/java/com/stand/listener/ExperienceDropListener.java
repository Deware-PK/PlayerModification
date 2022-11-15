package com.stand.listener;

import com.stand.utility.Common;
import com.stand.utility.ListUtil;
import de.leonhard.storage.Config;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

public class ExperienceDropListener implements Listener {


	@EventHandler
	public void onEntitySpawn(final EntitySpawnEvent event) {
		final Config config = new Config("Settings", "plugins/PlayerModification");

		for (final String worldName : Common.getWorldNames()) {

			if (!config.getSection(worldName).getBoolean("Enabled_Mob_To_Drop_Exp")) {

				if (!(event.getEntity() instanceof LivingEntity)) return;

				final Entity entity = event.getEntity();

				for (final String BlackListWorldName : Common.getBlackListWorldNames()) {

					if (entity.getWorld().getName().equals(worldName) && !entity.getWorld().getName().equals(BlackListWorldName)) {

						ListUtil.addDisableDropEntity(entity);
					}
				}
			}

		}

	}

	@EventHandler
	public void onMobDied(final EntityDeathEvent event) {
		final Entity entity = event.getEntity();

		if (ListUtil.getDisableDropEntity(entity)) {
			event.setDroppedExp(0);
		}
	}
}
