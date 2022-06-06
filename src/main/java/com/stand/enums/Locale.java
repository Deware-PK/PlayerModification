package com.stand.enums;

import com.stand.utility.Common;
import de.leonhard.storage.Yaml;
import org.bukkit.entity.Player;

public enum Locale {

	ANTI_BUILD {
		@Override
		public void sendMessage(final Player player) {
			final Yaml yaml = new Yaml("language", "plugins/PlayerModification");
			Common.sendMessage(player , yaml.getString("Anti-build-message"));
		}
	},
	PVP_DISABLED {
		@Override
		public void sendMessage(final Player player) {
			final Yaml yaml = new Yaml("language", "plugins/PlayerModification");
			Common.sendMessage(player , Common.colorize(yaml.getString("Pvp-disable-message")));
		}
	},
	NO_PERMISSION {
		@Override
		public void sendMessage(final Player player) {
			final Yaml yaml = new Yaml("language", "plugins/PlayerModification");
			Common.sendMessage(player , yaml.getString("Do-not-have-permission"));
		}
	};


	public abstract void sendMessage(Player player);
}
