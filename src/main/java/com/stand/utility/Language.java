package com.stand.utility;

import de.leonhard.storage.Yaml;

public class Language {

	public static void init() {

		final Yaml yaml = new Yaml("language", "plugins/PlayerModification");
		yaml.setDefault("Anti-build-message" , "&cYou cannot build anything in this world!");
		yaml.setDefault("Pvp-disable-message" , "&cThis world isn't allowed to pvp!");
		yaml.setDefault("Do-not-have-permission" , "&cYou don't have permission to access PlayerModification plugin!");

	}
}
